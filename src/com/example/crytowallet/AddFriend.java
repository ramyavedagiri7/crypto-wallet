package com.example.crytowallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriend extends Activity {
	private static final String TAG = "AddFriend";
	public static final int MENU_HOME = Menu.FIRST + 1;
	public static final int MENU_LOGOUT = Menu.FIRST + 2;
	public static final int MENU_ABOUT = Menu.FIRST + 3;

	EditText mEdit;
	String alias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, MENU_HOME, 0, "Home");
		menu.add(0, MENU_LOGOUT, 0, "Logout");
		menu.add(0, MENU_ABOUT, 0, "About");
		getMenuInflater().inflate(R.menu.add_friend, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void popUpToast(View v) {
		mEdit = (EditText) findViewById(R.id.editText2);
		alias=mEdit.getText().toString();
		Log.i(TAG,"seeting alias : "+alias);
		Toast.makeText(getApplicationContext(), "Added "+alias+" Successfully!!", Toast.LENGTH_LONG).show();
		/*Toast tt= new Toast(this);
		tt.setText("Added "+alias+" Successfully!!");*/
		Intent intent = new Intent(this,MakeTransfer.class);
		Log.i(TAG,"friend is "+alias);
		intent.putExtra("FRIEND", alias);
		startActivity(intent);
	}
}
