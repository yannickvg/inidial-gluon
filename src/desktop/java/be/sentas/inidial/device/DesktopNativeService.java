package be.sentas.inidial.device;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.Phone;
import be.sentas.inidial.model.PhoneType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 15/08/16.
 */
public class DesktopNativeService implements NativeService {
    @Override
    public void callNumber(String number) {
        System.out.println("Call " + number);
    }

    @Override
    public void sendTextMessage(String number) {
        System.out.println("Send text message to " + number);
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        Contact yannick = new Contact("Yannick", "Van Godtsenhoven");
        yannick.getNumbers().add(new Phone("12345", PhoneType.HOME));
        yannick.getNumbers().add(new Phone("12345", PhoneType.MOBILE));
        contacts.add(yannick);
        Contact liesbeth = new Contact("Liesbeth", "Toorman");
        liesbeth.getNumbers().add(new Phone("12345", PhoneType.HOME));
        liesbeth.getNumbers().add(new Phone("12345", PhoneType.MOBILE));
        liesbeth.getNumbers().add(new Phone("12345", PhoneType.OTHER));
        contacts.add(liesbeth);
        Contact pieter = new Contact("Pieter", "Jagers");
        pieter.getNumbers().add(new Phone("12345", PhoneType.OTHER));
        pieter.getNumbers().add(new Phone("12345", PhoneType.MOBILE));
        contacts.add(pieter);
        Contact herman = new Contact("Herman", "Toorman");
        herman.getNumbers().add(new Phone("12345", PhoneType.HOME));
        contacts.add(herman);
        return contacts;
    }
}
