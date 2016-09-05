package be.sentas.inidial.device;

public class AndroidNativePlatform extends NativePlatform {

    private AndroidNativeService nativeService;

    @Override
    public NativeService getNativeService() {
        if (nativeService == null) {
            nativeService = new AndroidNativeService();
        }
        return nativeService;
    }

}
