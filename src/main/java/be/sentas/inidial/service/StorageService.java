package be.sentas.inidial.service;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.MostDialedContactsProvider;
import be.sentas.inidial.model.SettingsConfig;
import com.gluonhq.connect.ConnectState;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.gluoncloud.GluonClient;
import com.gluonhq.connect.gluoncloud.GluonClientBuilder;
import com.gluonhq.connect.gluoncloud.OperationMode;
import com.gluonhq.connect.provider.DataProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by yannick on 14/08/16.
 */
public class StorageService {

    private static final String SETTINGS_CONFIG = "settingsConfig";
    private static final String MOST_DIALED_CONTACTS = "mostDialedContacts";

    private final ObjectProperty<SettingsConfig> settingsConfig = new SimpleObjectProperty<>(new SettingsConfig());
    private final ObjectProperty<MostDialedContactsProvider> mostDialedContacts = new SimpleObjectProperty<>(new MostDialedContactsProvider());

    private GluonClient gluonClient;

    @PostConstruct
    public void postConstruct() {
        gluonClient = GluonClientBuilder.create()
                .operationMode(OperationMode.LOCAL_ONLY)
                .build();
    }

    public void retrieveSettingsConfig() {
        GluonObservableObject<SettingsConfig> gluonSettingsConfig = DataProvider.retrieveObject(
                gluonClient.createObjectDataReader(SETTINGS_CONFIG, SettingsConfig.class));
        gluonSettingsConfig.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv) && gluonSettingsConfig.get() != null) {
                settingsConfig.set(gluonSettingsConfig.get());
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

    public List<String> retrieveMostDialedContacts() {
        GluonObservableObject<MostDialedContactsProvider> mostDialedContacts = DataProvider.retrieveObject(
                gluonClient.createObjectDataReader(MOST_DIALED_CONTACTS, MostDialedContactsProvider.class));
        return mostDialedContacts.get().getMostDialedContactIds();
    }

    public void addDialedContact(Contact contact) {
        GluonObservableObject<MostDialedContactsProvider> mostDialedContactsObservable = DataProvider.retrieveObject(
                gluonClient.createObjectDataReader(MOST_DIALED_CONTACTS, MostDialedContactsProvider.class));
        MostDialedContactsProvider mostDialedContacts = mostDialedContactsObservable.get();
        mostDialedContacts.addDialedContact(contact);
        DataProvider.storeObject(mostDialedContacts,
                gluonClient.createObjectDataWriter(MOST_DIALED_CONTACTS, MostDialedContactsProvider.class));
    }

}
