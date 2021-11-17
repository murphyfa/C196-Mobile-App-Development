package emurphy.c196.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import emurphy.c196.Database.AssessmentCourse;
import emurphy.c196.Database.AssessmentEntity;
import emurphy.c196.Database.AssessmentRepository;
import emurphy.c196.Database.CourseEntity;

public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepository assessmentRepository;

    public AssessmentViewModel(@NonNull Application app) {
        super(app);
        assessmentRepository = new AssessmentRepository(app);
    }

    public long insertAssessment(AssessmentEntity entity) {
        return assessmentRepository.insertAssessment(entity);
    }

    public void deleteAssessment(AssessmentEntity entity) {
        assessmentRepository.deleteAssessment(entity);
    }

    public void updateAssessment(AssessmentEntity entity) {
        assessmentRepository.updateAssessment(entity);
    }

    public AssessmentEntity getAssessmentById(long id) {
        return assessmentRepository.getAssessmentById(id);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return assessmentRepository.getAllAssessments();
    }

    public LiveData<List<AssessmentCourse>> getAllAssessmentCourses(){
        return assessmentRepository.getAllAssessmentCourses();
    }

    public List<AssessmentEntity> getAllAssessmentsForCourse(int id) {
        return assessmentRepository.getAllAssessmentsForCourse(id);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsForCourseLive(long id){
        return assessmentRepository.getAllAssessmentsForCourseLive(id);
    }

    public List<CourseEntity> getAllCourses() {
        return assessmentRepository.getAllCourses();
    }
}
