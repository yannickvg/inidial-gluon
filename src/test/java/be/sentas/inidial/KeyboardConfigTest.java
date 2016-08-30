package be.sentas.inidial;

import be.sentas.inidial.model.KeyboardConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by yannick on 30/08/16.
 */
public class KeyboardConfigTest {

    @Test
    public void qwertyAllDisabled() {
        KeyboardConfig config = KeyboardConfig.getConfig(KeyboardConfig.Layout.QWERTY, new ArrayList<>());
        List<KeyboardConfig.Key> firstRow = Arrays.asList(
                new KeyboardConfig.Key("Q", false),
                new KeyboardConfig.Key("W", false),
                new KeyboardConfig.Key("E", false),
                new KeyboardConfig.Key("R", false),
                new KeyboardConfig.Key("T", false),
                new KeyboardConfig.Key("Y", false),
                new KeyboardConfig.Key("U", false),
                new KeyboardConfig.Key("I", false),
                new KeyboardConfig.Key("O", false),
                new KeyboardConfig.Key("P", false));
        List<KeyboardConfig.Key> secondRow = Arrays.asList(
                new KeyboardConfig.Key("A", false),
                new KeyboardConfig.Key("S", false),
                new KeyboardConfig.Key("D", false),
                new KeyboardConfig.Key("F", false),
                new KeyboardConfig.Key("G", false),
                new KeyboardConfig.Key("H", false),
                new KeyboardConfig.Key("J", false),
                new KeyboardConfig.Key("K", false),
                new KeyboardConfig.Key("L", false));
        List<KeyboardConfig.Key> thirdRow = Arrays.asList(
                new KeyboardConfig.Key("Z", false),
                new KeyboardConfig.Key("X", false),
                new KeyboardConfig.Key("C", false),
                new KeyboardConfig.Key("V", false),
                new KeyboardConfig.Key("B", false),
                new KeyboardConfig.Key("N", false),
                new KeyboardConfig.Key("M", false));
        assertEquals("firstRow should be valid", firstRow , config.getFirstRow().getKeys());
        assertEquals("secondRow should be valid", secondRow , config.getSecondRow().getKeys());
        assertEquals("thirdRow should be valid", thirdRow , config.getThirdRow().getKeys());
    }

    @Test
    public void qwertySomeEnabled() {
        KeyboardConfig config = KeyboardConfig.getConfig(KeyboardConfig.Layout.QWERTY, Arrays.asList("Q", "T", "P", "A", "H", "Z", "N"));
        List<KeyboardConfig.Key> firstRow = Arrays.asList(
                new KeyboardConfig.Key("Q", true),
                new KeyboardConfig.Key("W", false),
                new KeyboardConfig.Key("E", false),
                new KeyboardConfig.Key("R", false),
                new KeyboardConfig.Key("T", true),
                new KeyboardConfig.Key("Y", false),
                new KeyboardConfig.Key("U", false),
                new KeyboardConfig.Key("I", false),
                new KeyboardConfig.Key("O", false),
                new KeyboardConfig.Key("P", true));
        List<KeyboardConfig.Key> secondRow = Arrays.asList(
                new KeyboardConfig.Key("A", true),
                new KeyboardConfig.Key("S", false),
                new KeyboardConfig.Key("D", false),
                new KeyboardConfig.Key("F", false),
                new KeyboardConfig.Key("G", false),
                new KeyboardConfig.Key("H", true),
                new KeyboardConfig.Key("J", false),
                new KeyboardConfig.Key("K", false),
                new KeyboardConfig.Key("L", false));
        List<KeyboardConfig.Key> thirdRow = Arrays.asList(
                new KeyboardConfig.Key("Z", true),
                new KeyboardConfig.Key("X", false),
                new KeyboardConfig.Key("C", false),
                new KeyboardConfig.Key("V", false),
                new KeyboardConfig.Key("B", false),
                new KeyboardConfig.Key("N", true),
                new KeyboardConfig.Key("M", false));
        assertEquals("firstRow should be valid", firstRow , config.getFirstRow().getKeys());
        assertEquals("secondRow should be valid", secondRow , config.getSecondRow().getKeys());
        assertEquals("thirdRow should be valid", thirdRow , config.getThirdRow().getKeys());
    }

    @Test
    public void azertyAllDisabled() {
        KeyboardConfig config = KeyboardConfig.getConfig(KeyboardConfig.Layout.AZERTY, new ArrayList<>());
        List<KeyboardConfig.Key> firstRow = Arrays.asList(
                new KeyboardConfig.Key("A", false),
                new KeyboardConfig.Key("Z", false),
                new KeyboardConfig.Key("E", false),
                new KeyboardConfig.Key("R", false),
                new KeyboardConfig.Key("T", false),
                new KeyboardConfig.Key("Y", false),
                new KeyboardConfig.Key("U", false),
                new KeyboardConfig.Key("I", false),
                new KeyboardConfig.Key("O", false),
                new KeyboardConfig.Key("P", false));
        List<KeyboardConfig.Key> secondRow = Arrays.asList(
                new KeyboardConfig.Key("Q", false),
                new KeyboardConfig.Key("S", false),
                new KeyboardConfig.Key("D", false),
                new KeyboardConfig.Key("F", false),
                new KeyboardConfig.Key("G", false),
                new KeyboardConfig.Key("H", false),
                new KeyboardConfig.Key("J", false),
                new KeyboardConfig.Key("K", false),
                new KeyboardConfig.Key("L", false),
                new KeyboardConfig.Key("M", false));
        List<KeyboardConfig.Key> thirdRow = Arrays.asList(
                new KeyboardConfig.Key("W" , false),
                new KeyboardConfig.Key("X", false),
                new KeyboardConfig.Key("C", false),
                new KeyboardConfig.Key("V", false),
                new KeyboardConfig.Key("B", false),
                new KeyboardConfig.Key("N", false));
        assertEquals("firstRow should be valid", firstRow , config.getFirstRow().getKeys());
        assertEquals("secondRow should be valid", secondRow , config.getSecondRow().getKeys());
        assertEquals("thirdRow should be valid", thirdRow , config.getThirdRow().getKeys());
    }
}
