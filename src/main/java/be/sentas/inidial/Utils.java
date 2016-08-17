package be.sentas.inidial;

/**
 * Created by yannick on 17/08/16.
 */
public class Utils {

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().equals("");
    }

    public static boolean isBlank(String value) {
        return !isNotBlank(value);
    }
}
