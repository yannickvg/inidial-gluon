package be.sentas.inidial.model;

import java.util.Comparator;

/**
 * Created by yannick on 14/08/16.
 */
public class ContactComparator implements Comparator<Contact> {

    private NameDirection direction;

    public ContactComparator(NameDirection direction) {
        super();
        this.direction = direction;
    }

    @Override
    public int compare(Contact lhs, Contact rhs) {
        return lhs.getDisplayName(direction).compareTo(rhs.getDisplayName(direction));
    }

}
