package finance.system;

import finance.interfaces.IDataManager;
import finance.models.*;
import finance.reports.Report;
import finance.enums.*;
import finance.exceptions.FinanceException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialSystem {
    private List<Account> accounts;
    private List<Budget> budgets;
    private List<SavingsGoal> savingsGoals;
    private IDataManager dataManager;
    private int nextAccountId;
    private int nextTransactionId;
    private int nextBudgetId;
    private int nextSavingsGoalId;

    public FinancialSystem(IDataManager dataManager) {
        this.accounts = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.savingsGoals = new ArrayList<>();
        this.dataManager = dataManager;
        this.nextAccountId = 1;
        this.nextTransactionId = 1;
        this.nextBudgetId = 1;
        this.nextSavingsGoalId = 1;
    }

    public Account addAccount(String name, double balance, AccountType type) throws FinanceException {
        if (name == null || name.trim().isEmpty()) {
            throw new FinanceException("Account name cannot be empty");
        }
        Account account = new Account(nextAccountId++, name, balance, type);
        accounts.add(account);
        return account;
    }

    public void removeAccount(int accountId) throws FinanceException {
        Account account = findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }
        accounts.remove(account);
    }

    public void updateAccount(int accountId, String name, AccountType type) throws FinanceException {
        Account account = findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }
        if (name != null && !name.trim().isEmpty()) {
            account.setName(name);
        }
        if (type != null) {
            account.setType(type);
        }
    }

    public void addTransaction(int accountId, Transaction transaction) throws FinanceException {
        Account account = findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }
        
        account.addTransaction(transaction);
        
        if (transaction.getType() == TransactionType.INCOME) {
            account.deposit(transaction.getAmount());
        } else {
            account.withdraw(transaction.getAmount());
            updateBudgetUsage(transaction);
        }
    }

    public Transaction createTransaction(double amount, String description, String date,
                                        TransactionCategory category, TransactionType type) {
        return new Transaction(nextTransactionId++, amount, description, date, category, type);
    }

    public void removeTransaction(int accountId, int transactionId) throws FinanceException {
        Account account = findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }
        
        Transaction transaction = findTransactionInAccount(account, transactionId);
        if (transaction == null) {
            throw new FinanceException("Transaction not found");
        }
        
        if (transaction.getType() == TransactionType.INCOME) {
            account.withdraw(transaction.getAmount());
        } else {
            account.deposit(transaction.getAmount());
            Budget budget = findBudgetByCategory(transaction.getCategory());
            if (budget != null) {
                double newUsage = budget.getCurrentUsage() - transaction.getAmount();
                budget.resetBudget();
                if (newUsage > 0) {
                    budget.addExpense(newUsage);
                }
            }
        }
        
        account.removeTransaction(transaction);
    }

    public void updateTransaction(int accountId, int transactionId, double amount, String description) throws FinanceException {
        Account account = findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }
        
        Transaction transaction = findTransactionInAccount(account, transactionId);
        if (transaction == null) {
            throw new FinanceException("Transaction not found");
        }
        
        double oldAmount = transaction.getAmount();
        transaction.updateTransaction(amount, description);
        
        double difference = amount - oldAmount;
        if (transaction.getType() == TransactionType.INCOME) {
            if (difference > 0) {
                account.deposit(difference);
            } else {
                account.withdraw(-difference);
            }
        } else {
            if (difference > 0) {
                account.withdraw(difference);
            } else {
                account.deposit(-difference);
            }
            Budget budget = findBudgetByCategory(transaction.getCategory());
            if (budget != null) {
                budget.addExpense(difference);
            }
        }
    }

    public Budget createBudget(double limit, TransactionCategory category) throws FinanceException {
        if (limit <= 0) {
            throw new FinanceException("Budget limit must be positive");
        }
        Budget budget = new Budget(nextBudgetId++, limit, category);
        budgets.add(budget);
        return budget;
    }

    public void removeBudget(int budgetId) throws FinanceException {
        Budget budget = findBudgetById(budgetId);
        if (budget == null) {
            throw new FinanceException("Budget not found");
        }
        budgets.remove(budget);
    }

    public void updateBudget(int budgetId, double limit) throws FinanceException {
        Budget budget = findBudgetById(budgetId);
        if (budget == null) {
            throw new FinanceException("Budget not found");
        }
        if (limit <= 0) {
            throw new FinanceException("Budget limit must be positive");
        }
        budget.setLimit(limit);
    }

    public SavingsGoal createSavingsGoal(String name, double targetAmount) throws FinanceException {
        if (name == null || name.trim().isEmpty()) {
            throw new FinanceException("Savings goal name cannot be empty");
        }
        if (targetAmount <= 0) {
            throw new FinanceException("Target amount must be positive");
        }
        SavingsGoal goal = new SavingsGoal(nextSavingsGoalId++, name, targetAmount);
        savingsGoals.add(goal);
        return goal;
    }

    public void removeSavingsGoal(int goalId) throws FinanceException {
        SavingsGoal goal = findSavingsGoalById(goalId);
        if (goal == null) {
            throw new FinanceException("Savings goal not found");
        }
        savingsGoals.remove(goal);
    }

    public void updateSavingsGoal(int goalId, String name, double targetAmount) throws FinanceException {
        SavingsGoal goal = findSavingsGoalById(goalId);
        if (goal == null) {
            throw new FinanceException("Savings goal not found");
        }
        if (name != null && !name.trim().isEmpty()) {
            goal.setName(name);
        }
        if (targetAmount > 0) {
            goal.setTargetAmount(targetAmount);
        }
    }

    public void depositToSavingsGoal(int goalId, double amount) throws FinanceException {
        SavingsGoal goal = findSavingsGoalById(goalId);
        if (goal == null) {
            throw new FinanceException("Savings goal not found");
        }
        goal.deposit(amount);
    }

    public String generateReport(Report report) {
        return report.generateContent();
    }

    public List<Transaction> filterTransactions(TransactionCategory category, TransactionType type, String datePrefix) {
        List<Transaction> allTransactions = new ArrayList<>();
        for (Account account : accounts) {
            allTransactions.addAll(account.getTransactions());
        }

        return allTransactions.stream()
            .filter(t -> category == null || t.getCategory() == category)
            .filter(t -> type == null || t.getType() == type)
            .filter(t -> datePrefix == null || t.getDate().startsWith(datePrefix))
            .collect(Collectors.toList());
    }

    public void saveSystemState() {
        List<String> data = new ArrayList<>();
        data.add("FINANCIAL_SYSTEM_DATA");
        dataManager.saveData(data);
    }

    private void updateBudgetUsage(Transaction transaction) {
        if (transaction.getType() == TransactionType.EXPENSE) {
            Budget budget = findBudgetByCategory(transaction.getCategory());
            if (budget != null) {
                budget.addExpense(transaction.getAmount());
            }
        }
    }

    public Account findAccountById(int id) {
        return accounts.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public Budget findBudgetById(int id) {
        return budgets.stream()
            .filter(b -> b.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public Budget findBudgetByCategory(TransactionCategory category) {
        return budgets.stream()
            .filter(b -> b.getCategory() == category)
            .findFirst()
            .orElse(null);
    }

    public SavingsGoal findSavingsGoalById(int id) {
        return savingsGoals.stream()
            .filter(g -> g.getId() == id)
            .findFirst()
            .orElse(null);
    }

    private Transaction findTransactionInAccount(Account account, int transactionId) {
        return account.getTransactions().stream()
            .filter(t -> t.getId() == transactionId)
            .findFirst()
            .orElse(null);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public List<Budget> getBudgets() {
        return new ArrayList<>(budgets);
    }

    public List<SavingsGoal> getSavingsGoals() {
        return new ArrayList<>(savingsGoals);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (Account account : accounts) {
            allTransactions.addAll(account.getTransactions());
        }
        return allTransactions;
    }
}
