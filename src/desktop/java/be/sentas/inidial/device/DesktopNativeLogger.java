package be.sentas.inidial.device;

public class DesktopNativeLogger extends NativeLogger {

    @Override
    public void d(String message) {
        System.out.println(message);
    }
}
