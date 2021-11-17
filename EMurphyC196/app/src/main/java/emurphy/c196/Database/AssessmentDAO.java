package emurphy.c196.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert
    long insertAssessment(AssessmentEntity entity);

    @Delete
    void deleteAssessment(AssessmentEntity entity);

    @Update
    void updateAssessment(AssessmentEntity entity);

    @Query("SELECT * FROM assessments WHERE assessment_id = :id")
    AssessmentEntity getAssessmentById(long id);

    @Query("SELECT * FROM assessments ORDER BY start_date ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("SELECT assessments.*, courses.title as course_title FROM assessments JOIN courses ON assessments.course_id = courses.course_id ORDER BY assessments.start_date ASC")
    LiveData<List<AssessmentCourse>> getAllAssessmentCourses();

    @Query("SELECT * FROM assessments WHERE course_id = :id ORDER BY start_date ASC")
    List<AssessmentEntity> getAllAssessmentsForCourse(long id);

    @Query("SELECT * FROM assessments WHERE course_id = :id ORDER BY start_date ASC")
    LiveData<List<AssessmentEntity>> getAllAssessmentsForCourseLive(long id);
}
