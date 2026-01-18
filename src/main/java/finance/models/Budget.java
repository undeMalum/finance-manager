package finance.models;

import finance.enums.TransactionCategory;

public class Budget {
    private int id;
    private double limit;
    private double currentUsage;
    private TransactionCategory category;

    public Budget(int id, double limit, TransactionCategory category) {
        this.id = id;
        this.limit = limit;
        this.currentUsage = 0.0;
        this.category = category;
    }

    public double checkStatus() {
        return limit - currentUsage;
    }

    public void resetBudget() {
        this.currentUsage = 0.0;
    }

    public void addExpense(double amount) {
        this.currentUsage += amount;
    }

    public int getId() {
        return id;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getCurrentUsage() {
        return currentUsage;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public double getPercentageUsed() {
        if (limit == 0) return 0;
        return (currentUsage / limit) * 100;
    }
}
