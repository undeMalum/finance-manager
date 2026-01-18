package finance.reports;

import finance.system.FinancialSystem;
import finance.models.Transaction;
import finance.enums.TransactionType;
import java.util.List;

public class MonthlyReport extends Report {
    private int month;
    private int year;
    private FinancialSystem system;

    public MonthlyReport(int month, int year, FinancialSystem system) {
        super(year + "-" + String.format("%02d", month), "Monthly Financial Report");
        this.month = month;
        this.year = year;
        this.system = system;
    }

    @Override
    public String generateContent() {
        StringBuilder content = new StringBuilder();
        content.append("========================================\n");
        content.append(getTitle()).append("\n");
        content.append("Period: ").append(getDate()).append("\n");
        content.append("========================================\n\n");

        String datePrefix = year + "-" + String.format("%02d", month);
        List<Transaction> monthlyTransactions = system.filterTransactions(null, null, datePrefix);

        double totalIncome = 0.0;
        double totalExpenses = 0.0;

        content.append("TRANSACTIONS SUMMARY:\n");
        content.append("----------------------------------------\n");

        for (Transaction t : monthlyTransactions) {
            if (t.getType() == TransactionType.INCOME) {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }

        content.append("Total Income: $").append(String.format("%.2f", totalIncome)).append("\n");
        content.append("Total Expenses: $").append(String.format("%.2f", totalExpenses)).append("\n");
        content.append("Net: $").append(String.format("%.2f", totalIncome - totalExpenses)).append("\n\n");

        content.append("TRANSACTION DETAILS (").append(monthlyTransactions.size()).append(" transactions):\n");
        content.append("----------------------------------------\n");

        if (monthlyTransactions.isEmpty()) {
            content.append("No transactions found for this period.\n");
        } else {
            for (Transaction t : monthlyTransactions) {
                content.append(t.getDate()).append(" | ");
                content.append(t.getType()).append(" | ");
                content.append(t.getCategory()).append(" | ");
                content.append("$").append(String.format("%.2f", t.getAmount())).append(" | ");
                content.append(t.getDescription()).append("\n");
            }
        }

        content.append("\n========================================\n");
        return content.toString();
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
