package emurphy.c196.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert
    long insertCourse(CourseEntity entity);

    @Delete
    void deleteCourse(CourseEntity entity);

    @Update
    void updateCourse(CourseEntity entity);

    @Query("SELECT * FROM courses WHERE course_id = :id")
    CourseEntity getCourseById(long id);

    @Query("SELECT * FROM courses ORDER BY start_date ASC")
    LiveData<List<CourseEntity>> getAllCoursesLive();

    @Query("SELECT * FROM courses ORDER BY start_date ASC")
    List<CourseEntity> getAllCourses();

    @Query("SELECT courses.*, terms.title as term_title, terms.start_date as term_start_date, terms.end_date as term_end_date FROM courses JOIN terms ON courses.term_id = terms.term_id ORDER BY terms.start_date, courses.start_date")
    LiveData<List<CourseTerm>> getAllCourseTerms();

    @Query("SELECT * FROM courses WHERE term_id = :id ORDER BY start_date ASC")
    List<CourseEntity> getAllCoursesForTerm(long id);

    @Query("SELECT * FROM courses WHERE term_id = :id ORDER BY start_date ASC")
    LiveData<List<CourseEntity>> getAllCoursesForTermLive(long id);
}
