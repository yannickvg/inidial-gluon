package be.sentas.inidial.model;

/**
 * Created by yannick on 11/08/16.
 */
public class PhoneNumber {

    private String number;

    private Type type;

    public PhoneNumber(String number, Type type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }
}

enum Type {
    HOME, WORK, MOBILE
}
