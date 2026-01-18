package finance.models;

import finance.exceptions.FinanceException;

public class SavingsGoalTest {
    private SavingsGoal goal;

    public void setUp() {
        goal = new SavingsGoal(1, "Vacation", 5000.0);
    }

    public void testDeposit() {
        try {
            goal.deposit(1000.0);
            if (goal.getCurrentAmount() == 1000.0) {
                System.out.println("PASS: testDeposit - Amount deposited correctly");
            } else {
                System.out.println("FAIL: testDeposit - Expected 1000.0, got " + goal.getCurrentAmount());
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testDeposit - Unexpected exception: " + e.getMessage());
        }
    }

    public void testDepositNegativeAmount() {
        try {
            goal.deposit(-500.0);
            System.out.println("FAIL: testDepositNegativeAmount - Should throw exception");
        } catch (FinanceException e) {
            System.out.println("PASS: testDepositNegativeAmount - Exception thrown as expected");
        }
    }

    public void testGetProgress() {
        try {
            goal.deposit(2500.0);
            double progress = goal.getProgress();
            if (progress == 50.0) {
                System.out.println("PASS: testGetProgress - Progress is 50.0%");
            } else {
                System.out.println("FAIL: testGetProgress - Expected 50.0%, got " + progress + "%");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testGetProgress - Unexpected exception: " + e.getMessage());
        }
    }

    public void testGetRemainingAmount() {
        try {
            goal.deposit(3000.0);
            double remaining = goal.getRemainingAmount();
            if (remaining == 2000.0) {
                System.out.println("PASS: testGetRemainingAmount - Remaining is 2000.0");
            } else {
                System.out.println("FAIL: testGetRemainingAmount - Expected 2000.0, got " + remaining);
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testGetRemainingAmount - Unexpected exception: " + e.getMessage());
        }
    }

    public void testIsCompleted() {
        try {
            goal.deposit(5000.0);
            if (goal.isCompleted()) {
                System.out.println("PASS: testIsCompleted - Goal marked as completed");
            } else {
                System.out.println("FAIL: testIsCompleted - Goal should be completed");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testIsCompleted - Unexpected exception: " + e.getMessage());
        }
    }

    public void testIsNotCompleted() {
        try {
            goal.deposit(3000.0);
            if (!goal.isCompleted()) {
                System.out.println("PASS: testIsNotCompleted - Goal correctly not completed");
            } else {
                System.out.println("FAIL: testIsNotCompleted - Goal should not be completed");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testIsNotCompleted - Unexpected exception: " + e.getMessage());
        }
    }

    public void testGettersAndSetters() {
        goal.setName("New Car");
        goal.setTargetAmount(15000.0);

        if (goal.getId() == 1 &&
            goal.getName().equals("New Car") &&
            goal.getTargetAmount() == 15000.0) {
            System.out.println("PASS: testGettersAndSetters - All getters and setters work correctly");
        } else {
            System.out.println("FAIL: testGettersAndSetters - Some values incorrect");
        }
    }

    public void runAllTests() {
        System.out.println("\n=== Running SavingsGoal Tests ===");
        setUp();
        testDeposit();
        setUp();
        testDepositNegativeAmount();
        setUp();
        testGetProgress();
        setUp();
        testGetRemainingAmount();
        setUp();
        testIsCompleted();
        setUp();
        testIsNotCompleted();
        setUp();
        testGettersAndSetters();
    }
}
