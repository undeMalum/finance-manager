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

    /** 
     * @return double
     */
    public double checkStatus() {
        return limit - currentUsage;
    }

    public void resetBudget() {
        this.currentUsage = 0.0;
    }

    /** 
     * @param amount
     */
    public void addExpense(double amount) {
        this.currentUsage += amount;
    }

    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    /** 
     * @return double
     */
    public double getLimit() {
        return limit;
    }

    /** 
     * @param limit
     */
    public void setLimit(double limit) {
        this.limit = limit;
    }

    /** 
     * @return double
     */
    public double getCurrentUsage() {
        return currentUsage;
    }

    /** 
     * @return TransactionCategory
     */
    public TransactionCategory getCategory() {
        return category;
    }

    /** 
     * @param category
     */
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    /** 
     * @return double
     */
    public double getPercentageUsed() {
        if (limit == 0) return 0;
        return (currentUsage / limit) * 100;
    }
}
