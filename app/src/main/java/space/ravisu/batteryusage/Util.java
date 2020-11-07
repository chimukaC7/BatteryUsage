package space.ravisu.batteryusage;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

public class Util {

    public static void registerPowerEventsReceiver(Context context, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        context.getApplicationContext().registerReceiver(receiver, filter);
    }

    public static void unregisterPowerEventsReceiver(Context context,  BroadcastReceiver receiver) {
        context.getApplicationContext().unregisterReceiver(receiver);
    }

    public void notifyJobComplete(Context context) {
        Intent i = new Intent("space.ravisu.batteryusage.ACTION_JOB_COMPLETED")
                .setComponent(new ComponentName(context.getPackageName(), "space.ravisu.batteryusage.TestReceiver"));
        context.sendBroadcast(i);
    }

    public boolean isBgProcessingEnabled(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE);
        return prefs.getBoolean("enable_bg_processing", false);
    }
}
