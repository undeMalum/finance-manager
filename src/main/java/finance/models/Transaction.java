package finance.models;

import finance.enums.TransactionCategory;
import finance.enums.TransactionType;
import finance.exceptions.FinanceException;

public class Transaction {
    private int id;
    private double amount;
    private String description;
    private String date;
    private TransactionCategory category;
    private TransactionType type;

    public Transaction(int id, double amount, String description, String date, 
                      TransactionCategory category, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
        this.type = type;
    }

    /** 
     * @param amount
     * @param description
     * @throws FinanceException
     */
    public void updateTransaction(double amount, String description) throws FinanceException {
        if (amount <= 0) {
            throw new FinanceException("Transaction amount must be positive");
        }
        this.amount = amount;
        this.description = description;
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
    public double getAmount() {
        return amount;
    }

    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /** 
     * @return String
     */
    public String getDate() {
        return date;
    }

    /** 
     * @return TransactionCategory
     */
    public TransactionCategory getCategory() {
        return category;
    }

    /** 
     * @return TransactionType
     */
    public TransactionType getType() {
        return type;
    }

    /** 
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /** 
     * @param category
     */
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    /** 
     * @param type
     */
    public void setType(TransactionType type) {
        this.type = type;
    }
}
