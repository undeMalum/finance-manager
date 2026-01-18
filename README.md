# Finance Manager

A comprehensive personal finance management system built in Java with a terminal-based interface.

## Project Overview

This application helps users manage their financial activities including accounts, transactions, budgets, and savings goals. It provides a complete command-line interface for personal finance tracking and reporting.

## Features

### Account Management
- Create an account (Adult, Teenager, Child)
- Edit account details
- Delete account
- View account balance

### Transaction Management
- Add income and expense transactions
- Edit and delete transactions
- Filter transactions by category, type, or date
- View complete transaction history

### Budgeting
- Create budgets for different categories
- Monitor budget usage in real-time
- Track percentage of budget used
- Receive warnings when approaching limits

### Savings Goals
- Define savings goals with target amounts
- Track progress toward goals
- Visual progress indicators
- Deposit funds to goals

### Reports
- Generate monthly financial reports
- View income vs expenses summary
- Detailed transaction breakdowns

### Data Management
- Export data to file
- Import data from file
- Persistent storage support

## Compilation Instructions (Windows 11)

### Compile Main Application
```powershell
javac -d bin -sourcepath src/main/java (Get-ChildItem -Path src/main/java -Recurse -Filter *.java).FullName
```

### Compile Tests
```powershell
javac -cp bin -d bin -sourcepath src/test/java (Get-ChildItem -Path src/test/java -Recurse -Filter *.java).FullName
```

### Run Main Application
```powershell
java -cp bin finance.Main
```

### Run Unit Tests
```powershell
java -cp bin finance.TestRunner
```

## Usage Guide

### Starting the Application

When you start the application, example data is automatically loaded to demonstrate functionality:
- 3 pre-configured accounts (Main Account, Savings Account, Teen Account)
- Sample transactions (salary, groceries, gas, entertainment)
- Example budgets for Food, Transportation, and Entertainment
- Demo savings goals (Vacation Fund, Emergency Fund)

### Main Menu Options

1. **Manage Accounts** - Create, view, edit, or delete accounts
2. **Manage Transactions** - Add, view, filter, edit, or delete transactions
3. **Manage Budgets** - Create and monitor spending budgets
4. **Manage Savings Goals** - Track progress toward financial goals
5. **Generate Monthly Report** - View detailed monthly summaries
6. **Export Data** - Save data to file
7. **Import Data** - Load data from file
0. **Exit** - Close the application

### Example Workflow

1. View existing accounts (Option 1 → 2)
2. Add a new transaction (Option 2 → 1)
3. Check budget status (Option 3 → 3)
4. View savings progress (Option 4 → 3)
5. Generate monthly report for January 2026 (Option 5)

## Testing

The application includes comprehensive unit tests for:
- Account operations (deposits, withdrawals)
- Transaction management
- Budget tracking
- Savings goal progress
- System integration tests

All tests verify:
- Normal operation
- Error conditions
- Edge cases
- Data integrity

Run the tests using `java -cp bin finance.TestRunner` to see all test results.
