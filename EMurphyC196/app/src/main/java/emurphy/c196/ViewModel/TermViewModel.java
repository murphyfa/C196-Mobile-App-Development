package emurphy.c196.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.TermEntity;
import emurphy.c196.Database.TermRepository;

public class TermViewModel extends AndroidViewModel {
    private TermRepository termRepository;

    public TermViewModel(@NonNull Application app) {
        super(app);
        termRepository = new TermRepository(app);
    }

    public long insertTerm(TermEntity entity) {
        return termRepository.insertTerm(entity);
    }

    public void deleteTerm(TermEntity entity) {
        termRepository.deleteTerm(entity);
    }

    public void updateTerm(TermEntity entity) {
        termRepository.updateTerm(entity);
    }

    public List<TermEntity> getAllTerms() {
        return termRepository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTermsLive() {
        return termRepository.getAllTermsLive();
    }

    public TermEntity getTermById(long id) {
        return termRepository.getTermById(id);
    }

    public Pair<TermEntity, List<CourseEntity>> getTermWithCourses(long id) {
        return termRepository.getTermWithCourses(id);
    }

    public LiveData<List<CourseEntity>> getCoursesForTerm(long id) {
        return termRepository.getCoursesForTerm(id);
    }
}

