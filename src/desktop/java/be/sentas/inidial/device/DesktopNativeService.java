package be.sentas.inidial.device;

/**
 * Created by yannick on 15/08/16.
 */
public class DesktopNativeService implements NativeService {
    @Override
    public void callNumber(String number) {
        System.out.println("Calling number: " + number);
    }
}
