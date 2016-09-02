package be.sentas.inidial.views;

import be.sentas.inidial.InidialApp;
import be.sentas.inidial.device.NativePlatformFactory;
import be.sentas.inidial.device.NativeService;
import be.sentas.inidial.service.InitialsService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SplashPresenter {

    @FXML
    private View splash;

    @FXML
    private ImageView stripes;

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
                InitialsService.createService(nativeService.getContacts());
                Platform.runLater(() -> MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW));
                return null;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

}
