<!-- TOC start (generated with https://github.com/derlin/bitdowntoc) -->

- [Finance Manager](#finance-manager)
   * [Project Overview](#project-overview)
   * [Features](#features)
      + [Account Management](#account-management)
      + [Transaction Management](#transaction-management)
      + [Budgeting](#budgeting)
      + [Savings Goals](#savings-goals)
      + [Reports](#reports)
      + [Data Management](#data-management)
   * [Compilation Instructions (Windows 11)](#compilation-instructions-windows-11)
      + [Compile Main Application](#compile-main-application)
      + [Compile Tests](#compile-tests)
      + [Run Main Application](#run-main-application)
      + [Run Unit Tests](#run-unit-tests)
   * [Usage Guide](#usage-guide)
      + [Starting the Application](#starting-the-application)
      + [Main Menu Options](#main-menu-options)
      + [Example Workflow](#example-workflow)
   * [Testing](#testing)
   * [Docs](#docs)
   * [Configuration](#configuration)
   * [Authors](#authors)

<!-- TOC end -->

<!-- TOC --><a name="finance-manager"></a>
# Finance Manager

A comprehensive personal finance management system built in Java with a terminal-based interface.

<!-- TOC --><a name="project-overview"></a>
## Project Overview

This application helps users manage their financial activities including accounts, transactions, budgets, and savings goals. It provides a complete command-line interface for personal finance tracking and reporting.

<!-- TOC --><a name="features"></a>
## Features

<!-- TOC --><a name="account-management"></a>
### Account Management
- Create an account (Adult, Teenager, Child)
- Edit account details
- Delete account
- View account balance

<!-- TOC --><a name="transaction-management"></a>
### Transaction Management
- Add income and expense transactions
- Edit and delete transactions
- Filter transactions by category, type, or date
- View complete transaction history

<!-- TOC --><a name="budgeting"></a>
### Budgeting
- Create budgets for different categories
- Monitor budget usage in real-time
- Track percentage of budget used
- Receive warnings when approaching limits

<!-- TOC --><a name="savings-goals"></a>
### Savings Goals
- Define savings goals with target amounts
- Track progress toward goals
- Visual progress indicators
- Deposit funds to goals

<!-- TOC --><a name="reports"></a>
### Reports
- Generate monthly financial reports
- View income vs expenses summary
- Detailed transaction breakdowns

<!-- TOC --><a name="data-management"></a>
### Data Management
- Export data to file
- Import data from file
- Persistent storage support

<!-- TOC --><a name="compilation-instructions-windows-11"></a>
## Compilation Instructions (Windows 11)

<!-- TOC --><a name="compile-main-application"></a>
### Compile Main Application
```powershell
javac -d bin -sourcepath src/main/java (Get-ChildItem -Path src/main/java -Recurse -Filter *.java).FullName
```

<!-- TOC --><a name="compile-tests"></a>
### Compile Tests
```powershell
javac -cp bin -d bin -sourcepath src/test/java (Get-ChildItem -Path src/test/java -Recurse -Filter *.java).FullName
```

<!-- TOC --><a name="run-main-application"></a>
### Run Main Application
```powershell
java -cp bin finance.Main
```

<!-- TOC --><a name="run-unit-tests"></a>
### Run Unit Tests
```powershell
java -cp bin finance.TestRunner
```

<!-- TOC --><a name="usage-guide"></a>
## Usage Guide

<!-- TOC --><a name="starting-the-application"></a>
### Starting the Application

When you start the application, example data is automatically loaded to demonstrate functionality:
- 3 pre-configured accounts (Main Account, Savings Account, Teen Account)
- Sample transactions (salary, groceries, gas, entertainment)
- Example budgets for Food, Transportation, and Entertainment
- Demo savings goals (Vacation Fund, Emergency Fund)

<!-- TOC --><a name="main-menu-options"></a>
### Main Menu Options

1. **Manage Accounts** - Create, view, edit, or delete accounts
2. **Manage Transactions** - Add, view, filter, edit, or delete transactions
3. **Manage Budgets** - Create and monitor spending budgets
4. **Manage Savings Goals** - Track progress toward financial goals
5. **Generate Monthly Report** - View detailed monthly summaries
6. **Export Data** - Save data to file
7. **Import Data** - Load data from file
0. **Exit** - Close the application

<!-- TOC --><a name="example-workflow"></a>
### Example Workflow

1. View existing accounts (Option 1 → 2)
2. Add a new transaction (Option 2 → 1)
3. Check budget status (Option 3 → 3)
4. View savings progress (Option 4 → 3)
5. Generate monthly report for January 2026 (Option 5)

<!-- TOC --><a name="testing"></a>
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

<!-- TOC --><a name="docs"></a>
## Docs

You can view project documentation [here](https://undemalum.github.io/finance-manager/).

<!-- TOC --><a name="configuration"></a>
## Configuration

Default settings in `AppConfiguration`:
- Default Currency: USD
- Maximum Budgets: 10

These can be modified programmatically through the configuration class.

<!-- TOC --><a name="authors"></a>
## Authors
- Mateusz Konat - 164200
- Mateusz Tyburski - 165545
- Mikołaj Strugarek - 166427
