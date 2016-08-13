package be.sentas.inidial;

import be.sentas.inidial.views.ContactListView;
import be.sentas.inidial.views.SettingsView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InidialApp extends MobileApplication {

    public static final String CONTACTS_VIEW = HOME_VIEW;
    public static final String SETTINGS_VIEW = "Settings View";
    public static final String MENU_LAYER = "Side Menu";
    
    @Override
    public void init() {
        addViewFactory(CONTACTS_VIEW, () -> (View) new ContactListView().getView());
        addViewFactory(SETTINGS_VIEW, () -> (View) new SettingsView().getView());
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.AMBER.assignTo(scene);

        scene.getStylesheets().add(InidialApp.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(InidialApp.class.getResourceAsStream("/icon.png")));
    }
}
