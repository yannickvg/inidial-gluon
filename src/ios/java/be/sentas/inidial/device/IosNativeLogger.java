package be.sentas.inidial.device;

import org.robovm.apple.foundation.Foundation;

public class IosNativeLogger extends NativeLogger {

    @Override
    public void d(String message) {
        Foundation.log(message);
    }
}
