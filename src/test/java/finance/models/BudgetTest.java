package finance.models;

import finance.enums.TransactionCategory;

public class BudgetTest {
    private Budget budget;

    public void setUp() {
        budget = new Budget(1, 1000.0, TransactionCategory.FOOD);
    }

    public void testCheckStatus() {
        budget.addExpense(300.0);
        double remaining = budget.checkStatus();
        if (remaining == 700.0) {
            System.out.println("PASS: testCheckStatus - Remaining budget is 700.0");
        } else {
            System.out.println("FAIL: testCheckStatus - Expected 700.0, got " + remaining);
        }
    }

    public void testResetBudget() {
        budget.addExpense(500.0);
        budget.resetBudget();
        if (budget.getCurrentUsage() == 0.0) {
            System.out.println("PASS: testResetBudget - Budget reset to 0.0");
        } else {
            System.out.println("FAIL: testResetBudget - Expected 0.0, got " + budget.getCurrentUsage());
        }
    }

    public void testAddExpense() {
        budget.addExpense(200.0);
        budget.addExpense(150.0);
        if (budget.getCurrentUsage() == 350.0) {
            System.out.println("PASS: testAddExpense - Current usage is 350.0");
        } else {
            System.out.println("FAIL: testAddExpense - Expected 350.0, got " + budget.getCurrentUsage());
        }
    }

    public void testGetPercentageUsed() {
        budget.addExpense(500.0);
        double percentage = budget.getPercentageUsed();
        if (percentage == 50.0) {
            System.out.println("PASS: testGetPercentageUsed - Usage is 50.0%");
        } else {
            System.out.println("FAIL: testGetPercentageUsed - Expected 50.0%, got " + percentage + "%");
        }
    }

    public void testGettersAndSetters() {
        budget.setLimit(1500.0);
        budget.setCategory(TransactionCategory.ENTERTAINMENT);
        
        if (budget.getId() == 1 &&
            budget.getLimit() == 1500.0 &&
            budget.getCategory() == TransactionCategory.ENTERTAINMENT) {
            System.out.println("PASS: testGettersAndSetters - All getters and setters work correctly");
        } else {
            System.out.println("FAIL: testGettersAndSetters - Some values incorrect");
        }
    }

    public void testBudgetExceeded() {
        budget.addExpense(1200.0);
        double percentage = budget.getPercentageUsed();
        if (percentage > 100.0) {
            System.out.println("PASS: testBudgetExceeded - Budget correctly shows as exceeded (120.0%)");
        } else {
            System.out.println("FAIL: testBudgetExceeded - Expected >100%, got " + percentage + "%");
        }
    }

    public void runAllTests() {
        System.out.println("\n=== Running Budget Tests ===");
        setUp();
        testCheckStatus();
        setUp();
        testResetBudget();
        setUp();
        testAddExpense();
        setUp();
        testGetPercentageUsed();
        setUp();
        testGettersAndSetters();
        setUp();
        testBudgetExceeded();
    }
}
