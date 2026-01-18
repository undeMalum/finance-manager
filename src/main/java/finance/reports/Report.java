package finance.reports;

public abstract class Report {
    private String date;
    private String title;

    public Report(String date, String title) {
        this.date = date;
        this.title = title;
    }

    public abstract String generateContent();

    /** 
     * @return String
     */
    public String getDate() {
        return date;
    }

    /** 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /** 
     * @param date
     */
    protected void setDate(String date) {
        this.date = date;
    }

    /** 
     * @param title
     */
    protected void setTitle(String title) {
        this.title = title;
    }
}
