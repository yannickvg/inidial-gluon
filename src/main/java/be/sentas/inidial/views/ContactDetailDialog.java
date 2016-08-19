package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.SwatchElement;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactDetailDialog extends VBox {

    @FXML
    private VBox dialog;

    @FXML
    private HBox header;

    @FXML
    private Label name;

    private StorageService storageService;

    private SettingsConfig settingsConfig;

    private OnInteractionListener listener;
    private GlassPane glassPane;

    private Contact contact;

    public ContactDetailDialog(StorageService storageService,
                               OnInteractionListener listener, Contact contact) {
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
        this.contact = contact;
        glassPane = MobileApplication.getInstance().getGlassPane();

        initSettings(storageService);
        handleLocation();
        initHeader();

    }

    private void initHeader() {
        name.setText(contact.getDisplayName(settingsConfig.getNameDirection()));
        Button cancelButton = MaterialDesignIcon.CLEAR.button(e -> hide());
        HBox.setMargin(cancelButton, new Insets(0, 5, 0, 0));
        header.getChildren().add(cancelButton);
        Rectangle divider = new Rectangle();
        divider.setWidth(glassPane.getWidth());
        divider.setHeight(1);
        divider.setFill(Swatch.AMBER.getColor(SwatchElement.PRIMARY_500));
        dialog.getChildren().add(1, divider);
    }

    private void handleLocation() {
        setMinWidth(MobileApplication.getInstance().getGlassPane().getWidth());
        setMinHeight(MobileApplication.getInstance().getGlassPane().getHeight()/2);
        setDialogOffScreen();
    }

    private void initSettings(StorageService storageService) {
        this.storageService = storageService;
        this.storageService.settingsConfigProperty().addListener((observable, oldValue1, newValue1) -> {
            updateSettings();
        });
        settingsConfig = storageService.settingsConfigProperty().get();
        this.storageService.retrieveSettingsConfig();
    }

    public void show() {
        moveDialogUp();
    }

    public void hide() {
        moveDialogDown();

    }

    private void setDialogOffScreen() {
        setTranslateY(glassPane.getHeight()/2);
    }

    private void moveDialogUp() {
        TranslateTransition transitionUp = new TranslateTransition(Duration.millis(300), this);
        transitionUp.setByY(-glassPane.getHeight()/2);
        transitionUp.play();
    }

    private void moveDialogDown() {
        TranslateTransition transitionDown = new TranslateTransition(Duration.millis(200), this);
        transitionDown.setByY(glassPane.getHeight()/2);
        transitionDown.setOnFinished(event -> listener.onCancelled());
        transitionDown.play();
    }

    private void updateSettings() {
        settingsConfig = storageService.settingsConfigProperty().get();
    }

    interface OnInteractionListener {
        void onNumberSelected(String number);
        void onCancelled();
    }

}
