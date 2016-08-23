package be.sentas.inidial.views;

import be.sentas.inidial.device.NativePlatformFactory;
import be.sentas.inidial.device.NativeService;
import be.sentas.inidial.model.*;
import be.sentas.inidial.service.InitialsService;
import be.sentas.inidial.service.StorageService;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.layout.Layer;
import com.gluonhq.charm.glisten.layout.MobileLayoutPane;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import be.sentas.inidial.InidialApp;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ContactListPresenter implements Keyboard.OnInteractionListener, ContactListCell.OnInteractionListener, ContactDetailOverlay.OnInteractionListener {

    @FXML
    private View mainView;

    @FXML
    private CharmListView<Contact, String> contactList;

    @FXML
    private Label emptyView;

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

    final NativeService nativeService = NativePlatformFactory.getPlatform().getNativeService();
    private Button clearButton;

    public void initialize() {
        mainView.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                initAppBar();
                initScreenContent();
                storageService.settingsConfigProperty().addListener((observable, oldValue1, newValue1) -> {
                    if (!oldValue1.equals(newValue1)) {
                        initScreenContent();
                    }
                });
                storageService.retrieveSettingsConfig();
            }
        });

    }

    private void initScreenContent() {
        updateSettings();
        initContacts();
        initKeyboard();
        initHeader();
        showMostDialedContacts();
    }

    private void showMostDialedContacts() {
        initials.setText("Most Dialed Contacts");
        List<String> identifiers = MostDialedContactsProvider.getInstance().getMostDialedContactIds();
        if (identifiers.isEmpty()) {
            updateList(FXCollections.observableArrayList());
            showEmptyView();
        } else {
            List<Contact> contacts = new ArrayList<>();
            for (String id: identifiers) {
                contacts.add(InitialsService.getService(settingsConfig.getNameDirection()).getContact(id));
            }
            updateList(FXCollections.observableList(contacts));
            showContactList();
        }
    }

    private void showContactList() {
        contactList.setVisible(true);
        emptyView.setVisible(false);
    }

    private void showEmptyView() {
        contactList.setVisible(false);
        emptyView.setVisible(true);
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
    }

    private void searchedInitialsChanged(String oldValue, String newValue) {
        AppBar appBar = MobileApplication.getInstance().getAppBar();
        if (!newValue.equals("")) {
            if (oldValue.equals("")) {
                clearButton = MaterialDesignIcon.CLEAR.button(e -> clearSearch());
                appBar.getActionItems().add(0, clearButton);
            }
        } else {
            appBar.getActionItems().remove(0);
        }
        initials.setText(newValue);

    }

    private void initContacts() {
        InitialsService.initService(settingsConfig.getNameDirection());
        contactList.setCellFactory(param -> new ContactListCell(settingsConfig.getNameDirection(), this));
    }

    private void initKeyboard() {
        keyboard.load(KeyboardConfig.getConfig(settingsConfig.getKeyboardLayout(), toStringList(InitialsService.getService(settingsConfig.getNameDirection()).getAvailableInitials())));
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
        keyboard.load(KeyboardConfig.getConfig(settingsConfig.getKeyboardLayout(), toStringList(InitialsService.getService(settingsConfig.getNameDirection()).getAvailableInitials())));
        numberOfMatches.setText("");
        showMostDialedContacts();
        if (clearButton != null) {
            MobileApplication.getInstance().getAppBar().getActionItems().remove(clearButton);
            clearButton = null;
        }
    }

    @Override
    public void onKeyPressed(String symbol) {
        searchedInitials.set(searchedInitials.getValue().concat(symbol));
        keyboard.load(KeyboardConfig.getConfig(settingsConfig.getKeyboardLayout(), toStringList(InitialsService.getService(settingsConfig.getNameDirection()).getNextCharacters(searchedInitials.getValue()))));
        ObservableList<Contact> contacts = FXCollections.observableList(InitialsService.getService(settingsConfig.getNameDirection()).getContactsByInitials(searchedInitials.getValue()));
        updateList(contacts);
        showContactList();
        if (contacts.size() == 1) {
            numberOfMatches.setText("1 match");
        } else {
            numberOfMatches.setText(contacts.size() + " matches");
        }

    }

    private void updateList(ObservableList<Contact> contacts) {
        contactList.setItems(contacts);
    }

    @Override
    public void onItemClicked(Contact contact) {
        if (settingsConfig.isAutoDial() && contact.hasOnlyOneNumber()) {
            onCallNumber(contact.getNumbers().get(0), contact);
        } else {
            ContactDetailOverlay.show(storageService, this, contact);
        }
    }

    @Override
    public void onCallNumber(Phone phone, Contact contact) {
        MostDialedContactsProvider.getInstance().addDialedContact(contact);
        nativeService.callNumber(phone.getNumber());
        clearSearch();
    }

    @Override
    public void onTextNumber(Phone phone, Contact contact) {
        MostDialedContactsProvider.getInstance().addDialedContact(contact);
        nativeService.sendTextMessage(phone.getNumber());
        clearSearch();
    }
}
