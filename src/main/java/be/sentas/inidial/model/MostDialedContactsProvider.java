package be.sentas.inidial.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yannick on 20/08/16.
 */
public class MostDialedContactsProvider {

    private List<DialedContact> contactsWithDials = new ArrayList<>();

    public void addDialedContact(Contact contact) {
        for (DialedContact dialedContact : contactsWithDials) {
            if (dialedContact.getContactId().equals(contact.getId())) {
                dialedContact.increaseDialCount();
                return;
            }
        }
        contactsWithDials.add(new DialedContact(contact.getId()));
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

    private Comparator<DialedContact> sorter = (o1, o2) -> o1.getDialCount().compareTo(o2.getDialCount());
}
