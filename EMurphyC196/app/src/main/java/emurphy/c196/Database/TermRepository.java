package emurphy.c196.Database;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TermRepository {
    private TermDAO termDAO;
    private CourseDAO courseDAO;

    public TermRepository(Application app) {
        Database db = Database.getDbInstance(app);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
    }

    public long insertTerm(TermEntity entity) {
        return termDAO.insert(entity);
    }

    public void deleteTerm(TermEntity entity) {
        termDAO.delete(entity);
    }

    public void updateTerm(TermEntity entity) {
        termDAO.update(entity);
    }

    public List<TermEntity> getAllTerms() {
        return termDAO.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTermsLive() {
        return termDAO.getAllTermsLive();
    }

    public TermEntity getTermById(long id) {
        return termDAO.getTermById(id);
    }

    public Pair<TermEntity, List<CourseEntity>> getTermWithCourses(long id) {
        TermEntity term = termDAO.getTermById(id);
        List<CourseEntity> courses = courseDAO.getAllCoursesForTerm(id);
        return new Pair<>(term, courses);
    }

    public LiveData<List<CourseEntity>> getCoursesForTerm(long id) {
        return courseDAO.getAllCoursesForTermLive(id);
    }
}
