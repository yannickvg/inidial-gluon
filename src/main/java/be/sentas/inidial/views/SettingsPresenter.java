package be.sentas.inidial.views;

import be.sentas.inidial.InidialApp;
import be.sentas.inidial.model.KeyboardConfig;
import be.sentas.inidial.model.NameDirection;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.SettingsPane;
import com.gluonhq.charm.glisten.control.settings.DefaultOption;
import com.gluonhq.charm.glisten.control.settings.Option;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;

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
                storageService.settingsConfigProperty().addListener((observable, ov, nv) -> updateSettings(nv));

                settingsConfig.autoDialProperty().addListener((observable, ov, nv) -> updateServiceAutoDial(nv));
                settingsConfig.keyboardLayout().addListener((observable, ov, nv) -> updateServiceKeyboardLayout(nv));
                settingsConfig.nameDirection().addListener((observable, ov, nv) -> updateServiceNameDirection(nv));

                final Option<BooleanProperty> autoDialOption = new DefaultOption<>(null,
                        "Auto Dial", "Immediately call when a contact has one number",
                        null, settingsConfig.autoDialProperty(), true);

                final DefaultOption<ObjectProperty<KeyboardConfig.Layout>> keyboardLayout = new DefaultOption<>(null,
                        "Keyboard", "Layout of the keyboard", null, settingsConfig.keyboardLayout(), true);

                final DefaultOption<ObjectProperty<NameDirection>> nameDirection = new DefaultOption<>(null,
                        "Name Order", "Order of the name parts", null, settingsConfig.nameDirection(), true);

                settingsPane.getOptions().addAll(autoDialOption, keyboardLayout, nameDirection);
                settingsPane.setSearchBoxVisible(false);

                //storageService.retrieveSettingsConfig();

            }
        });
    }

    private void updateServiceNameDirection(NameDirection nv) {
        storageService.settingsConfigProperty().getValue().setNameDirection(nv);
        storageService.storeSettingsConfig();
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
        this.settingsConfig.setNameDirection(settingsConfig.getNameDirection());
    }
}
