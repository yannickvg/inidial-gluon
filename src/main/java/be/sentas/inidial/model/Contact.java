package be.sentas.inidial.model;

import java.util.List;

/**
 * Created by yannick on 11/08/16.
 */
public class Contact {

    private String firstName;

    private String lastName;

    private List<PhoneNumber> numbers;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<PhoneNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<PhoneNumber> numbers) {
        this.numbers = numbers;
    }


    public String getDisplayName(NameDirection direction) {
        if(NameDirection.FIRSTLAST.equals(direction)) {
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

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().equals("");
    }
}
