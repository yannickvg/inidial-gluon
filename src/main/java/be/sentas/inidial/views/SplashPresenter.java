package be.sentas.inidial.views;

import be.sentas.inidial.InidialApp;
import be.sentas.inidial.Utils;
import be.sentas.inidial.device.Logger;
import be.sentas.inidial.device.NativePlatformFactory;
import be.sentas.inidial.device.NativeService;
import be.sentas.inidial.model.Contact;
import be.sentas.inidial.service.InitialsService;
import com.gluonhq.charm.down.common.PlatformFactory;
import com.gluonhq.charm.down.common.SettingService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashPresenter {

    public static final String LAST_SYNC_DATE = "lastSyncDate";
    public static final String CONTACTS = "contacts";
    public static final int INTERVAL_IN_MILLIS = 1000 * 60 * 60 * 24;

    @FXML
    private View splash;

    @FXML
    private ImageView stripes;

    private SettingService settingService = PlatformFactory.getPlatform().getSettingService();

    final NativeService nativeService = NativePlatformFactory.getPlatform().getNativeService();

    public void initialize() {

        splash.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                MobileApplication.getInstance().getAppBar().setVisible(false);
                RotateTransition rt = new RotateTransition(Duration.millis(6000), stripes);
                rt.setByAngle(360);
                rt.setCycleCount(Animation.INDEFINITE);
                rt.setInterpolator(Interpolator.LINEAR);
                rt.play();
            }
        });

        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                List<Contact> contacts;
                if (syncNeeded()) {
                    contacts = nativeService.getContacts();
                    storeContacts(contacts);
                } else {
                    contacts = retrieveContacts();
                }
                InitialsService.createService(contacts);
                Platform.runLater(() -> MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW));
                return null;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

    private List<Contact> retrieveContacts() {
        Type listType = new TypeToken<ArrayList<Contact>>(){}.getType();
        return new Gson().fromJson(settingService.retrieve(CONTACTS), listType);
    }

    private void storeContacts(List<Contact> contacts) {
        settingService.store(CONTACTS, new Gson().toJson(contacts));
    }

    private boolean syncNeeded() {
        boolean syncNeeded;
        try {
            long lastSync = Long.valueOf(settingService.retrieve(LAST_SYNC_DATE));
            syncNeeded = Utils.hasTimeIntervalPassed(new Date(lastSync), INTERVAL_IN_MILLIS);
        } catch (NumberFormatException e) {
            syncNeeded = true;
        }
        if (syncNeeded) {
            settingService.store(LAST_SYNC_DATE, String.valueOf(new Date().getTime()));
        }
        return syncNeeded;
    }

}
