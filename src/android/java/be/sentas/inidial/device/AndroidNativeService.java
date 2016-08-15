package be.sentas.inidial.device;

import android.content.Intent;
import android.net.Uri;
import javafxports.android.FXActivity;

/**
 * Created by yannick on 15/08/16.
 */
public class AndroidNativeService implements NativeService {

    @Override
    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        FXActivity.getInstance().startActivity(intent);
    }
}
