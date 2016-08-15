package be.sentas.inidial.device;

/**
 * Created by yannick on 15/08/16.
 */
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
