package be.sentas.inidial.views;

import be.sentas.inidial.Service;
import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.KeyboardConfig;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import be.sentas.inidial.InidialApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.Arrays;

public class ContactListPresenter {

    @FXML
    private View mainView;

    @FXML
    private CharmListView<Contact, String> contactList;

    @FXML
    private Keyboard keyboard;

    public void initialize() {
        mainView.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(InidialApp.MENU_LAYER)));
                appBar.setTitleText("Inidial");
                appBar.getActionItems().add(MaterialDesignIcon.CLEAR.button(e ->
                        System.out.println("Clear")));

                ObservableList<Contact> contacts = FXCollections.observableList(Service.getService().getContacts());
                contactList.setCellFactory(param -> new ContactListCell());
                contactList.setItems(contacts);

                System.out.println(mainView.getScene().getWidth());
                keyboard.setMaxWidth(mainView.getScene().getWidth());
                keyboard.load(KeyboardConfig.getConfig(KeyboardConfig.Layout.QWERTY, Arrays.asList("A", "B", "C")));
            }
        });
    }
    
    /*@FXML
    void buttonClick() {
        label.setText("Hello JavaFX Universe!");
    }*/
    
}
