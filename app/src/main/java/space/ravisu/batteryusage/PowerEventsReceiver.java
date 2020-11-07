package space.ravisu.batteryusage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PowerEventsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean setting = false;

        SharedPreferences prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE);

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            setting = true;
            Toast.makeText(context, "Plugged in!", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, "Unplugged!", Toast.LENGTH_SHORT).show();
            setting = false;
        }

        prefs.edit()
                .putBoolean("device_plugged_in", setting)
                .apply();
    }
}
