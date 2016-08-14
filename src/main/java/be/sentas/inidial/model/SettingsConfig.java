package be.sentas.inidial.model;

import javafx.beans.property.*;

/**
 * Created by yannick on 14/08/16.
 */
public class SettingsConfig {

    private final BooleanProperty autoDial = new SimpleBooleanProperty(this, "autoDial", false);

    public final BooleanProperty autoDialProperty() {
        return autoDial;
    }

    public final boolean isAutoDial() {
        return autoDial.get();
    }

    public final void setAutodial(boolean value) {
        autoDial.set(value);
    }

    private final ObjectProperty<KeyboardConfig.Layout> keyboardLayout = new SimpleObjectProperty<KeyboardConfig.Layout>(this, "keyboardLayout", KeyboardConfig.Layout.QWERTY) {
        @Override
        protected void invalidated() {
            keyboardLayoutOrdinal.set(get().ordinal());
        }
    };

    public final ObjectProperty<KeyboardConfig.Layout> keyboardLayout() {
        return keyboardLayout;
    }

    public final KeyboardConfig.Layout getKeyboardLayout() {
        return keyboardLayout.get();
    }

    public final void setKeyboardLayout(KeyboardConfig.Layout value) {
        keyboardLayout.set(value);
    }

    private final IntegerProperty keyboardLayoutOrdinal = new SimpleIntegerProperty(this, "keyboardLayoutOrdinal", KeyboardConfig.Layout.QWERTY.ordinal()) {
        @Override
        protected void invalidated() {
            setKeyboardLayout(KeyboardConfig.Layout.values()[get()]);
        }

    };

}
