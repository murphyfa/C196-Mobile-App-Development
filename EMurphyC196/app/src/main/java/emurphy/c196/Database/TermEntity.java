package emurphy.c196.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private long term_id;
    private String title;
    private String start_date;
    private String end_date;

    public TermEntity() {
    }

    public TermEntity(String title, String start_date, String end_date) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public long getTerm_id() {
        return term_id;
    }

    public void setTerm_id(long term_id) {
        this.term_id = term_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
