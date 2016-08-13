package be.sentas.inidial.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yannick on 11/08/16.
 */
public class Contact {

    private String firstName;

    private String lastName;

    private String initials;

    private List<PhoneNumber> numbers;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials = calculateInitials(firstName, lastName);
    }

    private String calculateInitials(String firstName, String lastName) {
        return "";
        /*return Arrays.stream(getFullName().split(" "))
                .map(s -> s.substring(0, 1))
                .collect(Collectors.joining());*/
    }

    public String getFullName() {
        return firstName + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInitials() {
        return initials;
    }

    public List<PhoneNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<PhoneNumber> numbers) {
        this.numbers = numbers;
    }


}
