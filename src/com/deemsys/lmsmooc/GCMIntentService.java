package com.deemsys.lmsmooc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {
	public static int i = 0;
	public static int value = 0;
	//private static final String TAG = "GCMIntentService";
	static Context con;
	private Controller aController = null;

	public GCMIntentService() {
		// Call extended class Constructor GCMBaseIntentService
		super(Config.GOOGLE_SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {

		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)
		if (aController == null)
			aController = (Controller) getApplicationContext();
		con = context;

		// aController.displayRegistrationMessageOnScreen(context,
		// "Your device registred with GCM");
		// Log.d("NAME", MainActivity.name);

		aController
				.register(context, registrationId, SplashActivity.deviceIMEI);

		// DBAdapter.addDeviceData(MainActivity.name, MainActivity.email,
		// registrationId, MainActivity.imei);

	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		aController.unregister(context, registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		String message = intent.getExtras().getString("message");

		String[] StringAll = message.split("\\^");
		for (int i = 0; i < StringAll.length; i++) {
			System.out.println(StringAll[i]);
		}
		String title = "";
		String imei = "";
		System.out.println(StringAll);
		int StringLength = StringAll.length;

		if (StringLength > 0) {

			title = StringAll[0];
			imei = StringAll[1];

			message = StringAll[1];

		}

		aController.displayMessageOnScreen(context, title, message);

		generateNotification(context, title, message, imei);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		String message = getString(R.string.gcm_deleted, total);

		String title = "DELETED";

		generateNotification(context, title, message, "");
	}

	@Override
	public void onError(Context context, String errorId) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {

		if (aController == null)
			aController = (Controller) getApplicationContext();

		return super.onRecoverableError(context, errorId);
	}

	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String title,
			String message, String imei) {

		// PackageManager pm = context.getPackageManager();
		// String lastEnabled = getLastEnabled(context); // Getting last enabled
		// // from shared
		// // preference
		//
		// if (TextUtils.isEmpty(lastEnabled)) {
		// lastEnabled = ".SplashActivity";
		// }
		// //lastEnabled="com.deemsys.lmsmooc.SplashActivity";
		// System.out.println("last enabled value" + lastEnabled);
		// System.out.println("i value::" + i);
		// ComponentName componentName = new
		// ComponentName("com.deemsys.lmsmooc",
		// lastEnabled);
		// pm.setComponentEnabledSetting(componentName,
		// PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
		// PackageManager.DONT_KILL_APP);
		//
		// value = i+1;
		// if (value <= 0) {
		// lastEnabled = ".SplashActivity";
		// } else if (value <= 10) {
		// lastEnabled = "com.deemsys.lmsmooc.a" + value;
		// } else {
		// lastEnabled = "com.deemsys.lmsmooc.a10p";
		// }
		// System.out.println("last enabled value" + lastEnabled);
		//
		// componentName = new ComponentName("com.deemsys.lmsmooc",
		// lastEnabled);
		// pm.setComponentEnabledSetting(componentName,
		// PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
		// PackageManager.DONT_KILL_APP);
		// setLastEnabled(lastEnabled,context);
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		System.out.println("in generate notification");
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);

		Intent notificationIntent = new Intent(context, SplashActivity.class);
		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		notificationIntent.putExtra("name", title);
		notificationIntent.putExtra("message", message);
		notificationIntent.putExtra("imei", imei);

		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(i, notification);
		Intent intent1 = new Intent();

		intent1.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",
				"com.deemsys.lmsmooc.SplashActivity");
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true);
		intent1.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE",
				String.valueOf(i + 1));
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
				"com.deemsys.lmsmooc");

		context.sendBroadcast(intent1);

		// if (Badge.isBadgingSupported(context)) {
		// Badge badge = Badge.getBadge(context);
		//
		// // This indicates there is no badge record for your app yet
		// if (badge == null) {
		// System.out.println("badge is null");
		// return;
		// } else {
		// Log.d("Badge", badge.toString());
		// System.out.println("badge is present");
		// }
		// }
		//
		//
		// if (Badge.isBadgingSupported(context)) {
		// Badge badge = new Badge();
		// badge.mPackage = context.getPackageName();
		// badge.mClass = context.getClass().getName();
		// badge.mBadgeCount = 1;
		// badge.save(context);
		// // badge.update(context);
		// System.out.println("badge show");
		//
		// }

		// if (Badge.isBadgingSupported(context)) {
		// for (Badge b : Badge.getAllBadges(context)) {
		// Log.d("Badge", "Badge: " + b.toString());
		// }
		// }
		// String launcherClassName = getLauncherClassName(context);
		// if (launcherClassName == null) {
		// return;
		// }
		Intent intent2 = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
		intent2.putExtra("badge_count", i + 1);
		intent2.putExtra("badge_count_package_name", context.getPackageName());
		intent2.putExtra("badge_count_class_name",
				"com.deemsys.lmsmooc.SplashActivity");
		context.sendBroadcast(intent2);

		i++;
	}

	static void setLastEnabled(String value, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("LastEnabled", value);
		editor.commit();
	}

	static String getLastEnabled(Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		return pref.getString("LastEnabled", "");
	}
}
