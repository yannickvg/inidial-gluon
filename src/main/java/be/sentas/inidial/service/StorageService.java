package be.sentas.inidial.service;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.SettingsConfig;
import com.gluonhq.connect.ConnectState;
import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.gluoncloud.GluonClient;
import com.gluonhq.connect.gluoncloud.GluonClientBuilder;
import com.gluonhq.connect.gluoncloud.OperationMode;
import com.gluonhq.connect.gluoncloud.SyncFlag;
import com.gluonhq.connect.provider.DataProvider;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import javax.annotation.PostConstruct;

/**
 * Created by yannick on 14/08/16.
 */
public class StorageService {

    private static final String CONTACTS = "contacts";
    private static final String SETTINGS_CONFIG = "settingsConfig";

    private final ListProperty<Contact> contacts = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<SettingsConfig> settingsConfig = new SimpleObjectProperty<>(new SettingsConfig());

    private GluonClient gluonClient;

    @PostConstruct
    public void postConstruct() {
        gluonClient = GluonClientBuilder.create()
                .operationMode(OperationMode.LOCAL_ONLY)
                .build();
    }

    public void retrieveContacts() {
        GluonObservableList<Contact> gluonContacts = DataProvider.retrieveList(
                gluonClient.createListDataReader(CONTACTS, Contact.class,
                        SyncFlag.LIST_WRITE_THROUGH, SyncFlag.OBJECT_WRITE_THROUGH));

        gluonContacts.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv)) {
                contacts.set(gluonContacts);
            }
        });
    }

    public Contact addContact(Contact contact) {
        contacts.get().add(contact);
        return contact;
    }

    public void removeContact(Contact contact) {
        contacts.get().remove(contact);
    }

    public ListProperty<Contact> contactsProperty() {
        return contacts;
    }

    public void retrieveSettingsConfig() {
        GluonObservableObject<SettingsConfig> gluonSettingsConfig = DataProvider.retrieveObject(
                gluonClient.createObjectDataReader(SETTINGS_CONFIG, SettingsConfig.class));
        gluonSettingsConfig.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv) && gluonSettingsConfig.get() != null) {
                settingsConfig.set(gluonSettingsConfig.get());
            } else {
                storeSettingsConfig();
            }
        });
    }

    public void storeSettingsConfig() {
        DataProvider.<SettingsConfig>storeObject(settingsConfig.get(),
                gluonClient.createObjectDataWriter(SETTINGS_CONFIG, SettingsConfig.class));
    }

    public ObjectProperty<SettingsConfig> settingsConfigProperty() {
        return settingsConfig;
    }

}
