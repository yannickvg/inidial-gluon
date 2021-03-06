package be.sentas.inidial.service;

import be.sentas.inidial.Utils;
import be.sentas.inidial.model.*;

import java.util.*;

public class InitialsService {

    private static InitialsService service;

    private List<Contact> contacts;

    private Map<String, List<Contact>> contactsMap;
    private InitialsMap initialsMap;

    private NameDirection direction;

    private InitialsService(NameDirection direction) {
        contacts = new ArrayList<>();
        contacts.add(new Contact("Yannick", "Van Godtsenhoven"));
        contacts.add(new Contact("Liesbeth", "Toorman"));
        contacts.add(new Contact("Pieter", "Jagers"));
        contacts.add(new Contact("Herman", "Toorman"));
        this.direction = direction;
        fillCombined(direction);
    }

    private InitialsService(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public static InitialsService getService(NameDirection direction) {
        if (service == null || service.initialsMap == null) {
            throw new IllegalStateException("Service not initialized, call createService and initService first");
        }
        if (!service.getDirection().equals(direction)) {
            service.fillCombined(direction);
        }
        return service;
    }

    public static void createService(List<Contact> contacts) {
        service = new InitialsService(contacts);
    }

    public static InitialsService initService(NameDirection direction) {
        if (service == null) {
            throw new IllegalStateException("Service not created, call createService first");
        }
        service.fillCombined(direction);
        return service;
    }

    public NameDirection getDirection() {
        return direction;
    }

    private void fillCombined(NameDirection direction) {
        this.direction = direction;
        initialsMap = new InitialsMap();
        contactsMap = new HashMap<>();
        for (Contact person : contacts) {
            StringBuilder builder = new StringBuilder();
            InitialChar character = null;
            InitialChar previousCharacter = null;
            if (Utils.isNotBlank(person.getDisplayName(direction))) {
                String[] splittedName = person.getDisplayName(direction).split(
                        " ");
                for (String namePart : splittedName) {
                    String firstCharacter = getFirstCharacter(namePart);
                    if (firstCharacter != null) {
                        builder.append(firstCharacter);
                    }
                }
                reverseArray(splittedName);
                for (String namePart : splittedName) {
                    String firstCharacter = getFirstCharacter(namePart);
                    if (firstCharacter != null) {
                        character = new InitialChar(firstCharacter);
                        if (previousCharacter != null) {
                            character.getNextChars().add(previousCharacter);
                        }
                        previousCharacter = character;
                    }
                }
            }
            String intials = builder.toString().toLowerCase();
            if (contactsMap.containsKey(intials)) {
                contactsMap.get(intials).add(person);
            } else {
                List<Contact> list = new ArrayList<>();
                list.add(person);
                contactsMap.put(intials, list);
            }
            initialsMap.addInitials(character);
        }
    }

    /*public InitialChar getAvailableNextCharacters(String initial) {
        return initialsMap.getPossibleInitialsForCharacter(initial);
    }*/

    public List<InitialChar> getNextCharacters(String initial) {
        if (initial != null && initial.length() > 0) {
            InitialChar values = initialsMap
                    .getPossibleInitialsForCharacter(Character.toString(initial
                            .charAt(0)));
            InitialChar previous = values;
            if (initial.length() > 1) {
                for (int i = 1; i < initial.length(); i++) {
                    char character = initial.charAt(i);
                    previous = previous.getNextIntialChar(Character
                            .toString(character));
                }
            }
            if (previous != null) {
                return previous.getNextChars();
            }
        }
        return new ArrayList<>();
    }

    public List<Contact> getContacts() {
        return getContactsByInitials("");
    }

    public Contact getContact(String id) {
        for (Contact contact : contacts) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getContactsByInitials(String initials) {
        initials = initials.toLowerCase();
        List<Contact> personsList = new ArrayList<Contact>();
        for (Map.Entry<String, List<Contact>> persons : contactsMap.entrySet()) {
            if (persons.getKey().toLowerCase().startsWith(initials)) {
                personsList.addAll(persons.getValue());
            }
        }
        ContactComparator comparator = new ContactComparator(direction);
        Collections.sort(personsList, comparator);
        return personsList;
    }

    public List<InitialChar> getAvailableInitials() {
        return initialsMap.getInitials();
    }

    private void reverseArray(String[] array) {
        int left = 0; // index of leftmost element
        int right = array.length - 1; // index of rightmost element

        while (left < right) {
            // exchange the left and right elements
            String temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            // move the bounds toward the center
            left++;
            right--;
        }
    }

    private String getFirstCharacter(String value) {
        if (Utils.isNotBlank(value))  {
            return value.substring(0, 1);
        }
        return null;
    }
}
