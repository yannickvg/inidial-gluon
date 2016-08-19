package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.layout.Layer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactDetailDialog extends VBox {

    @FXML
    private Label name;

    private StorageService storageService;

    private SettingsConfig settingsConfig;

    private ContactDetailOverlay.OnInteractionListener listener;

    public ContactDetailDialog(StorageService storageService,
                               ContactDetailOverlay.OnInteractionListener listener, Contact contact) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "contact_detail_dialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.listener = listener;

        this.storageService = storageService;
        this.storageService.settingsConfigProperty().addListener((observable, oldValue1, newValue1) -> {
            updateSettings();
        });
        settingsConfig = storageService.settingsConfigProperty().get();
        this.storageService.retrieveSettingsConfig();

        clear();
        name.setText(contact.getDisplayName(settingsConfig.getNameDirection()));
    }

    private void updateSettings() {
        settingsConfig = storageService.settingsConfigProperty().get();
    }

    private void clear() {
        name.setText("");
    }

}
