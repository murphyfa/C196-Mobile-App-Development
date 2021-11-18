package emurphy.c196;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ThreadLocalRandom;

public class Notifier extends BroadcastReceiver {

    private static final String CHANNEL_ID = "COURSE_SCHEDULER";
    private static final String NOTIFY_TITLE = "NOTIFY_TITLE";
    private static final String NOTIFY_TEXT = "NOTIFY_TEXT";
    private static final String NOTIFY_ID = "NOTIFY_ID";
    public static final String NOTIFY_TYPE = "NOTIFY_TYPE";
    public static final String NOTIFY_TARGET_ID = "NOTIFY_TARGET_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(NOTIFY_TITLE);
        String text = intent.getStringExtra(NOTIFY_TEXT);
        int notificationId = intent.getIntExtra(NOTIFY_ID, 0);
        long targetId = intent.getLongExtra(NOTIFY_TARGET_ID, 0);
        String type = intent.getStringExtra(NOTIFY_TYPE);

        Intent targetIntent;
        switch (type) {
            case "assessment": {
                targetIntent = new Intent(context, AssessmentEditorActivity.class);
                targetIntent.putExtra(AssessmentEditorActivity.EXTRA_ASSESSMENT_ID, targetId);
                break;
            }
            case "course": {
                targetIntent = new Intent(context, CourseEditorActivity.class);
                targetIntent.putExtra(CourseEditorActivity.EXTRA_COURSE_ID, targetId);
                break;
            }
            default: {
                targetIntent = new Intent(context, MainActivity.class);
                break;
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm_reminder)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(targetIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    public static int createAlert(Context context, long time, String type, long target_id, String title, String text, int existing_id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int notificationId;
        if (existing_id > 0) {
            notificationId = existing_id;
        } else {
            notificationId = createNotificationId();
        }

        Intent intent = new Intent(context, Notifier.class);
        intent.putExtra(NOTIFY_TITLE, title);
        intent.putExtra(NOTIFY_TEXT, text);
        intent.putExtra(NOTIFY_ID, notificationId);
        intent.putExtra(NOTIFY_TARGET_ID, target_id);
        intent.putExtra(NOTIFY_TYPE, type);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        return notificationId;
    }

    public static void cancelAlert(Context context, long time, String type, long target_id, String title, String text, int existing_id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Notifier.class);
        intent.putExtra(NOTIFY_TITLE, title);
        intent.putExtra(NOTIFY_TEXT, text);
        intent.putExtra(NOTIFY_ID, existing_id);
        intent.putExtra(NOTIFY_TARGET_ID, target_id);
        intent.putExtra(NOTIFY_TYPE, type);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, existing_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "C196", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("WGU Course Scheduler");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static int createNotificationId() {
        return ThreadLocalRandom.current().nextInt(10000, 99999);
    }
}
