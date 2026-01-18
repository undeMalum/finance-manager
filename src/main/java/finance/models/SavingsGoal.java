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

    public void deposit(double amount) throws FinanceException {
        if (amount <= 0) {
            throw new FinanceException("Deposit amount must be positive");
        }
        this.currentAmount += amount;
    }

    public double getProgress() {
        if (targetAmount == 0) return 0;
        return (currentAmount / targetAmount) * 100;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public double getRemainingAmount() {
        return targetAmount - currentAmount;
    }

    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }
}
