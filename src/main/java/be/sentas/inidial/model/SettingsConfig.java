package be.sentas.inidial.model;

import be.sentas.inidial.views.Keyboard;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

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

    private final ObjectProperty<KeyboardConfig.Layout> keyboardLayout = new SimpleObjectProperty<>(this, "keyboardLayout", KeyboardConfig.Layout.QWERTY);

    public final ObjectProperty<KeyboardConfig.Layout> keyboardLayout() {
        return keyboardLayout;
    }

    public final KeyboardConfig.Layout getKeyboardLayout() {
        return keyboardLayout.get();
    }

    public final void setKeyboardLayout(KeyboardConfig.Layout value) {
        keyboardLayout.set(value);
    }

}
