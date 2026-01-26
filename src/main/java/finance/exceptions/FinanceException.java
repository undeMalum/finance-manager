package finance.exceptions;

public class FinanceException extends Exception {
    private String message;

    public FinanceException(String msg) {
        super(msg);
        this.message = msg;
    }

    /** 
     * @return String
     */
    @Override
    public String getMessage() {
        return message;
    }
}
