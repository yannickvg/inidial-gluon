package be.sentas.inidial.device;

import android.util.Log;

public class AndroidNativeLogger extends NativeLogger {

    private static final String TAG = "InidialGluon";

    @Override
    public void d(String message) {
        Log.d(TAG, message);
    }
}
