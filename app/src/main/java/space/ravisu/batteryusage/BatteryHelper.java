package space.ravisu.batteryusage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryHelper {
    public static class BatteryState {
        public boolean isCharging;
        public boolean usbCharging;
        public boolean acOutletCharging;
        public int percent;

        public BatteryState(boolean isCharging, boolean usbCharging, boolean acOutletCharging, int percent) {
            this.isCharging = isCharging;
            this.usbCharging = usbCharging;
            this.acOutletCharging = acOutletCharging;
            this.percent = percent;
        }
    }

    public static BatteryState getCurrentBatteryState(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);

        // Get charging
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        // Get charger type
        int chargingType = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargingType == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargingType == BatteryManager.BATTERY_PLUGGED_AC;

        // Get battery level
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int percent = 0;
        if (level != -1 && scale != -1) {
            percent = level / scale;
        }

        return new BatteryState(isCharging, usbCharge, acCharge, percent);
    }
}
