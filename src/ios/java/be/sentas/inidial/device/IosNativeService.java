package be.sentas.inidial.device;

import be.sentas.inidial.model.Contact;
import org.robovm.apple.contacts.*;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.foundation.NSPredicate;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIAlertView;
import org.robovm.apple.uikit.UIApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yannick on 15/08/16.
 */
public class IosNativeService implements NativeService {

    @Override
    public void callNumber(String number) {
        String phoneNumberUrl = "tel:" + number;
        UIApplication.getSharedApplication().openURL(new NSURL(phoneNumberUrl));
    }

    @Override
    public void sendTextMessage(String number) {
        String phoneNumberUrl = "sms:" + number;
        UIApplication.getSharedApplication().openURL(new NSURL(phoneNumberUrl));
    }

    @Override
    public List<Contact> getContacts() {
        CNContactStore store = new CNContactStore();

        CNAuthorizationStatus authorizationStatus = CNContactStore.getAuthorizationStatusForEntityType(CNEntityType.Contacts);

        switch (authorizationStatus) {
            case NotDetermined:
                store.requestAccessForEntityType(CNEntityType.Contacts, (authorized, nsError) -> {
                    if (authorized) {
                        retrieveContactsWithStore(store);
                    } else {
                        //inform user of needed access
                    }
                });
                break;
            case Authorized:
                return retrieveContactsWithStore(store);
            default:
                //inform user of needed access
        }
        return new ArrayList<>();
    }

    private List<Contact> retrieveContactsWithStore(CNContactStore store) {

        List<Contact> contacts = new ArrayList<>();

        try {
            NSArray<CNGroup> groupsMatchingPredicate = store.getGroupsMatchingPredicate(null);

            for (CNGroup group : groupsMatchingPredicate) {
                NSPredicate predicate = CNContact.getPredicateForContactsInGroup(group.getIdentifier());
                NSArray<CNContact> iOSContacts = store.getUnifiedContactsMatchingPredicate(predicate, Arrays.asList(CNContactPropertyKey.GivenName, CNContactPropertyKey.MiddleName, CNContactPropertyKey.FamilyName, CNContactPropertyKey.PhoneNumbers,
                        CNContactPropertyKey.ImageDataAvailable, CNContactPropertyKey.ImageData));
                contacts.addAll(ContactsMapper.map(iOSContacts));
            }

        } catch (NSErrorException e) {
            //TODO errorhandling
        }

        return contacts;

    }
}