# Example Data Files

This directory contains pre-prepared example data files in JSON format to demonstrate the Financial Manager application.

## Available Files

### 1. example_data.json
Complete example dataset with:
- 4 accounts (Main, Savings, Teen, Emergency Fund)
- 8 transactions (various categories and types)
- 4 budgets (Food, Transportation, Entertainment, Other)
- 4 savings goals (Vacation, Emergency, Laptop, Car)

### 2. sample_minimal.json
Minimal example dataset with:
- 1 account
- 2 transactions
- 1 budget
- 1 savings goal

## File Format

JSON format with four main sections:

```json
{
  "accounts": [...],
  "transactions": [...],
  "budgets": [...],
  "savingsGoals": [...]
}
```

### Account Structure
```json
{
  "id": 1,
  "name": "Account Name",
  "balance": 1000.0,
  "type": "ADULT|TEENAGER|CHILD"
}
```

### Transaction Structure
```json
{
  "id": 1,
  "accountId": 1,
  "amount": 100.0,
  "description": "Description",
  "date": "YYYY-MM-DD",
  "category": "FOOD|TRANSPORTATION|ENTERTAINMENT|OTHER",
  "type": "INCOME|EXPENSE"
}
```

### Budget Structure
```json
{
  "id": 1,
  "limit": 500.0,
  "currentUsage": 250.0,
  "category": "FOOD|TRANSPORTATION|ENTERTAINMENT|OTHER"
}
```

### Savings Goal Structure
```json
{
  "id": 1,
  "name": "Goal Name",
  "targetAmount": 5000.0,
  "currentAmount": 1500.0
}
```

## Usage

To load example data in the application:
1. Run the application: `java -cp bin finance.Main`
2. Select option 7: "Import Data"
3. Enter the file path: `data/example_data.json`

The application will load all accounts, transactions, budgets, and savings goals from the file.
