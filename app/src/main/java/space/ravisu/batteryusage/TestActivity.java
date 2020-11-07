package space.ravisu.batteryusage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;
import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;

public class TestActivity extends Activity {

    /*
    * While Doze mode benefits users by reducing battery drain on newer Android devices, there are times when the system's behavior actually interferes with your own application's operation.
    * Let's look at some ways to override Doze mode when appropriate.
    * Doze mode ensures that no services are run when the device is idle.
    * Remember that this means your response to an alarm manager callback, or a job service, won't actually run until the system defined maintenance window.
    * In fact, even wait clocks don't survive Doze mode.
    * This is gonna be a problem if, for example, your app plays music from a streaming service.
    * In this situation it's appropriate to override Doze mode, and the way to do so is with an Android foreground service.
    * Let's walk through a quick primer on foreground services.
    * Foreground services are generally used for tasks that are clearly noticeable to the user.
    * This may or may not mean that they are interacting with the app, but they are certainly aware of its active operation, and a great example is a music-streaming app.
    * These types of services are typically not killed under low memory situations, unless the system is in dire need of resources.
    * This relates to the previous point, in that the user is currently actively interested in the ongoing operation. An important requirement of a foreground service, that also related to that first requirement of being noticeable is that every such service must show a notification in the Android system tray. And finally, as we just discussed, foreground services are able to override Doze mode for the duration of their existence.
     * */


    /*Use Doze mode whitelist
    * You might have a unique scenario where your application needs to override doze mode but a foreground service is not useful or appropriate.
    * For instance, you may have an instant messaging app that needs to stay connected to receive incoming messages in real time.
    * In such a situation, you have one other option. You can add your application to a doze mode whitelist.
    * This is a special feature whereby an app can be partially exempt from doze mode constraints.
    * It's not exactly the same as if you were to use a foreground service but it's close.
    * The app can still access networking functionality and use partial wake locks if it's on the whitelist.
    * However, Alarm Manager alarms and Job Scheduler jobs will not wake up the app even if it has been included in the whitelist.
    * */

    /*
    * So how does an app get on this list?
    * There are a few ways. And all of them involve the user in some way.
    * Your first option is to prompt the user to open the battery optimization screen in the Settings app and to manually add the app to the whitelist.
    * This could take the form of a Help screen or a message that educates the user on how to perform this operation.
    * */

    private PowerEventsReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (receiver == null) {
                            receiver = new PowerEventsReceiver();
                            Util.registerPowerEventsReceiver(TestActivity.this, receiver);
                        } else {
                            Util.unregisterPowerEventsReceiver(TestActivity.this, receiver);
                            receiver = null;
                        }
                    }
                }
        );

        dozeSetup();
    }

    private void dozeSetup() {
        Button settings = findViewById(R.id.button_doze_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Requires API 23
                startActivity(new Intent(ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS));
            }
        });

        Button direct = findViewById(R.id.button_doze_direct);
        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).setData(Uri.parse("package:" + getPackageName())));
            }
        });
    }
}
