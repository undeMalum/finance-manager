package finance.config;

public class AppConfiguration {
    private static String DEFAULT_CURRENCY = "USD";
    private static int MAX_BUDGETS = 10;

    public static void loadConfiguration() {
        DEFAULT_CURRENCY = "USD";
        MAX_BUDGETS = 10;
    }

    public static String getDefaultCurrency() {
        return DEFAULT_CURRENCY;
    }

    public static int getMaxBudgets() {
        return MAX_BUDGETS;
    }

    public static void setDefaultCurrency(String currency) {
        DEFAULT_CURRENCY = currency;
    }

    public static void setMaxBudgets(int maxBudgets) {
        MAX_BUDGETS = maxBudgets;
    }
}
