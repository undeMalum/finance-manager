package finance.system;

import finance.enums.*;
import finance.exceptions.FinanceException;
import finance.managers.FileManager;
import finance.models.*;

public class FinancialSystemTest {
    private FinancialSystem system;

    public void setUp() {
        FileManager fileManager = new FileManager("test_data.txt");
        system = new FinancialSystem(fileManager);
    }

    public void testAddAccount() {
        try {
            Account account = system.addAccount("Test Account", 1000.0, AccountType.ADULT);
            if (account != null && account.getName().equals("Test Account") && account.getBalance() == 1000.0) {
                System.out.println("PASS: testAddAccount - Account added successfully");
            } else {
                System.out.println("FAIL: testAddAccount - Account not created correctly");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testAddAccount - Exception: " + e.getMessage());
        }
    }

    public void testRemoveAccount() {
        try {
            Account account = system.addAccount("Test Account", 1000.0, AccountType.ADULT);
            system.removeAccount(account.getId());
            if (system.findAccountById(account.getId()) == null) {
                System.out.println("PASS: testRemoveAccount - Account removed successfully");
            } else {
                System.out.println("FAIL: testRemoveAccount - Account still exists");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testRemoveAccount - Exception: " + e.getMessage());
        }
    }

    public void testUpdateAccount() {
        try {
            Account account = system.addAccount("Old Name", 1000.0, AccountType.ADULT);
            system.updateAccount(account.getId(), "New Name", AccountType.TEENAGER);
            Account updated = system.findAccountById(account.getId());
            if (updated.getName().equals("New Name") && updated.getType() == AccountType.TEENAGER) {
                System.out.println("PASS: testUpdateAccount - Account updated successfully");
            } else {
                System.out.println("FAIL: testUpdateAccount - Account not updated correctly");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testUpdateAccount - Exception: " + e.getMessage());
        }
    }

    public void testAddTransaction() {
        try {
            Account account = system.addAccount("Test Account", 1000.0, AccountType.ADULT);
            Transaction transaction = system.createTransaction(100.0, "Test", "2026-01-18",
                                                              TransactionCategory.FOOD, TransactionType.EXPENSE);
            system.addTransaction(account.getId(), transaction);
            if (account.getBalance() == 900.0) {
                System.out.println("PASS: testAddTransaction - Transaction added and balance updated");
            } else {
                System.out.println("FAIL: testAddTransaction - Balance not updated correctly");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testAddTransaction - Exception: " + e.getMessage());
        }
    }

    public void testCreateBudget() {
        try {
            Budget budget = system.createBudget(500.0, TransactionCategory.FOOD);
            if (budget != null && budget.getLimit() == 500.0 && budget.getCategory() == TransactionCategory.FOOD) {
                System.out.println("PASS: testCreateBudget - Budget created successfully");
            } else {
                System.out.println("FAIL: testCreateBudget - Budget not created correctly");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testCreateBudget - Exception: " + e.getMessage());
        }
    }

    public void testCreateSavingsGoal() {
        try {
            SavingsGoal goal = system.createSavingsGoal("Vacation", 5000.0);
            if (goal != null && goal.getName().equals("Vacation") && goal.getTargetAmount() == 5000.0) {
                System.out.println("PASS: testCreateSavingsGoal - Goal created successfully");
            } else {
                System.out.println("FAIL: testCreateSavingsGoal - Goal not created correctly");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testCreateSavingsGoal - Exception: " + e.getMessage());
        }
    }

    public void testFilterTransactions() {
        try {
            Account account = system.addAccount("Test Account", 1000.0, AccountType.ADULT);
            Transaction t1 = system.createTransaction(100.0, "Food", "2026-01-18",
                                                     TransactionCategory.FOOD, TransactionType.EXPENSE);
            Transaction t2 = system.createTransaction(50.0, "Transport", "2026-01-18",
                                                     TransactionCategory.TRANSPORTATION, TransactionType.EXPENSE);
            system.addTransaction(account.getId(), t1);
            system.addTransaction(account.getId(), t2);

            var filtered = system.filterTransactions(TransactionCategory.FOOD, null, null);
            if (filtered.size() == 1 && filtered.get(0).getCategory() == TransactionCategory.FOOD) {
                System.out.println("PASS: testFilterTransactions - Filtering works correctly");
            } else {
                System.out.println("FAIL: testFilterTransactions - Filter returned wrong results");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testFilterTransactions - Exception: " + e.getMessage());
        }
    }

    public void testBudgetUsageUpdate() {
        try {
            Account account = system.addAccount("Test Account", 1000.0, AccountType.ADULT);
            Budget budget = system.createBudget(500.0, TransactionCategory.FOOD);
            Transaction t = system.createTransaction(100.0, "Groceries", "2026-01-18",
                                                    TransactionCategory.FOOD, TransactionType.EXPENSE);
            system.addTransaction(account.getId(), t);

            if (budget.getCurrentUsage() == 100.0) {
                System.out.println("PASS: testBudgetUsageUpdate - Budget usage updated correctly");
            } else {
                System.out.println("FAIL: testBudgetUsageUpdate - Expected 100.0, got " + budget.getCurrentUsage());
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testBudgetUsageUpdate - Exception: " + e.getMessage());
        }
    }

    public void runAllTests() {
        System.out.println("\n=== Running FinancialSystem Tests ===");
        setUp();
        testAddAccount();
        setUp();
        testRemoveAccount();
        setUp();
        testUpdateAccount();
        setUp();
        testAddTransaction();
        setUp();
        testCreateBudget();
        setUp();
        testCreateSavingsGoal();
        setUp();
        testFilterTransactions();
        setUp();
        testBudgetUsageUpdate();
    }
}
