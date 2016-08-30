package be.sentas.inidial.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yannick on 13/08/16.
 */
public class KeyboardConfig {

    private Row firstRow;
    private Row secondRow;
    private Row thirdRow;

    private KeyboardConfig() {}

    public static KeyboardConfig getConfig(Layout layout, List<String> enabledKeys) {
        switch (layout) {
            default:
                KeyboardConfig config = new KeyboardConfig();
                List<Key> firstRow = new ArrayList<>();
                for (String symbol :layout.getLayout().get(0)) {
                    firstRow.add(new Key(symbol, enabledKeys.contains(symbol)));
                }
                config.firstRow = new Row(firstRow);
                List<Key> secondRow = new ArrayList<>();
                for (String symbol :layout.getLayout().get(1)) {
                    secondRow.add(new Key(symbol, enabledKeys.contains(symbol)));
                }
                config.secondRow = new Row(secondRow);
                List<Key> thirdRow = new ArrayList<>();
                for (String symbol :layout.getLayout().get(2)) {
                    thirdRow.add(new Key(symbol, enabledKeys.contains(symbol)));
                }
                config.thirdRow = new Row(thirdRow);
                return config;
        }
    }

    public Row getFirstRow() {
        return firstRow;
    }

    public Row getSecondRow() {
        return secondRow;
    }

    public Row getThirdRow() {
        return thirdRow;
    }

    public enum Layout {
        QWERTY(Arrays.asList("Q","W","E","R","T","Y","U","I","O","P"),
                Arrays.asList("A","S","D","F","G","H","J","K","L"),
                Arrays.asList("Z","X","C","V","B","N","M")),
        AZERTY(Arrays.asList("A","Z","E","R","T","Y","U","I","O","P"),
                Arrays.asList("Q","S","D","F","G","H","J","K","L", "M"),
                        Arrays.asList("W","X","C","V","B","N")),;

        private List<List<String>> layout;

        Layout(List<String> firstRow, List<String> secondRow, List<String> thirdRow) {
            this.layout = Arrays.asList(firstRow, secondRow, thirdRow);
        }

        public List<List<String>> getLayout() {
            return layout;
        }
    }

    public static class Key {

        private String symbol;
        private boolean enabled;

        public Key(String symbol, boolean enabled) {
            this.symbol = symbol;
            this.enabled = enabled;
        }

        public String getSymbol() {
            return symbol;
        }

        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (enabled != key.enabled) return false;
            return symbol.equals(key.symbol);

        }

        @Override
        public int hashCode() {
            int result = symbol.hashCode();
            result = 31 * result + (enabled ? 1 : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Key{" +
                    "symbol='" + symbol + '\'' +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    public static class Row {

        private List<Key> keys;

        public Row(List<Key> keys) {
            this.keys = keys;
        }

        public List<Key> getKeys() {
            return keys;
        }
    }
}
