package emurphy.c196.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import emurphy.c196.Database.AssessmentEntity;
import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.CourseRepository;
import emurphy.c196.Database.CourseTerm;
import emurphy.c196.Database.NoteEntity;
import emurphy.c196.Database.TermEntity;
import kotlin.Triple;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository courseRepository;

    public CourseViewModel(@NonNull Application app) {
        super(app);
        courseRepository = new CourseRepository(app);
    }

    public long insertCourse(CourseEntity entity) {
        return courseRepository.insertCourse(entity);
    }

    public void deleteCourse(CourseEntity entity) {
        courseRepository.deleteCourse(entity);
    }

    public void updateCourse(CourseEntity entity) {
        courseRepository.updateCourse(entity);
    }

    public LiveData<List<CourseEntity>> getAllCoursesLive() {
        return courseRepository.getAllCoursesLive();
    }

    public LiveData<List<CourseTerm>> getAllCourseTerms() {
        return courseRepository.getAllCourseTerms();
    }

    public List<CourseEntity> getAllCoursesForTerm(int id) {
        return courseRepository.getAllCoursesForTerm(id);
    }

    public CourseEntity getCourseById(long id) {
        return courseRepository.getCourseById(id);
    }

    public List<AssessmentEntity> getAllAssessmentsForCourse(long id) {
        return courseRepository.getAllAssessmentsForCourse(id);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsForCourseLive(long id) {
        return courseRepository.getAllAssessmentsForCourseLive(id);
    }

    public LiveData<List<NoteEntity>> getAllNotesForCourseLive(long id) {
        return courseRepository.getAllNotesForCourseLive(id);
    }

    public Triple<CourseEntity, List<AssessmentEntity>, List<NoteEntity>> getCourseWithAssessmentsAndNotes(long id) {
        return courseRepository.getCourseWithAssessmentsAndNotes(id);
    }

    public List<TermEntity> getAllTerms() {
        return courseRepository.getAllTerms();
    }
}
