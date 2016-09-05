package be.sentas.inidial.device;

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
