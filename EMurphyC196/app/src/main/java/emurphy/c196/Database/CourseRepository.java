package emurphy.c196.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import kotlin.Triple;

public class CourseRepository {
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;
    private NoteDAO noteDAO;
    private TermDAO termDAO;

    public CourseRepository(Application app) {
        Database db = Database.getDbInstance(app);
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        noteDAO = db.noteDAO();
        termDAO = db.termDAO();
    }

    public long insertCourse(CourseEntity entity) {
        return courseDAO.insertCourse(entity);
    }

    public void updateCourse(CourseEntity entity) {
        courseDAO.updateCourse(entity);
    }

    public void deleteCourse(CourseEntity entity) {
        courseDAO.deleteCourse(entity);
    }

    public LiveData<List<CourseEntity>> getAllCoursesLive() {
        return courseDAO.getAllCoursesLive();
    }

    public List<CourseEntity> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public LiveData<List<CourseTerm>> getAllCourseTerms() {
        return courseDAO.getAllCourseTerms();
    }

    public CourseEntity getCourseById(long id) {
        return courseDAO.getCourseById(id);
    }

    public List<CourseEntity> getAllCoursesForTerm(long id) {
        return courseDAO.getAllCoursesForTerm(id);
    }

    public List<AssessmentEntity> getAllAssessmentsForCourse(long id) {
        return assessmentDAO.getAllAssessmentsForCourse(id);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsForCourseLive(long id) {
        return assessmentDAO.getAllAssessmentsForCourseLive(id);
    }

    public LiveData<List<NoteEntity>> getAllNotesForCourseLive(long id) {
        return noteDAO.getAllNotesForCourseLive(id);
    }

    public Triple<CourseEntity, List<AssessmentEntity>, List<NoteEntity>> getCourseWithAssessmentsAndNotes(long id) {
        CourseEntity course = courseDAO.getCourseById(id);
        List<AssessmentEntity> assessments = assessmentDAO.getAllAssessmentsForCourse(id);
        List<NoteEntity> notes = noteDAO.getAllNotesForCourse(id);
        return new Triple<>(course, assessments, notes);
    }

    public List<TermEntity> getAllTerms(){
        return termDAO.getAllTerms();
    }
}
