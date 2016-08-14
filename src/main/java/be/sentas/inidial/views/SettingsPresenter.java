package be.sentas.inidial.views;

import be.sentas.inidial.model.KeyboardConfig;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
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

import javax.inject.Inject;

public class SettingsPresenter {

    @FXML
    private View settings;

    @FXML
    private SettingsPane settingsPane;

    @Inject
    private StorageService storageService;

    private SettingsConfig settingsConfig;

    public void initialize() {
        settings.setShowTransitionFactory(BounceInRightTransition::new);

        settings.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e ->
                        MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW)));
                appBar.setTitleText("Settings");

                settingsConfig = new SettingsConfig();
                updateSettings(storageService.settingsConfigProperty().get());

                settingsConfig.autoDialProperty().addListener((observable, ov, nv) -> updateServiceAutoDial(nv));
                settingsConfig.keyboardLayout().addListener((observable, ov, nv) -> updateServiceKeyboardLayout(nv));

                final Option<BooleanProperty> autoDialOption = new DefaultOption<>(null,
                        "Auto Dial", "Immediately call when a contact has one number",
                        null, settingsConfig.autoDialProperty(), true);

                final DefaultOption<ObjectProperty<KeyboardConfig.Layout>> keyboardLayout = new DefaultOption<>(null,
                        "Keyboard", "Layout of the keyboard", null, settingsConfig.keyboardLayout(), true);

                settingsPane.getOptions().addAll(autoDialOption, keyboardLayout);
                settingsPane.setSearchBoxVisible(false);

                storageService.retrieveSettingsConfig();

            }
        });
    }

    private void updateServiceKeyboardLayout(KeyboardConfig.Layout nv) {
        storageService.settingsConfigProperty().getValue().setKeyboardLayout(nv);
        storageService.storeSettingsConfig();
    }

    private void updateServiceAutoDial(Boolean nv) {
        storageService.settingsConfigProperty().getValue().setAutodial(nv);
        storageService.storeSettingsConfig();
    }

    private void updateSettings(SettingsConfig settingsConfig) {
        this.settingsConfig.setAutodial(settingsConfig.isAutoDial());
        this.settingsConfig.setKeyboardLayout(settingsConfig.getKeyboardLayout());
    }
}
