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

    public void updateTransaction(double amount, String description) throws FinanceException {
        if (amount <= 0) {
            throw new FinanceException("Transaction amount must be positive");
        }
        this.amount = amount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
