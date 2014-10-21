package com.deemsys.lmsmooc;

import java.io.InputStream;
import java.net.URL;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.text.Html;

public class NewMainActivity extends SherlockFragmentActivity {

	DrawerLayout mDrawerLayout;
	TextView name;
	ListView mDrawerList;
	LinearLayout mDrawerLinear;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	Bitmap bitmap;
	ImageView profimg;

	Fragment fragment1 = new Fragmetns2();
	Fragment fragment2 = new ProfileFragment();
	Fragment fragment3 = new ChangePasswordFragment();
	Fragment fragment4 = new InboxFragment();
	Fragment fragment5 = new MyCourses();
	Fragment fragment6 = new MyFavoriteCourses();
	Fragment fragment7 = new MyFavoriteCategory();
	Fragment fragment8 = new MyFavoriteAuthor();
	Fragment fragment9 = new BillingFragment();
	Fragment fragment10 = new AboutUs();
	Fragment fragment11 = new HelpFragment();
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	static String sampletestvale = "8";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.drawer_main);
		name = (TextView) findViewById(R.id.name);
		name.setText(Config.firstname);
		mTitle = mDrawerTitle = getTitle();

		getActionBar().setTitle(
				Html.fromHtml("<font color=\"white\">" + mDrawerTitle
						+ "</font>"));
		Intent intent1 = new Intent();

		intent1.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",
				"com.deemsys.lmsmooc.SplashActivity");
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true);
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", false);
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
				"com.deemsys.lmsmooc");

		sendBroadcast(intent1);
		if (ChangePasswordFragment.alertDialog != null) {
			if (ChangePasswordFragment.alertDialog.isShowing()) {
				System.out.println("in new check for dialodg");
				ChangePasswordFragment.alertDialog.hide();
			}
		}
		Intent intent2 = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
		intent2.putExtra("badge_count", "");
		intent2.putExtra("badge_count_package_name", getApplicationContext()
				.getPackageName());
		intent2.putExtra("badge_count_class_name",
				"com.deemsys.lmsmooc.SplashActivity");
		getApplicationContext().sendBroadcast(intent2);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#3399FF")));

		title = new String[] { "Home", "Profile", "Change Password", "Inbox",
				"My Courses", "My Favorites", "My Categories", "My Authors",
				"My Billing", "About Us", "Help", "Logout" };

		subtitle = new String[] { "Subtitle Fragment 1", "Subtitle Fragment 2" };

		icon = new int[] { R.drawable.home, R.drawable.profile,
				R.drawable.changepassword, R.drawable.inbox,
				R.drawable.courses, R.drawable.favorites, R.drawable.category,
				R.drawable.author, R.drawable.billing, R.drawable.aboutm,
				R.drawable.help, R.drawable.logout, R.drawable.ic_launcher };

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLinear = (LinearLayout) findViewById(R.id.left_drawer);
		mDrawerList = (ListView) findViewById(R.id.listview_drawer);

		// profimg = (ImageView) findViewById(R.id.ivwLogo);
		profimg = (ImageView) findViewById(R.id.ivwLogo);

		mMenuAdapter = new MenuListAdapter(getApplicationContext(), title,
				subtitle, icon);

		mDrawerList.setAdapter(mMenuAdapter);
		new LoadImage().execute(LoginActivity.avatar_url + "UserImages/"
				+ Config.avatar);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.drawer, R.string.app_name, R.string.app_name) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {

				getActionBar().setTitle(
						Html.fromHtml("<font color=\"white\">" + mDrawerTitle
								+ "</font>"));

				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerLinear)) {
				mDrawerLayout.closeDrawer(mDrawerLinear);
			} else {
				mDrawerLayout.openDrawer(mDrawerLinear);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		switch (position) {
		case 0:
			ft.replace(R.id.content_frame, new Fragmetns2());
			break;
		case 1:

			ft.replace(R.id.content_frame, fragment2);
			break;
		case 2:
			ft.replace(R.id.content_frame, fragment3);
			break;
		case 3:
			ft.replace(R.id.content_frame, fragment4);
			break;
		case 4:
			ft.replace(R.id.content_frame, fragment5);
			break;
		case 5:
			ft.replace(R.id.content_frame, fragment6);
			break;
		case 6:
			ft.replace(R.id.content_frame, fragment7);
			break;
		case 7:
			ft.replace(R.id.content_frame, fragment8);
			break;
		case 8:
			ft.replace(R.id.content_frame, fragment9);
			break;
		case 9:
			ft.replace(R.id.content_frame, fragment10);
			break;
		case 10:
			ft.replace(R.id.content_frame, fragment11);
			break;
		default:
			AboutTweet.forcheck = 0;
			SharedPreferences settings = getApplicationContext()
					.getSharedPreferences("MyPref",
							getApplicationContext().MODE_PRIVATE);
			settings.edit().clear().commit();
			Intent intentSignUP = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intentSignUP);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		if (position == 0) {
			setTitle(Html.fromHtml("<font color=\"white\">" + "Courses"
					+ "</font>"));
		} else {
			setTitle(Html.fromHtml("<font color=\"white\">" + title[position]
					+ "</font>"));

		}
		mDrawerLayout.closeDrawer(mDrawerLinear);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {

	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(
						args[0]).getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {
			if (image != null) {
				// Bitmap newd=getRoundedShape(image);
				// profimg.setImageBitmap(newd);
				profimg.setImageBitmap(image);

			} else {

			}
		}
	}

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		int targetWidth = 170;
		int targetHeight = 140;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				Path.Direction.CCW);

		canvas.clipPath(path);

		Matrix mat = new Matrix();
		mat.postRotate(-90);
		Bitmap bmpRotate = Bitmap.createBitmap(scaleBitmapImage, 0, 0,
				scaleBitmapImage.getWidth(), scaleBitmapImage.getHeight(), mat,
				true);
		// Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(bmpRotate, new Rect(0, 0, bmpRotate.getWidth(),
				bmpRotate.getHeight()), new Rect(0, 0, targetWidth,
				targetHeight), null);
		return targetBitmap;
	}
	@Override
	public void onDestroy()
	{
		System.out.println("in destroy");
	super.onDestroy();
	}
}