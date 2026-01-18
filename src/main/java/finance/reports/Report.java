package finance.reports;

public abstract class Report {
    private String date;
    private String title;

    public Report(String date, String title) {
        this.date = date;
        this.title = title;
    }

    public abstract String generateContent();

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    protected void setDate(String date) {
        this.date = date;
    }

    protected void setTitle(String title) {
        this.title = title;
    }
}
