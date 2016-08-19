package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.Layer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;

import static com.gluonhq.charm.glisten.application.GlassPane.DEFAULT_BACKGROUND_FADE_LEVEL;

/**
 * Created by yannick on 11/08/16.
 */
public class ContactDetailOverlay extends Layer implements ContactDetailDialog.OnInteractionListener {

    private static ContactDetailOverlay overlay;

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
        relocateOverlayToCenter();
        showDialog(contact);
    }

    private void showDialog(Contact contact) {
        final ContactDetailDialog dialog = new ContactDetailDialog(storageService, this, contact);
        getChildren().add(dialog);
        dialog.show();
    }


    private void relocateOverlayToCenter() {
        GlassPane glassPane = MobileApplication.getInstance().getGlassPane();
        double newX = (glassPane.getWidth()) / 2;
        setLayoutX(newX - getLayoutBounds().getMinX());
        double newY = glassPane.getHeight() * 0.75;
        setLayoutY(newY - getLayoutBounds().getMinY());
    }

    public static void show(StorageService storageService, OnInteractionListener listener, Contact contact) {
        overlay = new ContactDetailOverlay(storageService, listener, contact);
        MobileApplication.getInstance().getGlassPane().setBackgroundFade(DEFAULT_BACKGROUND_FADE_LEVEL);
        MobileApplication.getInstance().getGlassPane().getLayers().add(overlay);
    }

    @Override
    public void onNumberSelected(String number) {

    }

    @Override
    public void onCancelled() {
        MobileApplication.getInstance().getGlassPane().setBackgroundFade(0);
        MobileApplication.getInstance().getGlassPane().getLayers().remove(overlay);
        overlay = null;
    }


    interface OnInteractionListener {
        void onNumberSelected(String number);
        void onCancelled();
    }


}
