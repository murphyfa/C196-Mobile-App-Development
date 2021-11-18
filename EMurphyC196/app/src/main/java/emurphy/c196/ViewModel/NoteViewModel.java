package emurphy.c196.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.NoteEntity;
import emurphy.c196.Database.NoteRepository;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application app) {
        super(app);
        noteRepository = new NoteRepository(app);
    }

    public long insertNote(NoteEntity entity) {
        return noteRepository.insertNote(entity);
    }

    public void deleteNote(NoteEntity entity) {
        noteRepository.deleteNote(entity);
    }

    public void updateNote(NoteEntity entity) {
        noteRepository.updateNote(entity);
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return noteRepository.getAllNotes();
    }

    public NoteEntity getNoteById(long id) {
        return noteRepository.getNoteById(id);
    }

    public List<NoteEntity> getAllNotesForCourse(long id) {
        return noteRepository.getAllNotesForCourse(id);
    }

    public LiveData<List<NoteEntity>> getAllNotesForCourseLive(long id) {
        return noteRepository.getAllNotesForCourseLive(id);
    }

    public CourseEntity getCourseById(long id) {
        return noteRepository.getCourseById(id);
    }
}
