package be.sentas.inidial.device;

import android.util.Log;

/**
 * Created by yannick on 15/08/16.
 */
public class AndroidNativeLogger extends NativeLogger {

    private static final String TAG = "InidialGluon";

    @Override
    public void d(String message) {
        Log.d(TAG, message);
    }
}
