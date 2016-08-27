package be.sentas.inidial.device;

/**
 * Created by yannick on 15/08/16.
 */
public class DesktopNativeLogger extends NativeLogger {

    @Override
    public void d(String message) {
        System.out.println(message);
    }
}
