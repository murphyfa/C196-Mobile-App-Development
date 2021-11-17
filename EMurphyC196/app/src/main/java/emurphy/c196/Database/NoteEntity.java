package emurphy.c196.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes",
        foreignKeys = @ForeignKey(entity = CourseEntity.class,
                parentColumns = "course_id",
                childColumns = "course_id",
                onDelete = ForeignKey.CASCADE))
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private long note_id;
    private String title;
    private String text;
    private long course_id;

    public NoteEntity() {
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(long note_id) {
        this.note_id = note_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }
}
