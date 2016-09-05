package be.sentas.inidial.device;

import be.sentas.inidial.Utils;
import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.Phone;
import be.sentas.inidial.model.PhoneType;
import org.robovm.apple.contacts.CNContact;
import org.robovm.apple.contacts.CNLabeledValue;
import org.robovm.apple.contacts.CNPhoneNumber;
import org.robovm.apple.foundation.NSArray;

import java.util.ArrayList;
import java.util.List;

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
        Contact contact = new Contact(iOSContact.getGivenName(), lastName);
        contact.setId(iOSContact.getIdentifier());
        NSArray<CNLabeledValue<CNPhoneNumber>> phoneNumbers = iOSContact.getPhoneNumbers();
        for (CNLabeledValue<CNPhoneNumber> phoneNumber :phoneNumbers) {
            String typeLabel = phoneNumber.getLabel().toLowerCase();
            PhoneType type;
            if (typeLabel.contains("<home>")) {
                type = PhoneType.HOME;
            } else if (typeLabel.contains("<mobile>")) {
                type = PhoneType.MOBILE;
            } else {
                type = PhoneType.OTHER;
            }
            contact.getNumbers().add(new Phone(phoneNumber.getValue().getStringValue(), type));
            contact.setHasImageData(iOSContact.isImageDataAvailable());
        }
        return contact;
    }
}
