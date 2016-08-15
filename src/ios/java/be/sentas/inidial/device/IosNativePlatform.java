package be.sentas.inidial.device;

/**
 * Created by yannick on 15/08/16.
 */
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
