package be.sentas.inidial;

import be.sentas.inidial.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 11/08/16.
 */
public class Service {

    private static Service service;

    private List<Contact> contacts;

    private Service() {
        contacts = new ArrayList<>();
        contacts.add(new Contact("Yannick", "Van Godtsenhoven"));
        contacts.add(new Contact("Liesbeth", "Toorman"));
        contacts.add(new Contact("Pieter", "Jagers"));
        contacts.add(new Contact("Herman", "Toorman"));
        contacts.add(new Contact("Yannick", "Van Godtsenhoven"));
        contacts.add(new Contact("Liesbeth", "Toorman"));
        contacts.add(new Contact("Pieter", "Jagers"));
        contacts.add(new Contact("Herman", "Toorman"));
        contacts.add(new Contact("Yannick", "Van Godtsenhoven"));
        contacts.add(new Contact("Liesbeth", "Toorman"));
        contacts.add(new Contact("Pieter", "Jagers"));
        contacts.add(new Contact("Herman", "Toorman"));
    }

    public static Service getService() {
        if (service == null) {
            service = new Service();
        }
        return service;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
