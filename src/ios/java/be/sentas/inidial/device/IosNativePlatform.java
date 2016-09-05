package be.sentas.inidial.device;

public class IosNativePlatform extends NativePlatform {

    private IosNativeService nativeService;

    @Override
    public NativeService getNativeService() {
        if (nativeService == null) {
            nativeService = new IosNativeService();
        }
        return nativeService;
    }

}
