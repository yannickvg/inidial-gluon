package be.sentas.inidial.model;

import com.gluonhq.charm.down.common.PlatformFactory;
import com.gluonhq.charm.down.common.SettingService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yannick on 20/08/16.
 */
public class MostDialedContactsProvider {

    public static final String CONTACTS_KEY = "contacts";
    private static MostDialedContactsProvider instance;

    private MostDialedContactsProvider() {
        service = PlatformFactory.getPlatform().getSettingService();
        contactsWithDials = new Gson().fromJson(service.retrieve(CONTACTS_KEY), new TypeToken<ArrayList<DialedContact>>(){}.getType());
        if (contactsWithDials == null) {
            contactsWithDials = new ArrayList<>();
        }
    }

    public static MostDialedContactsProvider getInstance() {
        if (instance == null) {
            instance = new MostDialedContactsProvider();
        }
        return instance;
    }

    private List<DialedContact> contactsWithDials;
    private SettingService service;

    public void addDialedContact(Contact contact) {
        boolean exists = false;
        for (DialedContact dialedContact : contactsWithDials) {
            if (dialedContact.getContactId().equals(contact.getId())) {
                dialedContact.increaseDialCount();
                exists = true;
                break;
            }
        }
        if (!exists) {
            contactsWithDials.add(new DialedContact(contact.getId()));
        }
        service.store(CONTACTS_KEY, new Gson().toJson(contactsWithDials));
    }

    public List<String> getMostDialedContactIds() {
        Collections.sort(contactsWithDials, sorter);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < ((contactsWithDials.size() > 5)? 5 : contactsWithDials.size()); i++) {
            result.add(contactsWithDials.get(i).getContactId());
        }
        return result;
    }

    public void clear() {
        contactsWithDials = new ArrayList<>();
    }

    private Comparator<DialedContact> sorter = (o1, o2) -> o2.getDialCount().compareTo(o1.getDialCount());
}
