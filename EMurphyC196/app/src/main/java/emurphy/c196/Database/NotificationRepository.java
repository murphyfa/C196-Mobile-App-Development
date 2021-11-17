package emurphy.c196.Database;

import android.app.Application;
import android.content.Context;

public class NotificationRepository {
    private NotificationDAO notificationDAO;

    public NotificationRepository(Context context){
        Database db = Database.getDbInstance(context);
        notificationDAO = db.notificationDAO();
    }

    public long insertNotification(NotificationEntity entity){
        return notificationDAO.insertNotification(entity);
    }

    public void deleteNotification(NotificationEntity entity){
        notificationDAO.deleteNotification(entity);
    }

    public NotificationEntity getNotificationById(long id){
        return notificationDAO.getNotificationById(id);
    }

    public int getNotificationIdForEntity(long id, String type){
        return notificationDAO.getNotificationIdForEntity(id, type);
    }
}
