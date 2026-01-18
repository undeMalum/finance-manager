package finance.models;

import finance.enums.TransactionCategory;
import finance.enums.TransactionType;
import finance.exceptions.FinanceException;

public class TransactionTest {
    private Transaction transaction;

    public void setUp() {
        transaction = new Transaction(1, 100.0, "Test Transaction", "2026-01-18",
                                     TransactionCategory.FOOD, TransactionType.EXPENSE);
    }

    public void testGetters() {
        if (transaction.getId() == 1 &&
            transaction.getAmount() == 100.0 &&
            transaction.getDescription().equals("Test Transaction") &&
            transaction.getDate().equals("2026-01-18") &&
            transaction.getCategory() == TransactionCategory.FOOD &&
            transaction.getType() == TransactionType.EXPENSE) {
            System.out.println("PASS: testGetters - All getters return correct values");
        } else {
            System.out.println("FAIL: testGetters - Some getters returned incorrect values");
        }
    }

    public void testUpdateTransaction() {
        try {
            transaction.updateTransaction(150.0, "Updated Description");
            if (transaction.getAmount() == 150.0 && 
                transaction.getDescription().equals("Updated Description")) {
                System.out.println("PASS: testUpdateTransaction - Transaction updated correctly");
            } else {
                System.out.println("FAIL: testUpdateTransaction - Values not updated correctly");
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testUpdateTransaction - Unexpected exception: " + e.getMessage());
        }
    }

    public void testUpdateTransactionNegativeAmount() {
        try {
            transaction.updateTransaction(-50.0, "Invalid");
            System.out.println("FAIL: testUpdateTransactionNegativeAmount - Should throw exception");
        } catch (FinanceException e) {
            System.out.println("PASS: testUpdateTransactionNegativeAmount - Exception thrown as expected");
        }
    }

    public void testSetters() {
        transaction.setAmount(200.0);
        transaction.setDescription("New Description");
        transaction.setDate("2026-01-19");
        transaction.setCategory(TransactionCategory.ENTERTAINMENT);
        transaction.setType(TransactionType.INCOME);

        if (transaction.getAmount() == 200.0 &&
            transaction.getDescription().equals("New Description") &&
            transaction.getDate().equals("2026-01-19") &&
            transaction.getCategory() == TransactionCategory.ENTERTAINMENT &&
            transaction.getType() == TransactionType.INCOME) {
            System.out.println("PASS: testSetters - All setters work correctly");
        } else {
            System.out.println("FAIL: testSetters - Some setters failed");
        }
    }

    public void runAllTests() {
        System.out.println("\n=== Running Transaction Tests ===");
        setUp();
        testGetters();
        setUp();
        testUpdateTransaction();
        setUp();
        testUpdateTransactionNegativeAmount();
        setUp();
        testSetters();
    }
}
