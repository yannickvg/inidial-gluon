package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.KeyboardConfig;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.Layer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactDetailOverlay extends Layer {

    private OnInteractionListener listener;

    private StorageService storageService;

    public ContactDetailOverlay(StorageService storageService, OnInteractionListener listener, Contact contact) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "contact_detail_overlay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.listener = listener;
        this.storageService = storageService;
        showDialog(contact);
    }

    private void showDialog(Contact contact) {
        final ContactDetailDialog dialog = new ContactDetailDialog(storageService, listener, contact);
        getChildren().add(dialog);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), dialog);
        tt.setByY(100f);
        tt.setCycleCount(Timeline.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();
        relocate();
    }

    private void relocate() {
        double newX = (MobileApplication.getInstance().getGlassPane().getWidth()) / 2;
        setLayoutX(newX - getLayoutBounds().getMinX());
        double newY = (MobileApplication.getInstance().getGlassPane().getHeight()) / 2;
        setLayoutY(newY - getLayoutBounds().getMinY());
    }

    interface OnInteractionListener {
        void onNumberSelected(String number);
        void onCancelled();
    }

}
