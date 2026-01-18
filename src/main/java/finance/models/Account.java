package finance.models;

import finance.enums.AccountType;
import finance.exceptions.FinanceException;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id;
    private String name;
    private double balance;
    private AccountType type;
    private List<Transaction> transactions;

    public Account(int id, String name, double balance, AccountType type) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.transactions = new ArrayList<>();
    }

    /** 
     * @param amount
     * @throws FinanceException
     */
    public void deposit(double amount) throws FinanceException {
        if (amount <= 0) {
            throw new FinanceException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    /** 
     * @param amount
     * @throws FinanceException
     */
    public void withdraw(double amount) throws FinanceException {
        if (amount <= 0) {
            throw new FinanceException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new FinanceException("Insufficient balance");
        }
        this.balance -= amount;
    }

    /** 
     * @return double
     */
    public double getBalance() {
        return balance;
    }

    /** 
     * @param type
     */
    public void setType(AccountType type) {
        this.type = type;
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
     * @return AccountType
     */
    public AccountType getType() {
        return type;
    }

    /** 
     * @return List<Transaction>
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /** 
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /** 
     * @param transaction
     */
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    /** 
     * @param balance
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }
}
