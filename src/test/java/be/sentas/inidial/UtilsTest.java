package be.sentas.inidial;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void nullIsBlank() {
        assertThat(Utils.isBlank(null)).isTrue();
        assertThat(Utils.isNotBlank(null)).isFalse();
    }

    @Test
    public void emptyIsBlank() {
        assertThat(Utils.isBlank("")).isTrue();
        assertThat(Utils.isNotBlank("")).isFalse();
    }

    @Test
    public void whiteSpacesIsBlank() {
        assertThat(Utils.isBlank("    ")).isTrue();
        assertThat(Utils.isNotBlank("    ")).isFalse();
    }

    @Test
    public void textIsNotBlank() {
        assertThat(Utils.isBlank("aaa")).isFalse();
        assertThat(Utils.isNotBlank("aaa")).isTrue();
    }

    @Test
    public void hasTimeIntervallPassedYes() {
        int oneHourInMillis = 1000 * 60 * 60;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        cal.add(Calendar.SECOND, -1);
        Date time = cal.getTime();
        assertThat(Utils.hasTimeIntervalPassed(time, oneHourInMillis)).isTrue();
    }

    @Test
    public void hasTimeIntervallPassedNo() {
        int oneHourInMillis = 1000 * 60 * 60;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        cal.add(Calendar.SECOND, +1);
        Date time = cal.getTime();
        assertThat(Utils.hasTimeIntervalPassed(time, oneHourInMillis)).isFalse();
    }

}
