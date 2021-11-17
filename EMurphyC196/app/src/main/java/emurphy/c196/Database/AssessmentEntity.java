package emurphy.c196.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments",
        foreignKeys = @ForeignKey(entity = CourseEntity.class,
                parentColumns = "course_id",
                childColumns = "course_id",
                onDelete = ForeignKey.CASCADE))
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private long assessment_id;
    private String title;
    private String start_date;
    private String end_date;
    private String type;
    private long course_id;
    private int start_notification_id;
    private int end_notification_id;

    public AssessmentEntity() {
    }

    public AssessmentEntity(String title, String start_date, String end_date, String type, long course_id) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
        this.course_id = course_id;
    }

    public long getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(long assessment_id) {
        this.assessment_id = assessment_id;
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

    public String getEnd_date() { return end_date; }

    public void setEnd_date(String end_date) { this.end_date = end_date; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
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
