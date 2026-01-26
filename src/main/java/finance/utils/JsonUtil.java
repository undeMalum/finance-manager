package finance.utils;

import finance.enums.*;
import finance.models.*;
import finance.system.FinancialSystem;
import java.io.*;
import java.util.*;

public class JsonUtil {

    /** 
     * @param system
     * @return String
     */
    public static String exportToJson(FinancialSystem system) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        json.append("  \"account\": ");
        Account account = system.getAccount();
        if (account != null) {
            json.append(accountToJson(account, 2));
        } else {
            json.append("null");
        }
        json.append(",\n");
        
        json.append("  \"transactions\": [\n");
        List<Transaction> transactions = system.getAllTransactions();
        for (int i = 0; i < transactions.size(); i++) {
            json.append(transactionToJson(transactions.get(i), 4));
            if (i < transactions.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("  ],\n");
        
        json.append("  \"budgets\": [\n");
        List<Budget> budgets = system.getBudgets();
        for (int i = 0; i < budgets.size(); i++) {
            json.append(budgetToJson(budgets.get(i), 4));
            if (i < budgets.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("  ],\n");
        
        json.append("  \"savingsGoals\": [\n");
        List<SavingsGoal> goals = system.getSavingsGoals();
        for (int i = 0; i < goals.size(); i++) {
            json.append(savingsGoalToJson(goals.get(i), 4));
            if (i < goals.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("  ]\n");
        
        json.append("}");
        return json.toString();
    }

    /** 
     * @param account
     * @param indent
     * @return String
     */
    private static String accountToJson(Account account, int indent) {
        String spaces = " ".repeat(indent);
        StringBuilder json = new StringBuilder();
        json.append(spaces).append("{\n");
        json.append(spaces).append("  \"id\": ").append(account.getId()).append(",\n");
        json.append(spaces).append("  \"name\": \"").append(escape(account.getName())).append("\",\n");
        json.append(spaces).append("  \"balance\": ").append(account.getBalance()).append(",\n");
        json.append(spaces).append("  \"type\": \"").append(account.getType()).append("\"\n");
        json.append(spaces).append("}");
        return json.toString();
    }

    /** 
     * @param t
     * @param indent
     * @return String
     */
    private static String transactionToJson(Transaction t, int indent) {
        String spaces = " ".repeat(indent);
        StringBuilder json = new StringBuilder();
        json.append(spaces).append("{\n");
        json.append(spaces).append("  \"id\": ").append(t.getId()).append(",\n");
        json.append(spaces).append("  \"amount\": ").append(t.getAmount()).append(",\n");
        json.append(spaces).append("  \"description\": \"").append(escape(t.getDescription())).append("\",\n");
        json.append(spaces).append("  \"date\": \"").append(t.getDate()).append("\",\n");
        json.append(spaces).append("  \"category\": \"").append(t.getCategory()).append("\",\n");
        json.append(spaces).append("  \"type\": \"").append(t.getType()).append("\"\n");
        json.append(spaces).append("}");
        return json.toString();
    }

    /** 
     * @param budget
     * @param indent
     * @return String
     */
    private static String budgetToJson(Budget budget, int indent) {
        String spaces = " ".repeat(indent);
        StringBuilder json = new StringBuilder();
        json.append(spaces).append("{\n");
        json.append(spaces).append("  \"id\": ").append(budget.getId()).append(",\n");
        json.append(spaces).append("  \"limit\": ").append(budget.getLimit()).append(",\n");
        json.append(spaces).append("  \"currentUsage\": ").append(budget.getCurrentUsage()).append(",\n");
        json.append(spaces).append("  \"category\": \"").append(budget.getCategory()).append("\"\n");
        json.append(spaces).append("}");
        return json.toString();
    }

    /** 
     * @param goal
     * @param indent
     * @return String
     */
    private static String savingsGoalToJson(SavingsGoal goal, int indent) {
        String spaces = " ".repeat(indent);
        StringBuilder json = new StringBuilder();
        json.append(spaces).append("{\n");
        json.append(spaces).append("  \"id\": ").append(goal.getId()).append(",\n");
        json.append(spaces).append("  \"name\": \"").append(escape(goal.getName())).append("\",\n");
        json.append(spaces).append("  \"targetAmount\": ").append(goal.getTargetAmount()).append(",\n");
        json.append(spaces).append("  \"currentAmount\": ").append(goal.getCurrentAmount()).append("\"\n");
        json.append(spaces).append("}");
        return json.toString();
    }

    /** 
     * @param filePath
     * @return Map<String, Object>
     * @throws IOException
     */
    public static Map<String, Object> importFromJson(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        
        return parseJson(content.toString());
    }

    /** 
     * @param json
     * @return Map<String, Object>
     */
    private static Map<String, Object> parseJson(String json) {
        Map<String, Object> result = new HashMap<>();
        
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
        
        String[] sections = splitTopLevel(json);
        
        for (String section : sections) {
            section = section.trim();
            if (section.isEmpty()) continue;
            
            int colonIndex = section.indexOf(":");
            if (colonIndex == -1) continue;
            
            String key = section.substring(0, colonIndex).trim();
            key = key.replaceAll("\"", "");
            
            String value = section.substring(colonIndex + 1).trim();
            
            if (key.equals("accounts")) {
                result.put("accounts", parseAccountArray(value));
            } else if (key.equals("transactions")) {
                result.put("transactions", parseTransactionArray(value));
            } else if (key.equals("budgets")) {
                result.put("budgets", parseBudgetArray(value));
            } else if (key.equals("savingsGoals")) {
                result.put("savingsGoals", parseSavingsGoalArray(value));
            }
        }
        
        return result;
    }

    /** 
     * @param json
     * @return String[]
     */
    private static String[] splitTopLevel(String json) {
        List<String> parts = new ArrayList<>();
        int depth = 0;
        int start = 0;
        boolean inString = false;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            }
            
            if (!inString) {
                if (c == '{' || c == '[') depth++;
                if (c == '}' || c == ']') depth--;
                
                if (c == ',' && depth == 0) {
                    parts.add(json.substring(start, i));
                    start = i + 1;
                }
            }
        }
        
        if (start < json.length()) {
            parts.add(json.substring(start));
        }
        
        return parts.toArray(new String[0]);
    }

    /** 
     * @param json
     * @return List<Map<String, String>>
     */
    private static List<Map<String, String>> parseAccountArray(String json) {
        return parseObjectArray(json);
    }

    /** 
     * @param json
     * @return List<Map<String, String>>
     */
    private static List<Map<String, String>> parseTransactionArray(String json) {
        return parseObjectArray(json);
    }

    /** 
     * @param json
     * @return List<Map<String, String>>
     */
    private static List<Map<String, String>> parseBudgetArray(String json) {
        return parseObjectArray(json);
    }

    /** 
     * @param json
     * @return List<Map<String, String>>
     */
    private static List<Map<String, String>> parseSavingsGoalArray(String json) {
        return parseObjectArray(json);
    }

    /** 
     * @param json
     * @return List<Map<String, String>>
     */
    private static List<Map<String, String>> parseObjectArray(String json) {
        List<Map<String, String>> objects = new ArrayList<>();
        
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);
        
        List<String> objectStrings = new ArrayList<>();
        int depth = 0;
        int start = 0;
        boolean inString = false;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            }
            
            if (!inString) {
                if (c == '{') depth++;
                if (c == '}') {
                    depth--;
                    if (depth == 0) {
                        objectStrings.add(json.substring(start, i + 1));
                        start = i + 1;
                    }
                }
            }
        }
        
        for (String objStr : objectStrings) {
            objects.add(parseObject(objStr));
        }
        
        return objects;
    }

    /** 
     * @param json
     * @return Map<String, String>
     */
    private static Map<String, String> parseObject(String json) {
        Map<String, String> obj = new HashMap<>();
        
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
        
        String[] pairs = splitTopLevel(json);
        
        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty()) continue;
            
            int colonIndex = pair.indexOf(":");
            if (colonIndex == -1) continue;
            
            String key = pair.substring(0, colonIndex).trim();
            key = key.replaceAll("\"", "");
            
            String value = pair.substring(colonIndex + 1).trim();
            value = value.replaceAll("\"", "");
            
            obj.put(key, value);
        }
        
        return obj;
    }

    /** 
     * @param str
     * @return String
     */
    private static String escape(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    /** 
     * @param str
     * @return AccountType
     */
    public static AccountType parseAccountType(String str) {
        return AccountType.valueOf(str);
    }

    /** 
     * @param str
     * @return TransactionCategory
     */
    public static TransactionCategory parseTransactionCategory(String str) {
        return TransactionCategory.valueOf(str);
    }

    /** 
     * @param str
     * @return TransactionType
     */
    public static TransactionType parseTransactionType(String str) {
        return TransactionType.valueOf(str);
    }
}
