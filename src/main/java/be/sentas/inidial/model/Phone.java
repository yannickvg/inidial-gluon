package be.sentas.inidial.model;

/**
 * Created by yannick on 11/08/16.
 */
public class Phone {

    private String contactId;

    private String id;

    private String number;

    private PhoneType type;

    public Phone(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public PhoneType getType() {
        return type;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

