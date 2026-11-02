-- Banking Management System: database schema (MySQL 8)
CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

CREATE TABLE IF NOT EXISTS customers (
    customer_id   INT           NOT NULL AUTO_INCREMENT,
    customer_name VARCHAR(100)  NOT NULL,
    email         VARCHAR(100)  NOT NULL,
    phone         VARCHAR(15)   NOT NULL,
    PRIMARY KEY (customer_id),
    UNIQUE KEY uk_customers_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS accounts (
    account_number BIGINT        NOT NULL,
    customer_id    INT           NOT NULL,
    account_type   VARCHAR(20)   NOT NULL,
    balance        DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    created_date   DATE          NOT NULL,
    PRIMARY KEY (account_number),
    CONSTRAINT fk_accounts_customer FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    CONSTRAINT chk_accounts_type    CHECK (account_type IN ('SAVINGS', 'CURRENT')),
    CONSTRAINT chk_accounts_balance CHECK (balance >= 0)
) ENGINE=InnoDB;

-- TRANSFER rows are signed: negative = debit (sender), positive = credit (receiver);
-- both rows of one transfer share the same transaction_reference.
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id        INT           NOT NULL AUTO_INCREMENT,
    account_number        BIGINT        NOT NULL,
    transaction_type      VARCHAR(20)   NOT NULL,
    amount                DECIMAL(12,2) NOT NULL,
    transaction_reference VARCHAR(50)   NOT NULL,
    transaction_date      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (transaction_id),
    CONSTRAINT fk_transactions_account FOREIGN KEY (account_number) REFERENCES accounts (account_number),
    CONSTRAINT chk_transactions_type   CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),
    INDEX idx_transactions_account (account_number, transaction_date)
) ENGINE=InnoDB;
