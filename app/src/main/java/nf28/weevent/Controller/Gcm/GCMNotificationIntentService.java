package nf28.weevent.Controller.Gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nf28.weevent.Controller.LoginActivity;
import nf28.weevent.Controller.MainActivity;
import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				for (int i = 0; i < 3; i++) {
					Log.i(TAG,
							"Working... ");
					/*try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}*/
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

				sendNotification(""+ extras.get("m"));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
        if (msg!=null){
            if (msg.contains("invite")){
                Log.d(TAG, "Preparing to send notification...: " + msg);
                mNotificationManager = (NotificationManager) this
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, LoginActivity.class), 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        this).setSmallIcon(R.drawable.gcm_cloud)
                        .setContentTitle("WeEvent")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setContentText(msg);

                mBuilder.setContentIntent(contentIntent);
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                Log.d(TAG, "Notification ok");

				sendMessage("updateEvents");
            }
            else if (msg.contains("id=")) {
                // enregistrement du nouveau etat chat
                Pattern pattern = Pattern.compile("id=(.*)");
                Matcher matcher = pattern.matcher(msg);
                if (matcher.find()) {
					String id = matcher.group(1);
                    SharedPreferences sharedPref = getSharedPreferences("global", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(id, "newMessage");
                    editor.commit();
                    sendMessage("updateChat");

					//Notif graphique d'un nouveau message
					mNotificationManager = (NotificationManager) this
							.getSystemService(Context.NOTIFICATION_SERVICE);

					PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
							new Intent(this, LoginActivity.class), 0);

					Event evt = DataManager.getInstance().getEvent(id);

					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
							this).setSmallIcon(R.drawable.gcm_cloud)
							.setContentTitle("WeEvent")
							.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
							.setDefaults(Notification.DEFAULT_SOUND)
							.setAutoCancel(true)
							.setContentText("New message in \"" + evt.getNom() + "\"");

					mBuilder.setContentIntent(contentIntent);
					mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
					Log.d(TAG, "Notification ok");
                }
            }
        }
	}

    private void sendMessage(String signalName)
    {
        Intent intent = new Intent(signalName);
        //intent.putExtra("update",  "chat");
        sendBroadcast(intent);
    }
}
