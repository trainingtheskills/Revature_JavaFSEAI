package com.banking.operations;

import com.banking.BankOperations;
import com.banking.database.DBConnection;
import com.banking.exception.InsufficientBalanceException;
import com.banking.exception.InvalidAccountException;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.utility.AccountNumberGenerator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/** JDBC implementation of all banking operations. */
public class BankingOperations implements BankOperations {

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE = Pattern.compile("^\\+?[0-9]{7,14}$");
    private static final int MAX_ACCOUNT_NUMBER_RETRIES = 5;

    // ------------------------------------------------------------------ customers

    @Override
    public Customer createCustomer(String name, String email, String phone) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (email == null || !EMAIL.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (phone == null || !PHONE.matcher(phone.trim()).matches()) {
            throw new IllegalArgumentException("Invalid phone number (7-14 digits, optional leading +).");
        }
        name = name.trim();
        email = email.trim().toLowerCase();
        phone = phone.trim();

        String sql = "INSERT INTO customers (customer_name, email, phone) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return new Customer(keys.getInt(1), name, email, phone);
            }
        } catch (SQLException e) {
            if (isDuplicateKey(e)) {
                throw new IllegalArgumentException("A customer with email '" + email + "' already exists.");
            }
            throw e;
        }
    }

    @Override
    public Customer getCustomer(int customerId) throws SQLException {
        String sql = "SELECT customer_id, customer_name, email, phone FROM customers WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getInt("customer_id"), rs.getString("customer_name"),
                            rs.getString("email"), rs.getString("phone"));
                }
            }
        }
        throw new IllegalArgumentException("Customer " + customerId + " does not exist.");
    }

    // ------------------------------------------------------------------ accounts

    @Override
    public Account createAccount(int customerId, String accountType) throws SQLException {
        if (!Account.SAVINGS.equalsIgnoreCase(accountType) && !Account.CURRENT.equalsIgnoreCase(accountType)) {
            throw new IllegalArgumentException("Account type must be SAVINGS or CURRENT.");
        }
        getCustomer(customerId); // rule 3: account only for an existing customer

        String type = accountType.toUpperCase();
        String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance, created_date) "
                   + "VALUES (?, ?, ?, 0.00, ?)";
        LocalDate today = LocalDate.now();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int attempt = 1; attempt <= MAX_ACCOUNT_NUMBER_RETRIES; attempt++) {
                long accountNumber = AccountNumberGenerator.generate();
                ps.setLong(1, accountNumber);
                ps.setInt(2, customerId);
                ps.setString(3, type);
                ps.setDate(4, Date.valueOf(today));
                try {
                    ps.executeUpdate();
                    return Account.of(type, accountNumber, customerId, BigDecimal.ZERO.setScale(2), today);
                } catch (SQLException e) {
                    // rule 2: the primary key guarantees uniqueness; on a collision, try a new number
                    if (!isDuplicateKey(e) || attempt == MAX_ACCOUNT_NUMBER_RETRIES) {
                        throw e;
                    }
                }
            }
        }
        throw new SQLException("Could not generate a unique account number.");
    }

    @Override
    public List<Account> getAccountsByCustomer(int customerId) throws SQLException {
        String sql = "SELECT account_number, customer_id, account_type, balance, created_date "
                   + "FROM accounts WHERE customer_id = ? ORDER BY account_number";
        List<Account> accounts = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapAccount(rs));
                }
            }
        }
        return accounts;
    }

    @Override
    public Account getAccount(long accountNumber) throws InvalidAccountException, SQLException {
        try (Connection con = DBConnection.getConnection()) {
            return fetchAccount(con, accountNumber, false);
        }
    }

    // ------------------------------------------------------------------ deposit / withdraw / balance

    @Override
    public void deposit(long accountNumber, BigDecimal amount) throws InvalidAccountException, SQLException {
        validateAmount(amount);
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                Account account = fetchAccount(con, accountNumber, true);
                updateBalance(con, accountNumber, account.getBalance().add(amount));
                insertTransaction(con, new Transaction(accountNumber, Transaction.DEPOSIT, amount, newReference()));
                con.commit();
            } catch (Exception e) {
                rollbackQuietly(con);
                throw e;
            }
        }
    }

    /** Overload: accepts a double (converted safely via BigDecimal.valueOf). */
    @Override
    public void deposit(long accountNumber, double amount) throws InvalidAccountException, SQLException {
        deposit(accountNumber, BigDecimal.valueOf(amount));
    }

    @Override
    public void withdraw(long accountNumber, BigDecimal amount)
            throws InvalidAccountException, InsufficientBalanceException, SQLException {
        validateAmount(amount);
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                Account account = fetchAccount(con, accountNumber, true);
                if (!account.canWithdraw(amount)) {
                    throw new InsufficientBalanceException("Insufficient balance. Available: "
                            + account.getBalance().toPlainString() + ", requested: " + amount.toPlainString());
                }
                updateBalance(con, accountNumber, account.getBalance().subtract(amount));
                insertTransaction(con, new Transaction(accountNumber, Transaction.WITHDRAWAL, amount, newReference()));
                con.commit();
            } catch (Exception e) {
                rollbackQuietly(con);
                throw e;
            }
        }
    }

    @Override
    public BigDecimal getBalance(long accountNumber) throws InvalidAccountException, SQLException {
        return getAccount(accountNumber).getBalance();
    }

    // ------------------------------------------------------------------ fund transfer

    @Override
    public String transfer(long fromAccount, long toAccount, BigDecimal amount)
            throws InvalidAccountException, InsufficientBalanceException, SQLException {
        if (fromAccount == toAccount) {
            throw new IllegalArgumentException("Sender and receiver accounts must be different.");
        }
        validateAmount(amount);

        // One connection, one transaction for the whole transfer.
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                // Lock rows in a fixed (ascending) order so two opposite transfers cannot deadlock.
                Account first = fetchAccount(con, Math.min(fromAccount, toAccount), true);
                Account second = fetchAccount(con, Math.max(fromAccount, toAccount), true);
                Account sender = (first.getAccountNumber() == fromAccount) ? first : second;
                Account receiver = (sender == first) ? second : first;

                if (!sender.canWithdraw(amount)) {
                    throw new InsufficientBalanceException("Insufficient balance in account " + fromAccount
                            + ". Available: " + sender.getBalance().toPlainString()
                            + ", requested: " + amount.toPlainString());
                }

                updateBalance(con, fromAccount, sender.getBalance().subtract(amount));
                updateBalance(con, toAccount, receiver.getBalance().add(amount));

                String reference = newReference();
                insertTransaction(con, new Transaction(fromAccount, Transaction.TRANSFER, amount.negate(), reference));
                insertTransaction(con, new Transaction(toAccount, Transaction.TRANSFER, amount, reference));

                con.commit();
                return reference;
            } catch (Exception e) {
                rollbackQuietly(con); // undo every step of the transfer
                throw e;
            }
        }
    }

    // ------------------------------------------------------------------ history

    @Override
    public List<Transaction> getTransactionHistory(long accountNumber) throws InvalidAccountException, SQLException {
        String sql = "SELECT t.transaction_id, t.account_number, c.customer_name, t.transaction_type, "
                   + "       t.amount, t.transaction_reference, t.transaction_date "
                   + "FROM transactions t "
                   + "JOIN accounts a ON t.account_number = a.account_number "
                   + "JOIN customers c ON a.customer_id = c.customer_id "
                   + "WHERE t.account_number = ? "
                   + "ORDER BY t.transaction_date, t.transaction_id";
        List<Transaction> history = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            fetchAccount(con, accountNumber, false); // validates the account
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, accountNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        history.add(new Transaction(
                                rs.getInt("transaction_id"),
                                rs.getLong("account_number"),
                                rs.getString("customer_name"),
                                rs.getString("transaction_type"),
                                rs.getBigDecimal("amount"),
                                rs.getString("transaction_reference"),
                                rs.getTimestamp("transaction_date").toLocalDateTime()));
                    }
                }
            }
        }
        return history;
    }

    // ------------------------------------------------------------------ private helpers

    private Account fetchAccount(Connection con, long accountNumber, boolean lockRow)
            throws InvalidAccountException, SQLException {
        String sql = "SELECT account_number, customer_id, account_type, balance, created_date "
                   + "FROM accounts WHERE account_number = ?" + (lockRow ? " FOR UPDATE" : "");
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAccount(rs);
                }
            }
        }
        throw new InvalidAccountException("Account " + accountNumber + " does not exist.");
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        return Account.of(rs.getString("account_type"), rs.getLong("account_number"),
                rs.getInt("customer_id"), rs.getBigDecimal("balance"),
                rs.getDate("created_date").toLocalDate());
    }

    private void updateBalance(Connection con, long accountNumber, BigDecimal newBalance) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_number = ?")) {
            ps.setBigDecimal(1, newBalance);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
        }
    }

    private void insertTransaction(Connection con, Transaction t) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, "
                   + "transaction_reference, transaction_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, t.getAccountNumber());
            ps.setString(2, t.getTransactionType());
            ps.setBigDecimal(3, t.getAmount());
            ps.setString(4, t.getTransactionReference());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (amount.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException("Amount cannot have more than 2 decimal places.");
        }
        if (amount.compareTo(new BigDecimal("9999999999.99")) > 0) {
            throw new IllegalArgumentException("Amount is too large.");
        }
    }

    private String newReference() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private boolean isDuplicateKey(SQLException e) {
        return e.getErrorCode() == 1062 || "23505".equals(e.getSQLState());
    }

    private void rollbackQuietly(Connection con) {
        try {
            con.rollback();
        } catch (SQLException ignored) {
            // connection is closed right after; nothing more to do
        }
    }
}
