package emurphy.c196.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert
    long insert(TermEntity entity);

    @Delete
    void delete(TermEntity entity);

    @Update
    void update(TermEntity entity);

    @Query("SELECT * FROM terms WHERE term_id = :id")
    TermEntity getTermById(long id);

    @Query("SELECT * FROM terms ORDER BY start_date ASC")
    LiveData<List<TermEntity>> getAllTermsLive();

    @Query("SELECT * FROM terms ORDER BY start_date ASC")
    List<TermEntity> getAllTerms();
}