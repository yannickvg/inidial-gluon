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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Arrays;

public class ContactListPresenter {

    @FXML
    private View mainView;

    @FXML
    private CharmListView<Contact, String> contactList;

    @FXML
    private Keyboard keyboard;

    @FXML
    private Label initials;

    @FXML
    private Label numberOfMatches;

    public void initialize() {
        mainView.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Inidial");
                appBar.getActionItems().add(MaterialDesignIcon.CLEAR.button(e ->
                        System.out.println("Clear")));

                appBar.getActionItems().add(MaterialDesignIcon.SETTINGS.button(e ->
                        MobileApplication.getInstance().switchView(InidialApp.SETTINGS_VIEW)));

                ObservableList<Contact> contacts = FXCollections.observableList(Service.getService().getContacts());
                contactList.setCellFactory(param -> new ContactListCell());
                contactList.setItems(contacts);

                keyboard.setMaxWidth(mainView.getScene().getWidth());
                keyboard.load(KeyboardConfig.getConfig(KeyboardConfig.Layout.QWERTY, Arrays.asList("A", "B", "C")));

                initials.setText("YVG");
                initials.setMaxWidth(Double.MAX_VALUE);
                numberOfMatches.setText("3 matches");
            }
        });
    }
    
    /*@FXML
    void buttonClick() {
        label.setText("Hello JavaFX Universe!");
    }*/
    
}
