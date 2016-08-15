package be.sentas.inidial.device;

/**
 * Created by yannick on 15/08/16.
 */
public class DesktopNativePlatform extends NativePlatform {

    private DesktopNativeService desktopNativeService;

    @Override
    public NativeService getNativeService() {
        if (desktopNativeService == null) {
            desktopNativeService = new DesktopNativeService();
        }
        return desktopNativeService;
    }

}
