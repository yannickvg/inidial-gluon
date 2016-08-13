package be.sentas.inidial;

import be.sentas.inidial.model.*;

import java.util.*;

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
        fillCombined(NameDirection.FIRSTLAST);
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

    private Map<String, List<Contact>> personMap;
    private InitialsMap initialsMap;

    private void fillCombined(NameDirection direction) {
        initialsMap = new InitialsMap();
        personMap = new HashMap<>();
        for (Contact person : contacts) {
            StringBuilder builder = new StringBuilder();
            InitialChar character = null;
            InitialChar previousCharacter = null;
            if (isNotBlank(person.getDisplayName(direction))) {
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
            if (personMap.containsKey(intials)) {
                personMap.get(intials).add(person);
            } else {
                List<Contact> list = new ArrayList<>();
                list.add(person);
                personMap.put(intials, list);
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

    public List<Contact> getPersonsByInitials(String initials,
                                              NameDirection direction) {
        initials = initials.toLowerCase();
        List<Contact> personsList = new ArrayList<Contact>();
        for (Map.Entry<String, List<Contact>> persons : personMap.entrySet()) {
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
        if (isNotBlank(value))  {
            return value.substring(0, 1);
        }
        return null;
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().equals("");
    }

}
