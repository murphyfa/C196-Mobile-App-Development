package emurphy.c196.Database;

public class CourseTerm extends CourseEntity {
    private String term_title;
    private String term_start_date;
    private String term_end_date;

    public CourseTerm() {
    }

    public String getTerm_title() {
        return term_title;
    }

    public void setTerm_title(String term_title) {
        this.term_title = term_title;
    }

    public String getTerm_start_date() {
        return term_start_date;
    }

    public void setTerm_start_date(String term_start_date) {
        this.term_start_date = term_start_date;
    }

    public String getTerm_end_date() {
        return term_end_date;
    }

    public void setTerm_end_date(String term_end_date) {
        this.term_end_date = term_end_date;
    }
}
