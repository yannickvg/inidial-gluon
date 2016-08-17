package be.sentas.inidial.device;

import be.sentas.inidial.Utils;
import be.sentas.inidial.model.Contact;
import org.robovm.apple.contacts.CNContact;
import org.robovm.apple.foundation.NSArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 17/08/16.
 */
public class ContactsMapper {
    public static List<Contact> map(NSArray<CNContact> iOSContacts) {
        List<Contact> contacts = new ArrayList<>();

        for (CNContact iOSContact : iOSContacts) {
            contacts.add(map(iOSContact));
        }
        return contacts;
    }

    public static Contact map(CNContact iOSContact) {
        String lastName = Utils.isNotBlank(iOSContact.getMiddleName()) ? iOSContact.getMiddleName() + " " + iOSContact.getFamilyName()  : iOSContact.getFamilyName();
        return new Contact(iOSContact.getGivenName(), lastName);
    }
}
