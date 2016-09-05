package be.sentas.inidial.views;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.Phone;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.Layer;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import static com.gluonhq.charm.glisten.application.GlassPane.DEFAULT_BACKGROUND_FADE_LEVEL;

public class ContactDetailOverlay extends Layer implements ContactDetailDialog.OnInteractionListener {

    private static ContactDetailOverlay overlay;

    private OnInteractionListener listener;

    private StorageService storageService;

    private ContactDetailDialog dialog;

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

    private void relocateOverlayToCenter() {
        GlassPane glassPane = MobileApplication.getInstance().getGlassPane();
        double newX = (glassPane.getWidth()) / 2;
        setLayoutX(newX - getLayoutBounds().getMinX());
        double newY = glassPane.getHeight() * 0.75;
        setLayoutY(newY - getLayoutBounds().getMinY());
    }

    public static void show(StorageService storageService, OnInteractionListener listener, Contact contact) {
        overlay = new ContactDetailOverlay(storageService, listener, contact);
        overlay.showOverlay();
    }

    public static void close() {
        overlay.cancel();
    }

    @Override
    public void onCallNumber(Phone phone, Contact contact) {
        listener.onCallNumber(phone, contact);
        closeOverlay();
    }

    @Override
    public void onTextNumber(Phone phone, Contact contact) {
        listener.onTextNumber(phone, contact);
        closeOverlay();
    }

    private void cancel() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    public void onCancelled() {
        listener.onCancelled();
        closeOverlay();
    }

    private void showDialog(Contact contact) {
        dialog = new ContactDetailDialog(storageService, this, contact);
        getChildren().add(dialog);
        dialog.show();
    }

    private void showOverlay() {
        MobileApplication.getInstance().getGlassPane().setBackgroundFade(DEFAULT_BACKGROUND_FADE_LEVEL);
        MobileApplication.getInstance().getGlassPane().getLayers().add(this);
    }

    private void closeOverlay() {
        MobileApplication.getInstance().getGlassPane().setBackgroundFade(0);
        MobileApplication.getInstance().getGlassPane().getLayers().remove(this);
        overlay = null;
    }

    interface OnInteractionListener {
        void onCallNumber(Phone phone, Contact contact);
        void onTextNumber(Phone phone, Contact contact);
        void onCancelled();
    }


}
