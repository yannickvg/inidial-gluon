package be.sentas.inidial.model;

/**
 * Created by yannick on 20/08/16.
 */
public class DialedContact {

    private String contactId;

    private Integer dialCount;

    public DialedContact(String contactId) {
        this.contactId = contactId;
        dialCount = 1;
    }

    public Integer getDialCount() {
        return dialCount;
    }

    public void increaseDialCount() {
        dialCount++;
    }

    public String getContactId() {
        return contactId;
    }
}
