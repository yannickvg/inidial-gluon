package be.sentas.inidial;

import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.ContactComparator;
import be.sentas.inidial.model.NameDirection;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactComparatorTest {

    @Test
    public void compareDifferentContacts() {
        Contact c1 = new Contact("John", "Peters");
        Contact c2 = new Contact("Peter", "Johnson");

        assertThat(new ContactComparator(NameDirection.FIRST_LAST).compare(c1, c2)).isLessThan(0);
        assertThat(new ContactComparator(NameDirection.LAST_FIRST).compare(c1, c2)).isGreaterThan(0);
    }


    @Test
    public void compareEqualContactse() {
        Contact c1 = new Contact("John", "Peters");
        Contact c2 = new Contact("John", "Peters");

        assertThat(new ContactComparator(NameDirection.FIRST_LAST).compare(c1, c2)).isEqualTo(0);
        assertThat(new ContactComparator(NameDirection.LAST_FIRST).compare(c1, c2)).isEqualTo(0);
    }
}
