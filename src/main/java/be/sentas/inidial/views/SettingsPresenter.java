package be.sentas.inidial.views;

import be.sentas.inidial.model.KeyboardConfig;
import be.sentas.inidial.model.SettingsConfig;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.SettingsPane;
import com.gluonhq.charm.glisten.control.settings.DefaultOption;
import com.gluonhq.charm.glisten.control.settings.Option;
import com.gluonhq.charm.glisten.control.settings.OptionEditor;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import be.sentas.inidial.InidialApp;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

public class SettingsPresenter {

    @FXML
    private View settings;

    @FXML
    private SettingsPane settingsPane;

    public void initialize() {
        settings.setShowTransitionFactory(BounceInRightTransition::new);

        settings.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e ->
                        MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW)));
                appBar.setTitleText("Settings");

                SettingsConfig settings = new SettingsConfig();

                settings.autoDialProperty().addListener((observable, oldValue1, newValue1) -> System.out.println(newValue1));
                settings.keyboardLayout().addListener((observable, oldValue1, newValue1) -> System.out.println(newValue1));

                final Option<BooleanProperty> autoDialOption = new DefaultOption<BooleanProperty>(null,
                        "Auto Dial", "Immediately call when a contact has one number",
                        null, settings.autoDialProperty(), true);
                final DefaultOption<ObjectProperty<KeyboardConfig.Layout>> keyboardLayout = new DefaultOption<>(null,
                        "Keyboard", "Layout of the keyboard", null, settings.keyboardLayout(), true);
                settingsPane.getOptions().addAll(autoDialOption, keyboardLayout);
                settingsPane.setSearchBoxVisible(false);

            }
        });
    }




}
