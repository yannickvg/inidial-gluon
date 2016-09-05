package be.sentas.inidial.device;

import com.gluonhq.charm.down.common.JavaFXPlatform;

public class NativePlatformFactory {

    public static NativePlatform getPlatform() {
        try {
            return (NativePlatform) Class.forName(getPlatformClassName()).newInstance();
        } catch (Throwable ex) {
            return null;
        }

    }

    private static String getPlatformClassName() {
        switch (JavaFXPlatform.getCurrent()) {
            case ANDROID: return "be.sentas.inidial.device.AndroidNativePlatform";
            case IOS: return "be.sentas.inidial.device.IosNativePlatform";
            default : return "be.sentas.inidial.device.DesktopNativePlatform";
        }
    }
}
