package be.sentas.inidial.views;

import be.sentas.inidial.model.*;
import be.sentas.inidial.service.InitialsService;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import be.sentas.inidial.InidialApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ContactListPresenter implements Keyboard.OnInteractionListener {

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

    private SimpleStringProperty searchedInitials;

    @Inject
    private StorageService storageService;

    private SettingsConfig settingsConfig;

    private NameDirection direction = NameDirection.LASTFIRST;

    public void initialize() {
        mainView.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                initAppBar();
                storageService.settingsConfigProperty().addListener((observable, oldValue1, newValue1) -> {
                    updateSettings();
                    initKeyboard();
                    initContacts();
                    initHeader();
                });
                storageService.retrieveSettingsConfig();
            }
        });

    }

    private void updateSettings() {
        settingsConfig = storageService.settingsConfigProperty().get();
    }

    private void initAppBar() {
        AppBar appBar = MobileApplication.getInstance().getAppBar();
        appBar.setTitleText("Inidial");
        appBar.getActionItems().add(MaterialDesignIcon.SETTINGS.button(e ->
                MobileApplication.getInstance().switchView(InidialApp.SETTINGS_VIEW)));
    }

    private void initHeader() {
        searchedInitials = new SimpleStringProperty("");
        searchedInitials.addListener((observable, oldValue, newValue) -> searchedInitialsChanged(oldValue, newValue));
        initials.setMaxWidth(Double.MAX_VALUE);
        numberOfMatches.setText("3 matches");
    }

    private void searchedInitialsChanged(String oldValue, String newValue) {
        AppBar appBar = MobileApplication.getInstance().getAppBar();
        if (!newValue.equals("")) {
            if (oldValue.equals("")) {
                appBar.getActionItems().add(0, MaterialDesignIcon.CLEAR.button(e -> clearSearch()));
            }
        } else {
            appBar.getActionItems().remove(0);
        }
        initials.setText(newValue);

    }

    private void initContacts() {
        contactList.setCellFactory(param -> new ContactListCell(direction));
        updateList(FXCollections.observableList(InitialsService.getService(direction).getContacts()));

    }

    private void initKeyboard() {
        keyboard.load(KeyboardConfig.getConfig(settingsConfig.getKeyboardLayout(), toStringList(InitialsService.getService(direction).getAvailableInitials())));
        keyboard.setListener(this);
    }

    private List<String> toStringList(List<InitialChar> availableInitials) {
        List<String> stringList = new ArrayList<>();
        for (InitialChar initialChar : availableInitials) {
            stringList.add(initialChar.getInitial());
        }
        return stringList;
    }

    private void clearSearch() {
        searchedInitials.setValue("");
        keyboard.load(KeyboardConfig.getConfig(settingsConfig.getKeyboardLayout(), toStringList(InitialsService.getService(direction).getAvailableInitials())));
        updateList(FXCollections.observableList(InitialsService.getService(direction).getContacts()));
    }

    @Override
    public void onKeyPressed(String symbol) {
        searchedInitials.set(searchedInitials.getValue().concat(symbol));
        keyboard.load(KeyboardConfig.getConfig(settingsConfig.getKeyboardLayout(), toStringList(InitialsService.getService(direction).getNextCharacters(searchedInitials.getValue()))));
        updateList(FXCollections.observableList(InitialsService.getService(direction).getContactsByInitials(searchedInitials.getValue())));
    }

    private void updateList(ObservableList<Contact> contacts) {
        contactList.setItems(contacts);
    }
    
    /*@FXML
    void buttonClick() {
        label.setText("Hello JavaFX Universe!");
    }*/
    
}
