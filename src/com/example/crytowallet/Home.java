package com.example.crytowallet;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Home extends Activity {
	private static final String TAG = "Home";
	public static final int MENU_HOME = Menu.FIRST + 1;
	public static final int MENU_LOGOUT = Menu.FIRST + 2;
	public static final int MENU_ABOUT = Menu.FIRST + 3;
	SessionManagement session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		session = new SessionManagement(getApplicationContext());
		session.checkLogin();
		setContentView(R.layout.activity_home);
		HashMap<String, String> user = session.getUserDetails();
		TextView tv =(TextView) findViewById(R.id.textView1);
		tv.setText("Welcome "+user.get(SessionManagement.KEY_NAME)+" !");
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		menu.add(0, MENU_HOME, 0, "Home");
		menu.add(0, MENU_LOGOUT, 0, "Logout");
		menu.add(0, MENU_ABOUT, 0, "About");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

	    case MENU_LOGOUT:
	    	Log.i(TAG,"Insided logout");
	    	try {
				session.logoutUser();
				Intent intent = new Intent(this, LoginPage.class);
				startActivity(intent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        break;
	    default:
            return super.onOptionsItemSelected(item);
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	public void goToCheckBalance(View v) {
		Log.i(TAG, "Inide upload click");
		Intent intent = new Intent(this, ViewBalance.class);
		startActivity(intent);

	}
	public void goToMakeTransfer(View v) {
		Log.i(TAG, "Inide goToMakeTransfer click");
		Intent intent = new Intent(this, MakeTransfer.class);
		startActivity(intent);

	}
	public void goToAddFriend(View v) {
		Log.i(TAG, "Inide goToMakeTransfer click");
		Intent intent = new Intent(this, AddFriend.class);
		startActivity(intent);

	}
	public void goToLogin(View v) {
		Log.i(TAG, "Inide goToMakeTransfer click");
		Intent intent = new Intent(this, LoginPage.class);
		startActivity(intent);

	}
}
