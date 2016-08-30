package be.sentas.inidial;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by yannick on 30/08/16.
 */
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

}
