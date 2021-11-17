package emurphy.c196.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDAO noteDAO;
    private CourseDAO courseDAO;

    public NoteRepository(Application app) {
        Database db = Database.getDbInstance(app);
        noteDAO = db.noteDAO();
        courseDAO = db.courseDAO();
    }

    public long insertNote(NoteEntity entity) {
        return noteDAO.insertNote(entity);
    }

    public void deleteNote(NoteEntity entity) {
        noteDAO.deleteNote(entity);
    }

    public void updateNote(NoteEntity entity) {
        noteDAO.updateNote(entity);
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return noteDAO.getAllNotes();
    }

    public NoteEntity getNoteById(long id) {
        return noteDAO.getNoteById(id);
    }

    public List<NoteEntity> getAllNotesForCourse(long id) {
        return noteDAO.getAllNotesForCourse(id);
    }

    public LiveData<List<NoteEntity>> getAllNotesForCourseLive(long id) {
        return noteDAO.getAllNotesForCourseLive(id);
    }

    public CourseEntity getCourseById(long id){
        return courseDAO.getCourseById(id);
    }
}
