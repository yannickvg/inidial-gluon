package be.sentas.inidial.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.layout.layer.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import be.sentas.inidial.InidialApp;
import javafx.fxml.FXML;

public class SettingsPresenter {

    @FXML
    private View settings;

    public void initialize() {
        settings.setShowTransitionFactory(BounceInRightTransition::new);
        
        settings.getLayers().add(new FloatingActionButton(MaterialDesignIcon.INFO.text, 
            e -> System.out.println("Info")));
        
        settings.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e ->
                        MobileApplication.getInstance().switchView(InidialApp.CONTACTS_VIEW)));
                appBar.setTitleText("Settings");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
    }
}
