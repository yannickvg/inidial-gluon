package be.sentas.inidial.device;

import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;

/**
 * Created by yannick on 15/08/16.
 */
public class IosNativeService implements NativeService {

    @Override
    public void callNumber(String number) {
        String phoneNumberUrl = "tel:" + number;
        UIApplication.getSharedApplication().openURL(new NSURL(phoneNumberUrl));
    }
}
