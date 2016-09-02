package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.Phone;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.SwatchElement;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactDetailDialog extends VBox implements PhoneListCell.OnInteractionListener {

    @FXML
    private VBox dialog;

    @FXML
    private HBox header;

    @FXML
    private Label name;

    @FXML
    private CharmListView<Phone, String> phoneNumbers;

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
        initPhoneNumbers();
    }

    private void initPhoneNumbers() {
        phoneNumbers.setCellFactory(param -> new PhoneListCell(this));
        phoneNumbers.setItems(FXCollections.observableList(contact.getNumbers()));
    }

    private void initHeader() {
        name.setText(contact.getDisplayName(settingsConfig.getNameDirection()));
        Button cancelButton = MaterialDesignIcon.CLEAR.button(e -> cancel());
        cancelButton.setStyle("-fx-background-color: #2284c5");
        HBox.setMargin(cancelButton, new Insets(10, 16, 10, 0));
        header.getChildren().add(cancelButton);
        Rectangle divider = new Rectangle();
        divider.setWidth(glassPane.getWidth());
        divider.setHeight(1);
        divider.setFill(Swatch.BLUE.getColor(SwatchElement.PRIMARY_500));
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

    void show() {
        moveDialogUp();
    }



    private void updateSettings() {
        settingsConfig = storageService.settingsConfigProperty().get();
    }

    @Override
    public void onCallNumber(Phone item) {
        Consumer<Phone> onCall = (phone) -> listener.onCallNumber(phone, contact);
        moveDialogDown(item, onCall);
    }

    @Override
    public void onTextNumber(Phone item) {
        Consumer<Phone> onText = (phone) -> listener.onTextNumber(phone, contact);
        moveDialogDown(item, onText);
    }

    private void setDialogOffScreen() {
        setTranslateY(glassPane.getHeight()/2);
    }

    private void moveDialogUp() {
        TranslateTransition transitionUp = new TranslateTransition(Duration.millis(300), this);
        transitionUp.setByY(-glassPane.getHeight()/2);
        transitionUp.play();
    }

    void cancel() {
        moveDialogDown(null, null);
    }

    private void moveDialogDown(Phone phone, Consumer<Phone> doAction) {
        TranslateTransition transitionDown = new TranslateTransition(Duration.millis(200), this);
        transitionDown.setByY(glassPane.getHeight()/2);
        transitionDown.setOnFinished(event -> {
            if (phone != null) {
                doAction.accept(phone);
            } else {
                listener.onCancelled();
            }
        });
        transitionDown.play();
    }

    interface OnInteractionListener {
        void onCallNumber(Phone phone, Contact contact);
        void onTextNumber(Phone phone, Contact contact);
        void onCancelled();
    }

}
