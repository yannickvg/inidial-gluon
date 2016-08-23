package be.sentas.inidial.views;

import be.sentas.inidial.InidialApp;
import be.sentas.inidial.model.SettingsConfig;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

public class SplashPresenter {

    @FXML
    private VBox container;

    @Inject
    private StorageService storageService;

    private SettingsConfig settingsConfig;

    public void initialize() {

        new Thread() {

            // runnable for that thread
            public void run() {
                try {
                    Thread.sleep(5000);
                    Platform.runLater(() -> {
                        MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW);
                    });
                } catch (InterruptedException e) {

                }
            }
        }.start();
    }

}
