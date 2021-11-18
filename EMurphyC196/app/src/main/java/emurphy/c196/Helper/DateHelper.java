package emurphy.c196.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateHelper {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public static long toMilliseconds(String dateString) {
        try {
            return (dateFormat.parse(dateString)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
