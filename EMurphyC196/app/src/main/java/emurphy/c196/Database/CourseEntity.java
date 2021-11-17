package emurphy.c196.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses",
        foreignKeys = @ForeignKey(entity = TermEntity.class,
                parentColumns = "term_id",
                childColumns = "term_id",
                onDelete = ForeignKey.CASCADE))
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private long course_id;
    private String title;
    private String start_date;
    private String end_date;
    private String status;
    private String instructor_name;
    private String instructor_phone;
    private String instructor_email;
    private long term_id;
    private int start_notification_id;
    private int end_notification_id;

    public CourseEntity() {
    }

    public CourseEntity(String title, String start_date, String end_date, String status, String instructor_name, String instructor_phone, String instructor_email, long term_id) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.instructor_name = instructor_name;
        this.instructor_phone = instructor_phone;
        this.instructor_email = instructor_email;
        this.term_id = term_id;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getInstructor_phone() {
        return instructor_phone;
    }

    public void setInstructor_phone(String instructor_phone) {
        this.instructor_phone = instructor_phone;
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
    }

    public long getTerm_id() {
        return term_id;
    }

    public void setTerm_id(long term_id) {
        this.term_id = term_id;
    }

    public int getStart_notification_id() {
        return start_notification_id;
    }

    public void setStart_notification_id(int start_notification_id) {
        this.start_notification_id = start_notification_id;
    }

    public int getEnd_notification_id() {
        return end_notification_id;
    }

    public void setEnd_notification_id(int end_notification_id) {
        this.end_notification_id = end_notification_id;
    }
}
