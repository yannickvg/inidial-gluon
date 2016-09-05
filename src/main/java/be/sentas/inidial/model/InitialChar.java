package be.sentas.inidial.model;

import java.util.ArrayList;
import java.util.List;

public class InitialChar {
    private String initial;
    private List<InitialChar> nextChars = new ArrayList<InitialChar>();

    public InitialChar() {
    }

    public InitialChar(String intial) {
        this.initial = intial;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public List<InitialChar> getNextChars() {
        return nextChars;
    }

    public void setNextChars(List<InitialChar> nextChars) {
        this.nextChars = nextChars;
    }

    public InitialChar getNextIntialChar(String character) {
        for (InitialChar initialChar : nextChars) {
            if(initialChar.getInitial().equalsIgnoreCase(character)) {
                return initialChar;
            }
        }
        return null;
    }

}
