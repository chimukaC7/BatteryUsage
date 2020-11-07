package space.ravisu.batteryusage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class BatteryOkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs = context.getSharedPreferences("default",  Context.MODE_PRIVATE);
        String action = intent.getAction();

        if (TextUtils.isEmpty(action)) return;

        switch(action) {
            case Intent.ACTION_BATTERY_OKAY:
                prefs.edit()
                        .putBoolean("enable_bg_processing", true)
                        .apply();
                break;
            case Intent.ACTION_BATTERY_LOW:
                prefs.edit()
                        .putBoolean("enable_bg_processing", false)
                        .apply();
                break;
        }
    }
}
