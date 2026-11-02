package com.banking;

import com.banking.database.DBConnection;
import com.banking.exception.InsufficientBalanceException;
import com.banking.exception.InvalidAccountException;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.operations.BankingOperations;
import com.banking.utility.InputUtility;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** Application entry point: shows the menu and drives the console workflow. */
public class Main {

    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final InputUtility input = new InputUtility();
    private static final BankOperations bank = new BankingOperations();

    public static void main(String[] args) {
        try {
            DBConnection.testConnection();
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database: " + e.getMessage());
            System.out.println("Check resources/db.properties and make sure MySQL is running.");
            return;
        }

        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = input.readInt("Enter your choice: ");
                switch (choice) {
                    case 1: createCustomer(); break;
                    case 2: createAccount(); break;
                    case 3: viewCustomerDetails(); break;
                    case 4: deposit(); break;
                    case 5: withdraw(); break;
                    case 6: checkBalance(); break;
                    case 7: transfer(); break;
                    case 8: viewTransactionHistory(); break;
                    case 9:
                        running = false;
                        System.out.println("Thank you for using the Banking Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-9.");
                }
            } catch (InvalidAccountException | InsufficientBalanceException | IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("\nDatabase error: " + e.getMessage());
            } catch (NoSuchElementException e) {
                running = false; // console input closed (Ctrl+D / end of piped input)
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("       BANKING MANAGEMENT SYSTEM");
        System.out.println("====================================");
        System.out.println();
        System.out.println("1. Create Customer");
        System.out.println("2. Create Bank Account");
        System.out.println("3. View Customer Details");
        System.out.println("4. Deposit Money");
        System.out.println("5. Withdraw Money");
        System.out.println("6. Check Account Balance");
        System.out.println("7. Transfer Money");
        System.out.println("8. View Transaction History");
        System.out.println("9. Exit");
        System.out.println();
    }

    // ------------------------------------------------------------------ menu actions

    private static void createCustomer() throws SQLException {
        String name = input.readString("Customer name : ");
        String email = input.readString("Email         : ");
        String phone = input.readString("Phone         : ");
        Customer c = bank.createCustomer(name, email, phone);
        System.out.println("\nCustomer created successfully. Customer ID: " + c.getCustomerId());
    }

    private static void createAccount() throws SQLException {
        int customerId = input.readInt("Customer ID : ");
        int type = input.readInt("Account type (1 = Savings, 2 = Current) : ");
        if (type != 1 && type != 2) {
            throw new IllegalArgumentException("Please choose 1 (Savings) or 2 (Current).");
        }
        Account a = bank.createAccount(customerId, type == 1 ? Account.SAVINGS : Account.CURRENT);
        System.out.println("\nAccount created successfully.");
        System.out.println("Account number : " + a.getAccountNumber());
        System.out.println("Account type   : " + a.getAccountType());
        System.out.println("Balance        : " + a.getBalance().toPlainString());
    }

    private static void viewCustomerDetails() throws SQLException {
        int customerId = input.readInt("Customer ID : ");
        Customer c = bank.getCustomer(customerId);
        System.out.println("\n--- Customer Details ---");
        System.out.println(c);
        List<Account> accounts = bank.getAccountsByCustomer(customerId);
        System.out.println("\n--- Accounts ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts yet.");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    private static void deposit() throws InvalidAccountException, SQLException {
        long accountNumber = input.readLong("Account number : ");
        BigDecimal amount = input.readAmount("Deposit amount : ");
        bank.deposit(accountNumber, amount);
        System.out.println("\nDeposit successful. New balance: " + bank.getBalance(accountNumber).toPlainString());
    }

    private static void withdraw() throws InvalidAccountException, InsufficientBalanceException, SQLException {
        long accountNumber = input.readLong("Account number : ");
        BigDecimal amount = input.readAmount("Withdrawal amount : ");
        bank.withdraw(accountNumber, amount);
        System.out.println("\nWithdrawal successful. New balance: " + bank.getBalance(accountNumber).toPlainString());
    }

    private static void checkBalance() throws InvalidAccountException, SQLException {
        long accountNumber = input.readLong("Account number : ");
        System.out.println("\nCurrent balance: " + bank.getBalance(accountNumber).toPlainString());
    }

    private static void transfer() throws InvalidAccountException, InsufficientBalanceException, SQLException {
        long from = input.readLong("Sender account number   : ");
        long to = input.readLong("Receiver account number : ");
        BigDecimal amount = input.readAmount("Transfer amount          : ");
        String reference = bank.transfer(from, to, amount);
        System.out.println("\nTransfer successful. Reference: " + reference);
        System.out.println("Sender balance: " + bank.getBalance(from).toPlainString());
    }

    private static void viewTransactionHistory() throws InvalidAccountException, SQLException {
        long accountNumber = input.readLong("Account number : ");
        List<Transaction> history = bank.getTransactionHistory(accountNumber);

        String filter = input.readOptional("Filter by type (DEPOSIT / WITHDRAWAL / TRANSFER, Enter = all) : ")
                .toUpperCase();

        // Lambda: keep only the requested transaction type
        List<Transaction> shown = history.stream()
                .filter(t -> filter.isEmpty() || t.getTransactionType().equals(filter))
                .collect(Collectors.toList());

        System.out.println();
        if (shown.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.printf("%-6s %-19s %-13s %14s  %s%n", "ID", "Date & Time", "Type", "Amount", "Reference");
        System.out.println("---------------------------------------------------------------------");
        shown.forEach(t -> System.out.printf("%-6d %-19s %-13s %14s  %s%n",
                t.getTransactionId(),
                t.getTransactionDate().format(DATE_TIME),
                t.getDisplayType(),
                t.getAmount().abs().toPlainString(),
                t.getTransactionReference()));

        printSummary(shown);
    }

    /** Transaction summary using the Stream API. */
    private static void printSummary(List<Transaction> list) {
        BigDecimal deposits = sum(list, t -> Transaction.DEPOSIT.equals(t.getTransactionType()));
        BigDecimal withdrawals = sum(list, t -> Transaction.WITHDRAWAL.equals(t.getTransactionType()));
        BigDecimal transfersOut = sum(list, t -> Transaction.TRANSFER.equals(t.getTransactionType())
                && t.getAmount().signum() < 0).abs();
        BigDecimal transfersIn = sum(list, t -> Transaction.TRANSFER.equals(t.getTransactionType())
                && t.getAmount().signum() > 0);

        Map<String, Long> countByType = list.stream().collect(
                Collectors.groupingBy(Transaction::getDisplayType, TreeMap::new, Collectors.counting()));

        System.out.println("\n--- Summary ---");
        System.out.println("Transactions   : " + list.size() + "  " + countByType);
        System.out.println("Total deposits : " + deposits.toPlainString());
        System.out.println("Total withdrawn: " + withdrawals.toPlainString());
        System.out.println("Transfers out  : " + transfersOut.toPlainString());
        System.out.println("Transfers in   : " + transfersIn.toPlainString());
    }

    private static BigDecimal sum(List<Transaction> list, java.util.function.Predicate<Transaction> filter) {
        return list.stream()
                .filter(filter)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
