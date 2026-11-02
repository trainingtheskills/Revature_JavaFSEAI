# Banking Management System (Core Java 8 + JDBC + MySQL)

## Setup
1. Run `sql/schema.sql` in MySQL (creates `banking_db` and its tables).
2. Put your MySQL user and password in `resources/db.properties`.
3. Run: `mvn -q compile exec:java`

## Notes
- Transfers use one connection with auto-commit off; commit on success, rollback on any failure.
  Rows are locked with `SELECT ... FOR UPDATE` in ascending account order to avoid deadlocks.
- Deposits and withdrawals are also transactional (balance update + history row together).
- A transfer writes two TRANSFER rows with the same reference: negative amount for the sender,
  positive for the receiver.
