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
    private Account account;
    private List<Budget> budgets;
    private List<SavingsGoal> savingsGoals;
    private IDataManager dataManager;
    private int nextTransactionId;
    private int nextBudgetId;
    private int nextSavingsGoalId;

    public FinancialSystem(IDataManager dataManager) {
        this.account = null;
        this.budgets = new ArrayList<>();
        this.savingsGoals = new ArrayList<>();
        this.dataManager = dataManager;
        this.nextTransactionId = 1;
        this.nextBudgetId = 1;
        this.nextSavingsGoalId = 1;
    }

    /** 
     * @param name
     * @param balance
     * @param type
     * @throws FinanceException
     */
    public void createAccount(String name, double balance, AccountType type) throws FinanceException {
        if (name == null || name.trim().isEmpty()) {
            throw new FinanceException("Account name cannot be empty");
        }
        if (account != null) {
            throw new FinanceException("Account already exists. Only one account is allowed.");
        }
        this.account = new Account(1, name, balance, type);
    }

    /** 
     * @param name
     * @param type
     * @throws FinanceException
     */
    public void updateAccount(String name, AccountType type) throws FinanceException {
        if (account == null) {
            throw new FinanceException("No account exists");
        }
        if (name != null && !name.trim().isEmpty()) {
            account.setName(name);
        }
        if (type != null) {
            account.setType(type);
        }
    }

    /** 
     * @return Account
     */
    public Account getAccount() {
        return account;
    }

    /** 
     * @return boolean
     */
    public boolean hasAccount() {
        return account != null;
    }

    /** 
     * @param accountId
     * @param transaction
     * @throws FinanceException
     */
    public void addTransaction(Transaction transaction) throws FinanceException {
        if (account == null) {
            throw new FinanceException("No account exists");
        }
        
        account.addTransaction(transaction);
        
        if (transaction.getType() == TransactionType.INCOME) {
            account.deposit(transaction.getAmount());
        } else {
            account.withdraw(transaction.getAmount());
            updateBudgetUsage(transaction);
        }
    }

    /** 
     * @param amount
     * @param description
     * @param date
     * @param category
     * @param type
     * @return Transaction
     */
    public Transaction createTransaction(double amount, String description, String date,
                                        TransactionCategory category, TransactionType type) {
        return new Transaction(nextTransactionId++, amount, description, date, category, type);
    }

    /** 
     * @param transactionId
     * @throws FinanceException
     */
    public void removeTransaction(int transactionId) throws FinanceException {
        if (account == null) {
            throw new FinanceException("No account exists");
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

    /** 
     * @param transactionId
     * @param amount
     * @param description
     * @throws FinanceException
     */
    public void updateTransaction(int transactionId, double amount, String description) throws FinanceException {
        if (account == null) {
            throw new FinanceException("No account exists");
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

    /** 
     * @param limit
     * @param category
     * @return Budget
     * @throws FinanceException
     */
    public Budget createBudget(double limit, TransactionCategory category) throws FinanceException {
        if (limit <= 0) {
            throw new FinanceException("Budget limit must be positive");
        }
        Budget budget = new Budget(nextBudgetId++, limit, category);
        budgets.add(budget);
        return budget;
    }

    /** 
     * @param budgetId
     * @throws FinanceException
     */
    public void removeBudget(int budgetId) throws FinanceException {
        Budget budget = findBudgetById(budgetId);
        if (budget == null) {
            throw new FinanceException("Budget not found");
        }
        budgets.remove(budget);
    }

    /** 
     * @param budgetId
     * @param limit
     * @throws FinanceException
     */
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

    /** 
     * @param name
     * @param targetAmount
     * @return SavingsGoal
     * @throws FinanceException
     */
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

    /** 
     * @param goalId
     * @throws FinanceException
     */
    public void removeSavingsGoal(int goalId) throws FinanceException {
        SavingsGoal goal = findSavingsGoalById(goalId);
        if (goal == null) {
            throw new FinanceException("Savings goal not found");
        }
        savingsGoals.remove(goal);
    }

    /** 
     * @param goalId
     * @param name
     * @param targetAmount
     * @throws FinanceException
     */
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

    /** 
     * @param goalId
     * @param amount
     * @throws FinanceException
     */
    public void depositToSavingsGoal(int goalId, double amount) throws FinanceException {
        SavingsGoal goal = findSavingsGoalById(goalId);
        if (goal == null) {
            throw new FinanceException("Savings goal not found");
        }
        goal.deposit(amount);
    }

    /** 
     * @param report
     * @return String
     */
    public String generateReport(Report report) {
        return report.generateContent();
    }

    /** 
     * @param category
     * @param type
     * @param datePrefix
     * @return List<Transaction>
     */
    public List<Transaction> filterTransactions(TransactionCategory category, TransactionType type, String datePrefix) {
        if (account == null) {
            return new ArrayList<>();
        }
        
        List<Transaction> allTransactions = account.getTransactions();

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

    /** 
     * @param transaction
     */
    private void updateBudgetUsage(Transaction transaction) {
        if (transaction.getType() == TransactionType.EXPENSE) {
            Budget budget = findBudgetByCategory(transaction.getCategory());
            if (budget != null) {
                budget.addExpense(transaction.getAmount());
            }
        }
    }

    /** 
     * @param id
     * @return Budget
     */
    public Budget findBudgetById(int id) {
        return budgets.stream()
            .filter(b -> b.getId() == id)
            .findFirst()
            .orElse(null);
    }

    /** 
     * @param category
     * @return Budget
     */
    public Budget findBudgetByCategory(TransactionCategory category) {
        return budgets.stream()
            .filter(b -> b.getCategory() == category)
            .findFirst()
            .orElse(null);
    }

    /** 
     * @param id
     * @return SavingsGoal
     */
    public SavingsGoal findSavingsGoalById(int id) {
        return savingsGoals.stream()
            .filter(g -> g.getId() == id)
            .findFirst()
            .orElse(null);
    }

    /** 
     * @param account
     * @param transactionId
     * @return Transaction
     */
    private Transaction findTransactionInAccount(Account account, int transactionId) {
        return account.getTransactions().stream()
            .filter(t -> t.getId() == transactionId)
            .findFirst()
            .orElse(null);
    }

    /** 
     * @return List<Budget>
     */
    public List<Budget> getBudgets() {
        return new ArrayList<>(budgets);
    }

    /** 
     * @return List<SavingsGoal>
     */
    public List<SavingsGoal> getSavingsGoals() {
        return new ArrayList<>(savingsGoals);
    }

    /** 
     * @return List<Transaction>
     */
    public List<Transaction> getAllTransactions() {
        if (account == null) {
            return new ArrayList<>();
        }
        return account.getTransactions();
    }
}
