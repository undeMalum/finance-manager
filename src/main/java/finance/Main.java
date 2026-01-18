package finance;

import finance.config.AppConfiguration;
import finance.enums.*;
import finance.exceptions.FinanceException;
import finance.managers.FileManager;
import finance.models.*;
import finance.reports.MonthlyReport;
import finance.system.FinancialSystem;
import finance.utils.JsonUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static FinancialSystem system;
    private static Scanner scanner;

    /** 
     * @param args
     */
    public static void main(String[] args) {
        AppConfiguration.loadConfiguration();
        FileManager fileManager = new FileManager("financial_data.txt");
        system = new FinancialSystem(fileManager);
        scanner = new Scanner(System.in);

        loadExampleData();

        System.out.println("Welcome to Financial Manager!");
        System.out.println("Currency: " + AppConfiguration.getDefaultCurrency());

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        manageAccounts();
                        break;
                    case 2:
                        manageTransactions();
                        break;
                    case 3:
                        manageBudgets();
                        break;
                    case 4:
                        manageSavingsGoals();
                        break;
                    case 5:
                        generateMonthlyReport();
                        break;
                    case 6:
                        exportData();
                        break;
                    case 7:
                        importData();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using Financial Manager!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (FinanceException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("           MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Manage Accounts");
        System.out.println("2. Manage Transactions");
        System.out.println("3. Manage Budgets");
        System.out.println("4. Manage Savings Goals");
        System.out.println("5. Generate Monthly Report");
        System.out.println("6. Export Data");
        System.out.println("7. Import Data");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    /** 
     * @throws FinanceException
     */
    private static void manageAccounts() throws FinanceException {
        System.out.println("\n--- ACCOUNT MANAGEMENT ---");
        System.out.println("1. Add Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Edit Account");
        System.out.println("4. Delete Account");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addAccount();
                break;
            case 2:
                viewAllAccounts();
                break;
            case 3:
                editAccount();
                break;
            case 4:
                deleteAccount();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void addAccount() throws FinanceException {
        System.out.println("\n--- ADD ACCOUNT ---");
        String name = getStringInput("Enter account name: ");
        double balance = getDoubleInput("Enter initial balance: ");

        System.out.println("Select account type:");
        System.out.println("1. ADULT");
        System.out.println("2. TEENAGER");
        System.out.println("3. CHILD");
        int typeChoice = getIntInput("Enter choice: ");

        AccountType type;
        switch (typeChoice) {
            case 1:
                type = AccountType.ADULT;
                break;
            case 2:
                type = AccountType.TEENAGER;
                break;
            case 3:
                type = AccountType.CHILD;
                break;
            default:
                type = AccountType.ADULT;
        }

        Account account = system.addAccount(name, balance, type);
        System.out.println("Account created successfully! ID: " + account.getId());
    }

    private static void viewAllAccounts() {
        System.out.println("\n--- ALL ACCOUNTS ---");
        List<Account> accounts = system.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (Account account : accounts) {
            System.out.println("ID: " + account.getId() + 
                             " | Name: " + account.getName() + 
                             " | Balance: $" + String.format("%.2f", account.getBalance()) + 
                             " | Type: " + account.getType());
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void editAccount() throws FinanceException {
        viewAllAccounts();
        int id = getIntInput("Enter account ID to edit: ");
        String name = getStringInput("Enter new name (or press Enter to skip): ");
        
        System.out.println("Select new account type (or 0 to skip):");
        System.out.println("1. ADULT");
        System.out.println("2. TEENAGER");
        System.out.println("3. CHILD");
        int typeChoice = getIntInput("Enter choice: ");

        AccountType type = null;
        switch (typeChoice) {
            case 1:
                type = AccountType.ADULT;
                break;
            case 2:
                type = AccountType.TEENAGER;
                break;
            case 3:
                type = AccountType.CHILD;
                break;
        }

        system.updateAccount(id, name, type);
        System.out.println("Account updated successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void deleteAccount() throws FinanceException {
        viewAllAccounts();
        int id = getIntInput("Enter account ID to delete: ");
        system.removeAccount(id);
        System.out.println("Account deleted successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void manageTransactions() throws FinanceException {
        System.out.println("\n--- TRANSACTION MANAGEMENT ---");
        System.out.println("1. Add Transaction");
        System.out.println("2. View All Transactions");
        System.out.println("3. Filter Transactions");
        System.out.println("4. Edit Transaction");
        System.out.println("5. Delete Transaction");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addTransaction();
                break;
            case 2:
                viewAllTransactions();
                break;
            case 3:
                filterTransactions();
                break;
            case 4:
                editTransaction();
                break;
            case 5:
                deleteTransaction();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void addTransaction() throws FinanceException {
        viewAllAccounts();
        int accountId = getIntInput("Enter account ID: ");

        double amount = getDoubleInput("Enter amount: ");
        String description = getStringInput("Enter description: ");
        String date = getStringInput("Enter date (YYYY-MM-DD): ");

        System.out.println("Select category:");
        System.out.println("1. FOOD");
        System.out.println("2. TRANSPORTATION");
        System.out.println("3. ENTERTAINMENT");
        System.out.println("4. OTHER");
        int catChoice = getIntInput("Enter choice: ");

        TransactionCategory category;
        switch (catChoice) {
            case 1:
                category = TransactionCategory.FOOD;
                break;
            case 2:
                category = TransactionCategory.TRANSPORTATION;
                break;
            case 3:
                category = TransactionCategory.ENTERTAINMENT;
                break;
            default:
                category = TransactionCategory.OTHER;
        }

        System.out.println("Select type:");
        System.out.println("1. INCOME");
        System.out.println("2. EXPENSE");
        int typeChoice = getIntInput("Enter choice: ");

        TransactionType type = (typeChoice == 1) ? TransactionType.INCOME : TransactionType.EXPENSE;

        Transaction transaction = system.createTransaction(amount, description, date, category, type);
        system.addTransaction(accountId, transaction);
        System.out.println("Transaction added successfully! ID: " + transaction.getId());
    }

    private static void viewAllTransactions() {
        System.out.println("\n--- ALL TRANSACTIONS ---");
        List<Transaction> transactions = system.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction t : transactions) {
            System.out.println("ID: " + t.getId() + 
                             " | Date: " + t.getDate() + 
                             " | Type: " + t.getType() + 
                             " | Category: " + t.getCategory() + 
                             " | Amount: $" + String.format("%.2f", t.getAmount()) + 
                             " | Description: " + t.getDescription());
        }
    }

    private static void filterTransactions() {
        System.out.println("\n--- FILTER TRANSACTIONS ---");
        System.out.println("Select filter type:");
        System.out.println("1. By Category");
        System.out.println("2. By Type");
        System.out.println("3. By Date");
        System.out.println("4. All Filters");

        int choice = getIntInput("Enter choice: ");

        TransactionCategory category = null;
        TransactionType type = null;
        String datePrefix = null;

        if (choice == 1 || choice == 4) {
            System.out.println("Select category:");
            System.out.println("1. FOOD");
            System.out.println("2. TRANSPORTATION");
            System.out.println("3. ENTERTAINMENT");
            System.out.println("4. OTHER");
            int catChoice = getIntInput("Enter choice: ");
            switch (catChoice) {
                case 1:
                    category = TransactionCategory.FOOD;
                    break;
                case 2:
                    category = TransactionCategory.TRANSPORTATION;
                    break;
                case 3:
                    category = TransactionCategory.ENTERTAINMENT;
                    break;
                case 4:
                    category = TransactionCategory.OTHER;
                    break;
            }
        }

        if (choice == 2 || choice == 4) {
            System.out.println("Select type:");
            System.out.println("1. INCOME");
            System.out.println("2. EXPENSE");
            int typeChoice = getIntInput("Enter choice: ");
            type = (typeChoice == 1) ? TransactionType.INCOME : TransactionType.EXPENSE;
        }

        if (choice == 3 || choice == 4) {
            datePrefix = getStringInput("Enter date prefix (e.g., 2026-01): ");
        }

        List<Transaction> filtered = system.filterTransactions(category, type, datePrefix);
        
        System.out.println("\n--- FILTERED TRANSACTIONS (" + filtered.size() + " found) ---");
        if (filtered.isEmpty()) {
            System.out.println("No transactions match the filter.");
        } else {
            for (Transaction t : filtered) {
                System.out.println("ID: " + t.getId() + 
                                 " | Date: " + t.getDate() + 
                                 " | Type: " + t.getType() + 
                                 " | Category: " + t.getCategory() + 
                                 " | Amount: $" + String.format("%.2f", t.getAmount()) + 
                                 " | Description: " + t.getDescription());
            }
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void editTransaction() throws FinanceException {
        viewAllAccounts();
        int accountId = getIntInput("Enter account ID: ");
        
        Account account = system.findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }

        List<Transaction> transactions = account.getTransactions();
        System.out.println("\nTransactions for account " + account.getName() + ":");
        for (Transaction t : transactions) {
            System.out.println("ID: " + t.getId() + " | " + t.getDescription() + " | $" + t.getAmount());
        }

        int transactionId = getIntInput("Enter transaction ID to edit: ");
        double amount = getDoubleInput("Enter new amount: ");
        String description = getStringInput("Enter new description: ");

        system.updateTransaction(accountId, transactionId, amount, description);
        System.out.println("Transaction updated successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void deleteTransaction() throws FinanceException {
        viewAllAccounts();
        int accountId = getIntInput("Enter account ID: ");
        
        Account account = system.findAccountById(accountId);
        if (account == null) {
            throw new FinanceException("Account not found");
        }

        List<Transaction> transactions = account.getTransactions();
        System.out.println("\nTransactions for account " + account.getName() + ":");
        for (Transaction t : transactions) {
            System.out.println("ID: " + t.getId() + " | " + t.getDescription() + " | $" + t.getAmount());
        }

        int transactionId = getIntInput("Enter transaction ID to delete: ");
        system.removeTransaction(accountId, transactionId);
        System.out.println("Transaction deleted successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void manageBudgets() throws FinanceException {
        System.out.println("\n--- BUDGET MANAGEMENT ---");
        System.out.println("1. Create Budget");
        System.out.println("2. View All Budgets");
        System.out.println("3. Monitor Budget Usage");
        System.out.println("4. Update Budget");
        System.out.println("5. Delete Budget");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                createBudget();
                break;
            case 2:
                viewAllBudgets();
                break;
            case 3:
                monitorBudgetUsage();
                break;
            case 4:
                updateBudget();
                break;
            case 5:
                deleteBudget();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void createBudget() throws FinanceException {
        System.out.println("\n--- CREATE BUDGET ---");
        double limit = getDoubleInput("Enter budget limit: ");

        System.out.println("Select category:");
        System.out.println("1. FOOD");
        System.out.println("2. TRANSPORTATION");
        System.out.println("3. ENTERTAINMENT");
        System.out.println("4. OTHER");
        int catChoice = getIntInput("Enter choice: ");

        TransactionCategory category;
        switch (catChoice) {
            case 1:
                category = TransactionCategory.FOOD;
                break;
            case 2:
                category = TransactionCategory.TRANSPORTATION;
                break;
            case 3:
                category = TransactionCategory.ENTERTAINMENT;
                break;
            default:
                category = TransactionCategory.OTHER;
        }

        Budget budget = system.createBudget(limit, category);
        System.out.println("Budget created successfully! ID: " + budget.getId());
    }

    private static void viewAllBudgets() {
        System.out.println("\n--- ALL BUDGETS ---");
        List<Budget> budgets = system.getBudgets();

        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
            return;
        }

        for (Budget budget : budgets) {
            System.out.println("ID: " + budget.getId() + 
                             " | Category: " + budget.getCategory() + 
                             " | Limit: $" + String.format("%.2f", budget.getLimit()) + 
                             " | Used: $" + String.format("%.2f", budget.getCurrentUsage()) + 
                             " | Remaining: $" + String.format("%.2f", budget.checkStatus()) + 
                             " | Usage: " + String.format("%.1f", budget.getPercentageUsed()) + "%");
        }
    }

    private static void monitorBudgetUsage() {
        System.out.println("\n--- BUDGET USAGE MONITORING ---");
        List<Budget> budgets = system.getBudgets();

        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
            return;
        }

        for (Budget budget : budgets) {
            double percentage = budget.getPercentageUsed();
            String status;

            if (percentage >= 100) {
                status = "EXCEEDED";
            } else if (percentage >= 80) {
                status = "WARNING";
            } else {
                status = "OK";
            }

            System.out.println("\nCategory: " + budget.getCategory());
            System.out.println("Limit: $" + String.format("%.2f", budget.getLimit()));
            System.out.println("Current Usage: $" + String.format("%.2f", budget.getCurrentUsage()));
            System.out.println("Remaining: $" + String.format("%.2f", budget.checkStatus()));
            System.out.println("Usage: " + String.format("%.1f", percentage) + "%");
            System.out.println("Status: " + status);
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void updateBudget() throws FinanceException {
        viewAllBudgets();
        int id = getIntInput("Enter budget ID to update: ");
        double limit = getDoubleInput("Enter new limit: ");
        system.updateBudget(id, limit);
        System.out.println("Budget updated successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void deleteBudget() throws FinanceException {
        viewAllBudgets();
        int id = getIntInput("Enter budget ID to delete: ");
        system.removeBudget(id);
        System.out.println("Budget deleted successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void manageSavingsGoals() throws FinanceException {
        System.out.println("\n--- SAVINGS GOALS MANAGEMENT ---");
        System.out.println("1. Create Savings Goal");
        System.out.println("2. View All Savings Goals");
        System.out.println("3. View Goal Progress");
        System.out.println("4. Deposit to Goal");
        System.out.println("5. Update Goal");
        System.out.println("6. Delete Goal");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                createSavingsGoal();
                break;
            case 2:
                viewAllSavingsGoals();
                break;
            case 3:
                viewGoalProgress();
                break;
            case 4:
                depositToGoal();
                break;
            case 5:
                updateSavingsGoal();
                break;
            case 6:
                deleteSavingsGoal();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void createSavingsGoal() throws FinanceException {
        System.out.println("\n--- CREATE SAVINGS GOAL ---");
        String name = getStringInput("Enter goal name: ");
        double targetAmount = getDoubleInput("Enter target amount: ");

        SavingsGoal goal = system.createSavingsGoal(name, targetAmount);
        System.out.println("Savings goal created successfully! ID: " + goal.getId());
    }

    private static void viewAllSavingsGoals() {
        System.out.println("\n--- ALL SAVINGS GOALS ---");
        List<SavingsGoal> goals = system.getSavingsGoals();

        if (goals.isEmpty()) {
            System.out.println("No savings goals found.");
            return;
        }

        for (SavingsGoal goal : goals) {
            System.out.println("ID: " + goal.getId() + 
                             " | Name: " + goal.getName() + 
                             " | Target: $" + String.format("%.2f", goal.getTargetAmount()) + 
                             " | Current: $" + String.format("%.2f", goal.getCurrentAmount()) + 
                             " | Progress: " + String.format("%.1f", goal.getProgress()) + "%" + 
                             " | Completed: " + (goal.isCompleted() ? "Yes" : "No"));
        }
    }

    private static void viewGoalProgress() {
        System.out.println("\n--- SAVINGS GOAL PROGRESS ---");
        List<SavingsGoal> goals = system.getSavingsGoals();

        if (goals.isEmpty()) {
            System.out.println("No savings goals found.");
            return;
        }

        for (SavingsGoal goal : goals) {
            System.out.println("\n" + goal.getName() + ":");
            System.out.println("Target: $" + String.format("%.2f", goal.getTargetAmount()));
            System.out.println("Current: $" + String.format("%.2f", goal.getCurrentAmount()));
            System.out.println("Remaining: $" + String.format("%.2f", goal.getRemainingAmount()));
            System.out.println("Progress: " + String.format("%.1f", goal.getProgress()) + "%");
            
            int progressBars = (int) (goal.getProgress() / 5);
            System.out.print("[");
            for (int i = 0; i < 20; i++) {
                if (i < progressBars) {
                    System.out.print("=");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("]");
            
            if (goal.isCompleted()) {
                System.out.println("Status: COMPLETED!");
            } else {
                System.out.println("Status: In Progress");
            }
        }
    }

    /** 
     * @throws FinanceException
     */
    private static void depositToGoal() throws FinanceException {
        viewAllSavingsGoals();
        int id = getIntInput("Enter goal ID: ");
        double amount = getDoubleInput("Enter deposit amount: ");
        system.depositToSavingsGoal(id, amount);
        System.out.println("Deposit successful!");
    }

    /** 
     * @throws FinanceException
     */
    private static void updateSavingsGoal() throws FinanceException {
        viewAllSavingsGoals();
        int id = getIntInput("Enter goal ID to update: ");
        String name = getStringInput("Enter new name (or press Enter to skip): ");
        System.out.print("Enter new target amount (or 0 to skip): ");
        double targetAmount = getDoubleInput("");
        
        system.updateSavingsGoal(id, name, targetAmount);
        System.out.println("Savings goal updated successfully!");
    }

    /** 
     * @throws FinanceException
     */
    private static void deleteSavingsGoal() throws FinanceException {
        viewAllSavingsGoals();
        int id = getIntInput("Enter goal ID to delete: ");
        system.removeSavingsGoal(id);
        System.out.println("Savings goal deleted successfully!");
    }

    private static void generateMonthlyReport() {
        System.out.println("\n--- GENERATE MONTHLY REPORT ---");
        int month = getIntInput("Enter month (1-12): ");
        int year = getIntInput("Enter year: ");

        MonthlyReport report = new MonthlyReport(month, year, system);
        String content = system.generateReport(report);
        System.out.println("\n" + content);
    }

    private static void exportData() {
        System.out.println("\n--- EXPORT DATA ---");
        String filename = getStringInput("Enter filename (default: data/export.json): ");
        if (filename.trim().isEmpty()) {
            filename = "data/export.json";
        }
        
        try {
            String jsonData = JsonUtil.exportToJson(system);
            java.nio.file.Files.writeString(java.nio.file.Path.of(filename), jsonData);
            System.out.println("Data exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    private static void importData() {
        System.out.println("\n--- IMPORT DATA ---");
        System.out.println("Available example files:");
        System.out.println("  - data/example_data.json (complete dataset)");
        System.out.println("  - data/sample_minimal.json (minimal dataset)");
        System.out.println();
        
        String filename = getStringInput("Enter filename to import: ");
        if (filename.trim().isEmpty()) {
            System.out.println("Import cancelled.");
            return;
        }
        
        try {
            Map<String, Object> data = JsonUtil.importFromJson(filename);
            loadDataFromMap(data);
            System.out.println("Data imported successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error importing data: " + e.getMessage());
        }
    }
    
    /** 
     * @param data
     * @throws FinanceException
     */
    private static void loadDataFromMap(Map<String, Object> data) throws FinanceException {
        List<Map<String, String>> accounts = (List<Map<String, String>>) data.get("accounts");
        List<Map<String, String>> transactions = (List<Map<String, String>>) data.get("transactions");
        List<Map<String, String>> budgets = (List<Map<String, String>>) data.get("budgets");
        List<Map<String, String>> savingsGoals = (List<Map<String, String>>) data.get("savingsGoals");
        
        if (accounts != null) {
            for (Map<String, String> accData : accounts) {
                int id = Integer.parseInt(accData.get("id"));
                String name = accData.get("name");
                double balance = Double.parseDouble(accData.get("balance"));
                AccountType type = JsonUtil.parseAccountType(accData.get("type"));
                system.addAccount(name, balance, type);
            }
        }
        
        if (transactions != null) {
            for (Map<String, String> transData : transactions) {
                int accountId = 1;
                double amount = Double.parseDouble(transData.get("amount"));
                String description = transData.get("description");
                String date = transData.get("date");
                TransactionCategory category = JsonUtil.parseTransactionCategory(transData.get("category"));
                TransactionType type = JsonUtil.parseTransactionType(transData.get("type"));
                
                Transaction t = system.createTransaction(amount, description, date, category, type);
                if (system.getAccounts().size() > 0) {
                    system.addTransaction(system.getAccounts().get(0).getId(), t);
                }
            }
        }
        
        if (budgets != null) {
            for (Map<String, String> budgetData : budgets) {
                double limit = Double.parseDouble(budgetData.get("limit"));
                TransactionCategory category = JsonUtil.parseTransactionCategory(budgetData.get("category"));
                Budget budget = system.createBudget(limit, category);
                
                if (budgetData.containsKey("currentUsage")) {
                    double usage = Double.parseDouble(budgetData.get("currentUsage"));
                    budget.addExpense(usage);
                }
            }
        }
        
        if (savingsGoals != null) {
            for (Map<String, String> goalData : savingsGoals) {
                String name = goalData.get("name");
                double targetAmount = Double.parseDouble(goalData.get("targetAmount"));
                SavingsGoal goal = system.createSavingsGoal(name, targetAmount);
                
                if (goalData.containsKey("currentAmount")) {
                    double currentAmount = Double.parseDouble(goalData.get("currentAmount"));
                    if (currentAmount > 0) {
                        system.depositToSavingsGoal(goal.getId(), currentAmount);
                    }
                }
            }
        }
    }

    private static void loadExampleData() {
        try {
            Account mainAccount = system.addAccount("Main Account", 5000.0, AccountType.ADULT);
            Account savingsAccount = system.addAccount("Savings Account", 10000.0, AccountType.ADULT);
            Account teenAccount = system.addAccount("Teen Account", 500.0, AccountType.TEENAGER);

            Transaction t1 = system.createTransaction(3000.0, "Monthly Salary", "2026-01-01", 
                                                     TransactionCategory.OTHER, TransactionType.INCOME);
            system.addTransaction(mainAccount.getId(), t1);

            Transaction t2 = system.createTransaction(250.0, "Grocery Shopping", "2026-01-05", 
                                                     TransactionCategory.FOOD, TransactionType.EXPENSE);
            system.addTransaction(mainAccount.getId(), t2);

            Transaction t3 = system.createTransaction(50.0, "Gas", "2026-01-07", 
                                                     TransactionCategory.TRANSPORTATION, TransactionType.EXPENSE);
            system.addTransaction(mainAccount.getId(), t3);

            Transaction t4 = system.createTransaction(80.0, "Cinema and Dinner", "2026-01-10", 
                                                     TransactionCategory.ENTERTAINMENT, TransactionType.EXPENSE);
            system.addTransaction(mainAccount.getId(), t4);

            Transaction t5 = system.createTransaction(100.0, "Allowance", "2026-01-15", 
                                                     TransactionCategory.OTHER, TransactionType.INCOME);
            system.addTransaction(teenAccount.getId(), t5);

            system.createBudget(500.0, TransactionCategory.FOOD);
            system.createBudget(200.0, TransactionCategory.TRANSPORTATION);
            system.createBudget(300.0, TransactionCategory.ENTERTAINMENT);

            SavingsGoal vacation = system.createSavingsGoal("Vacation Fund", 5000.0);
            system.depositToSavingsGoal(vacation.getId(), 1500.0);

            SavingsGoal emergency = system.createSavingsGoal("Emergency Fund", 10000.0);
            system.depositToSavingsGoal(emergency.getId(), 3000.0);

            System.out.println("Example data loaded successfully!");
        } catch (FinanceException e) {
            System.out.println("Error loading example data: " + e.getMessage());
        }
    }

    /** 
     * @param prompt
     * @return int
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    /** 
     * @param prompt
     * @return double
     */
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    /** 
     * @param prompt
     * @return String
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
