package emurphy.c196.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class, NoteEntity.class, NotificationEntity.class},
        version = 1)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "C196DB";
    private static volatile Database dbInstance;
    private static final Object LOCK = new Object();

    public abstract TermDAO termDAO();

    public abstract CourseDAO courseDAO();

    public abstract AssessmentDAO assessmentDAO();

    public abstract NoteDAO noteDAO();

    public abstract NotificationDAO notificationDAO();

    public static Database getDbInstance(Context context) {
        if (dbInstance == null) {
            synchronized (LOCK) {
                if (dbInstance == null) {
//                    context.deleteDatabase(DATABASE_NAME);
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallBack)
                            .build();
                }
            }
        }
        return dbInstance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(dbInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDAO termDAO;
        private CourseDAO courseDAO;

        private PopulateDbAsyncTask(Database db) {
            termDAO = db.termDAO();
            courseDAO = db.courseDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long termId = termDAO.insert(new TermEntity("Term 1", "2021-11-01", "2021-11-30"));
            courseDAO.insertCourse(new CourseEntity("Mobile App Development 1", "2021-11-01", "2021-11-30", "In Progress", "John Doe", "999-999-9999", "contact@sample.com", termId));
            courseDAO.insertCourse(new CourseEntity("Mobile App Development 2", "2021-11-01", "2021-11-30", "In Progress", "John Doe", "999-999-9999", "contact@sample.com", termId));
            courseDAO.insertCourse(new CourseEntity("Mobile App Development 3", "2021-11-01", "2021-11-30", "In Progress", "John Doe", "999-999-9999", "contact@sample.com", termId));
            termId = termDAO.insert(new TermEntity("Term 2", "2021-12-01", "2021-12-31"));
            courseDAO.insertCourse(new CourseEntity("Mobile App Development 1", "2021-12-01", "2021-12-31", "In Progress", "John Doe", "999-999-9999", "contact@sample.com", termId));
            courseDAO.insertCourse(new CourseEntity("Mobile App Development 2", "2021-12-01", "2021-12-31", "In Progress", "John Doe", "999-999-9999", "contact@sample.com", termId));
            courseDAO.insertCourse(new CourseEntity("Mobile App Development 3", "2021-12-01", "2021-12-31", "In Progress", "John Doe", "999-999-9999", "contact@sample.com", termId));
            return null;
        }
    }
}
