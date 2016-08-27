package be.sentas.inidial.device;

import com.gluonhq.charm.down.common.JavaFXPlatform;

/**
 * Created by yannick on 15/08/16.
 */
public class Logger {

    private static NativeLogger logger;

    public static void d(String message) {
        getLogger().d(message);
    }

    private static NativeLogger getLogger() {
        try {
            if (logger == null) {
                logger = (NativeLogger) Class.forName(getNativeLoggerClassName()).newInstance();
            }
            return logger;
        } catch (Throwable ex) {
            return null;
        }

    }

    private static String getNativeLoggerClassName() {
        switch (JavaFXPlatform.getCurrent()) {
            case ANDROID: return "be.sentas.inidial.device.AndroidNativeLogger";
            case IOS: return "be.sentas.inidial.device.IosNativeLogger";
            default : return "be.sentas.inidial.device.DesktopNativeLogger";
        }
    }
}
