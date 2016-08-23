package be.sentas.inidial.views;

import be.sentas.inidial.InidialApp;
import be.sentas.inidial.device.NativePlatformFactory;
import be.sentas.inidial.device.NativeService;
import be.sentas.inidial.service.InitialsService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SplashPresenter {

    @FXML
    private VBox container;

    final NativeService nativeService = NativePlatformFactory.getPlatform().getNativeService();

    public void initialize() {
        InitialsService.createService(nativeService.getContacts());
        Platform.runLater(() -> MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW));
    }

}
