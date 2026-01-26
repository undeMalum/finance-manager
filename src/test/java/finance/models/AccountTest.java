package finance.models;

import finance.enums.AccountType;
import finance.exceptions.FinanceException;

public class AccountTest {
    private Account account;

    public void setUp() {
        account = new Account(1, "Test Account", 1000.0, AccountType.ADULT);
    }

    public void testDeposit() {
        try {
            account.deposit(500.0);
            if (account.getBalance() == 1500.0) {
                System.out.println("PASS: testDeposit - Balance correctly updated to 1500.0");
            } else {
                System.out.println("FAIL: testDeposit - Expected 1500.0, got " + account.getBalance());
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testDeposit - Unexpected exception: " + e.getMessage());
        }
    }

    public void testDepositNegativeAmount() {
        try {
            account.deposit(-100.0);
            System.out.println("FAIL: testDepositNegativeAmount - Should throw exception");
        } catch (FinanceException e) {
            System.out.println("PASS: testDepositNegativeAmount - Exception thrown as expected");
        }
    }

    public void testWithdraw() {
        try {
            account.withdraw(300.0);
            if (account.getBalance() == 700.0) {
                System.out.println("PASS: testWithdraw - Balance correctly updated to 700.0");
            } else {
                System.out.println("FAIL: testWithdraw - Expected 700.0, got " + account.getBalance());
            }
        } catch (FinanceException e) {
            System.out.println("FAIL: testWithdraw - Unexpected exception: " + e.getMessage());
        }
    }

    public void testWithdrawInsufficientFunds() {
        try {
            account.withdraw(2000.0);
            System.out.println("FAIL: testWithdrawInsufficientFunds - Should throw exception");
        } catch (FinanceException e) {
            System.out.println("PASS: testWithdrawInsufficientFunds - Exception thrown as expected");
        }
    }

    public void testGettersAndSetters() {
        account.setName("Updated Account");
        account.setType(AccountType.TEENAGER);
        
        if (account.getName().equals("Updated Account") && 
            account.getType() == AccountType.TEENAGER &&
            account.getId() == 1) {
            System.out.println("PASS: testGettersAndSetters - All getters and setters work correctly");
        } else {
            System.out.println("FAIL: testGettersAndSetters - Values not updated correctly");
        }
    }

    public void runAllTests() {
        System.out.println("\n=== Running Account Tests ===");
        setUp();
        testDeposit();
        setUp();
        testDepositNegativeAmount();
        setUp();
        testWithdraw();
        setUp();
        testWithdrawInsufficientFunds();
        setUp();
        testGettersAndSetters();
    }
}
