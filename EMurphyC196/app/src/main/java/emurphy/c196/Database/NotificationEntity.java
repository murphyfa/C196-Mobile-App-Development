package emurphy.c196.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class NotificationEntity {
    @PrimaryKey
    private long notification_id;
    private String type;
    private int entity_id;

    public NotificationEntity() {}

    public long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(long notification_id) {
        this.notification_id = notification_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }
}
