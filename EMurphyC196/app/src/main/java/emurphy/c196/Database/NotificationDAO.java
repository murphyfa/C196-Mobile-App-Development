package emurphy.c196.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotificationDAO {
    @Insert
    long insertNotification(NotificationEntity entity);

    @Delete
    void deleteNotification(NotificationEntity entity);

    @Update
    void updateNotification(NotificationEntity entity);

    @Query("SELECT * FROM notifications WHERE notification_id = :id")
    NotificationEntity getNotificationById(long id);

    @Query("SELECT notification_id FROM notifications WHERE entity_id = :id AND type = :type")
    int getNotificationIdForEntity(long id, String type);
}
