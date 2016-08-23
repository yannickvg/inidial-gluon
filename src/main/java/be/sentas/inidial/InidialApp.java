package be.sentas.inidial;

import be.sentas.inidial.views.ContactListView;
import be.sentas.inidial.views.SettingsView;
import be.sentas.inidial.views.SplashView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InidialApp extends MobileApplication {

    public static final String SPLASH_VIEW = HOME_VIEW;
    public static final String CONTACTS_VIEW = "Contacts List View";
    public static final String SETTINGS_VIEW = "Settings View";
    
    @Override
    public void init() {
        addViewFactory(SPLASH_VIEW, () -> (View) new SplashView().getView());
        addViewFactory(CONTACTS_VIEW, () -> (View) new ContactListView().getView());
        addViewFactory(SETTINGS_VIEW, () -> (View) new SettingsView().getView());
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.AMBER.assignTo(scene);

        scene.getStylesheets().add(InidialApp.class.getResource("style.css").toExternalForm());
        scene.getStylesheets().add(InidialApp.class.getResource("views/contact_detail_dialog.css").toExternalForm());
        scene.getStylesheets().add(InidialApp.class.getResource("views/phone_list_item.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(InidialApp.class.getResourceAsStream("/icon.png")));
    }
}
