package be.sentas.inidial.device;

import org.robovm.apple.foundation.Foundation;

/**
 * Created by yannick on 15/08/16.
 */
public class IosNativeLogger extends NativeLogger {

    @Override
    public void d(String message) {
        Foundation.log(message);
    }
}
