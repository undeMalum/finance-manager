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
