package us.jasonh.spotifystreamer;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by jason on 7/7/15.
 */
public class Util {

    public static void showToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
