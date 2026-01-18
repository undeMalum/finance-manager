package finance;

import finance.models.*;
import finance.system.*;

public class TestRunner {
    /** 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   FINANCIAL MANAGER - TEST SUITE");
        System.out.println("========================================");

        AccountTest accountTest = new AccountTest();
        accountTest.runAllTests();

        TransactionTest transactionTest = new TransactionTest();
        transactionTest.runAllTests();

        BudgetTest budgetTest = new BudgetTest();
        budgetTest.runAllTests();

        SavingsGoalTest savingsGoalTest = new SavingsGoalTest();
        savingsGoalTest.runAllTests();

        FinancialSystemTest systemTest = new FinancialSystemTest();
        systemTest.runAllTests();

        System.out.println("\n========================================");
        System.out.println("   ALL TESTS COMPLETED");
        System.out.println("========================================");
    }
}
