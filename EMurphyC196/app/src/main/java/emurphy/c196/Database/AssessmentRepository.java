package emurphy.c196.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AssessmentRepository {
    private AssessmentDAO assessmentDAO;
    private CourseDAO courseDAO;

    public AssessmentRepository(Application app) {
        Database db = Database.getDbInstance(app);
        assessmentDAO = db.assessmentDAO();
        courseDAO = db.courseDAO();
    }

    public long insertAssessment(AssessmentEntity entity) {
        return assessmentDAO.insertAssessment(entity);
    }

    public void deleteAssessment(AssessmentEntity entity) {
        assessmentDAO.deleteAssessment(entity);
    }

    public void updateAssessment(AssessmentEntity entity) {
        assessmentDAO.updateAssessment(entity);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return assessmentDAO.getAllAssessments();
    }

    public LiveData<List<AssessmentCourse>> getAllAssessmentCourses(){
        return assessmentDAO.getAllAssessmentCourses();
    }

    public AssessmentEntity getAssessmentById(long id) {
        return assessmentDAO.getAssessmentById(id);
    }

    public List<AssessmentEntity> getAllAssessmentsForCourse(int id) {
        return assessmentDAO.getAllAssessmentsForCourse(id);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsForCourseLive(long id){
        return assessmentDAO.getAllAssessmentsForCourseLive(id);
    }

    public List<CourseEntity> getAllCourses() {
        return courseDAO.getAllCourses();
    }
}
