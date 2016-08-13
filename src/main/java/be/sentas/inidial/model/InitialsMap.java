package be.sentas.inidial.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 14/08/16.
 */
public class InitialsMap {
    private List<InitialChar> initials = new ArrayList<InitialChar>();

    public InitialsMap() {
    }

    public void addInitials(InitialChar initial) {
        addInitials(initial, initials);
    }

    public InitialChar getPossibleInitialsForCharacter(String initial) {
        return getInitial(initial, initials);
    }

    private void addInitials(InitialChar initial, List<InitialChar> initials ) {
        InitialChar existing = getInitial(initial.getInitial(), initials);
        if(existing != null) {
            for (InitialChar nextInitialChar : initial.getNextChars()) {
                addInitials(nextInitialChar, existing.getNextChars());
            }
        } else {
            initials.add(initial);
        }
    }

    private InitialChar getInitial(String initial, List<InitialChar> initials) {
        for (InitialChar initialChar : initials) {
            if(initialChar.getInitial().equalsIgnoreCase(initial)) {
                return initialChar;
            }
        }
        return null;
    }

    public List<InitialChar> getInitials() {
        return initials;
    }
}
