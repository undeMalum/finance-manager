package finance.models;

import finance.exceptions.FinanceException;

public class SavingsGoal {
    private int id;
    private String name;
    private double targetAmount;
    private double currentAmount;

    public SavingsGoal(int id, String name, double targetAmount) {
        this.id = id;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
    }

    /** 
     * @param amount
     * @throws FinanceException
     */
    public void deposit(double amount) throws FinanceException {
        if (amount <= 0) {
            throw new FinanceException("Deposit amount must be positive");
        }
        this.currentAmount += amount;
    }

    /** 
     * @return double
     */
    public double getProgress() {
        if (targetAmount == 0) return 0;
        return (currentAmount / targetAmount) * 100;
    }

    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * @return double
     */
    public double getTargetAmount() {
        return targetAmount;
    }

    /** 
     * @param targetAmount
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /** 
     * @return double
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /** 
     * @return double
     */
    public double getRemainingAmount() {
        return targetAmount - currentAmount;
    }

    /** 
     * @return boolean
     */
    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }
}
