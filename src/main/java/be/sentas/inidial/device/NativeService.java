package be.sentas.inidial.device;

import be.sentas.inidial.model.Contact;

import java.util.List;

public interface NativeService {

    void callNumber(String number);

    void sendTextMessage(String number);

    List<Contact> getContacts();

    byte[] getContactPicture(String contactId);

}
