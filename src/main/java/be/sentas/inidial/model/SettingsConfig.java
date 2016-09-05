package be.sentas.inidial.model;

import javafx.beans.property.*;

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


    /*
        KEYBOARD LAYOUT
     */
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

    /*
        NAME DIRECTION
     */
    private final ObjectProperty<NameDirection> nameDirection = new SimpleObjectProperty<NameDirection>(this, "nameDirection", NameDirection.FIRST_LAST) {
        @Override
        protected void invalidated() {
            nameDirectionOrdinal.set(get().ordinal());
        }
    };

    public final ObjectProperty<NameDirection> nameDirection() {
        return nameDirection;
    }

    public final NameDirection getNameDirection() {
        return nameDirection.get();
    }

    public final void setNameDirection(NameDirection value) {
        nameDirection.set(value);
    }

    private final IntegerProperty nameDirectionOrdinal = new SimpleIntegerProperty(this, "nameDirectionOrdinal", NameDirection.FIRST_LAST.ordinal()) {
        @Override
        protected void invalidated() {
            setNameDirection(NameDirection.values()[get()]);
        }

    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingsConfig that = (SettingsConfig) o;

        if (!autoDial.getValue().equals(that.autoDial.getValue())) return false;
        if (!keyboardLayout.getValue().equals(that.keyboardLayout.getValue())) return false;
        return nameDirection.getValue().equals(that.nameDirection.getValue());

    }

    @Override
    public int hashCode() {
        int result = autoDial.hashCode();
        result = 31 * result + keyboardLayout.hashCode();
        result = 31 * result + nameDirection.hashCode();
        return result;
    }

}
