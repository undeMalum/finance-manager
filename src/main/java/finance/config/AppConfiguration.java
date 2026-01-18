package finance.config;

public class AppConfiguration {
    private static String DEFAULT_CURRENCY = "USD";
    private static int MAX_BUDGETS = 10;

    public static void loadConfiguration() {
        DEFAULT_CURRENCY = "USD";
        MAX_BUDGETS = 10;
    }

    /** 
     * @return String
     */
    public static String getDefaultCurrency() {
        return DEFAULT_CURRENCY;
    }

    /** 
     * @return int
     */
    public static int getMaxBudgets() {
        return MAX_BUDGETS;
    }

    /** 
     * @param currency
     */
    public static void setDefaultCurrency(String currency) {
        DEFAULT_CURRENCY = currency;
    }

    /** 
     * @param maxBudgets
     */
    public static void setMaxBudgets(int maxBudgets) {
        MAX_BUDGETS = maxBudgets;
    }
}
