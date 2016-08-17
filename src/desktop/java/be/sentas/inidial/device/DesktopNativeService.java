package be.sentas.inidial.device;

import be.sentas.inidial.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 15/08/16.
 */
public class DesktopNativeService implements NativeService {
    @Override
    public void callNumber(String number) {
        System.out.println("Calling number: " + number);
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Yannick", "Van Godtsenhoven"));
        contacts.add(new Contact("Liesbeth", "Toorman"));
        contacts.add(new Contact("Pieter", "Jagers"));
        contacts.add(new Contact("Herman", "Toorman"));
        return contacts;
    }
}
