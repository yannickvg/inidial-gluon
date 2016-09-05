package be.sentas.inidial;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().equals("");
    }

    public static boolean isBlank(String value) {
        return !isNotBlank(value);
    }

    public static boolean hasTimeIntervalPassed(Date date, int intervalInMillis) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.add(Calendar.MILLISECOND, -intervalInMillis);
        return date.before(nowCal.getTime());
    }
}
