package be.sentas.inidial.model;

import be.sentas.inidial.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 11/08/16.
 */
public class Contact {

    private String id;

    private String firstName;

    private String lastName;

    private List<Phone> numbers = new ArrayList<>();

    public Contact() {
    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Phone> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Phone> numbers) {
        this.numbers = numbers;
    }


    public String getDisplayName(NameDirection direction) {
        if(NameDirection.FIRST_LAST.equals(direction)) {
            StringBuilder sb = new StringBuilder();
            sb.append(isNotBlank(firstName) ? firstName : "");
            sb.append(isNotBlank(lastName) ? " " + lastName : "");
            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(isNotBlank(lastName) ? lastName : "");
            sb.append(isNotBlank(firstName) ? " " + firstName : "");
            return sb.toString();
        }
    }

    public boolean hasName() {
        return Utils.isNotBlank(getDisplayName(NameDirection.FIRST_LAST));
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().equals("");
    }

    public boolean hasOnlyOneNumber() {
        return getNumbers().size() == 1;
    }
}
