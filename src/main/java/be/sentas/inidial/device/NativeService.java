package be.sentas.inidial.device;

import be.sentas.inidial.model.Contact;

import java.util.List;

/**
 * Created by yannick on 15/08/16.
 */
public interface NativeService {

    void callNumber(String number);

    void sendTextMessage(String number);

    List<Contact> getContacts();

}
