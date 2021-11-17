package emurphy.c196.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    long insertNote(NoteEntity entity);

    @Delete
    void deleteNote(NoteEntity entity);

    @Update
    void updateNote(NoteEntity entity);

    @Query("SELECT * FROM notes WHERE note_id = :id")
    NoteEntity getNoteById(long id);

    @Query("SELECT * FROM notes")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("SELECT * FROM notes WHERE course_id = :id")
    List<NoteEntity> getAllNotesForCourse(long id);

    @Query("SELECT * FROM notes WHERE course_id = :id")
    LiveData<List<NoteEntity>> getAllNotesForCourseLive(long id);
}
