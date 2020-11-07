package space.ravisu.batteryusage;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous
 * task requests in a service on a separate handler thread.
 */
public class TestForegroundService extends IntentService {

    /*Overriding Doze Mode via foreground service
    * Noninterruptible Tasks
    * Use a foreground service
    * requires persistent notification
    * */

    private static final String ACTION_PLAY_SONG = "action.PLAY_SONG";

    private static final String EXTRA_PATH = "extra.PATH";

    public TestForegroundService() {
        super("TestForegroundService");
    }

    /**
     * Starts this service to perform action PLAY_SONG with
     * the given parameters. If the service is already performing
     * a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPlayMusic(Context context, String path) {
        Intent intent = new Intent(context, TestForegroundService.class);
        intent.setAction(ACTION_PLAY_SONG);
        intent.putExtra(EXTRA_PATH, path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY_SONG.equals(action)) {
                Toast.makeText(this,
                        "Playing song...",
                        Toast.LENGTH_SHORT).show();
                final String path = intent.getStringExtra(EXTRA_PATH);
                handleActionPlaySong(path);
            }
        }
    }

    /**
     * Handle ACTION_PLAY_SONG in the provided background
     * thread with the provided parameters.
     */
    private void handleActionPlaySong(String path) {

        Intent notificationIntent = new Intent(this, PlayerDummyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                notificationIntent,
                0);

        Notification notification = new NotificationCompat.Builder(
                this, "test")
                .setContentTitle("Player")
                .setContentText("Open Player")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        // make into a foreground service
        startForeground(1, notification);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stopForeground(true);
        }
    }
}
