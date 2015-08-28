package com.example.crytowallet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MakeTransfer extends Activity {

	String amount;
	String TAG="Make Transfer";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_transfer);
		Intent intent = getIntent();
		String value = intent.getStringExtra("FRIEND");
		Log.i(TAG,"friend value is "+value);

		Spinner s = (Spinner) findViewById(R.id.spinner1);
		List<String> strings = new ArrayList<String>();
		strings.add(value);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_transfer, menu);
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
	public void goToShowBalance(View v) {
		EditText amt= (EditText) findViewById(R.id.editText1);
		amount=amt.getText().toString();
		Log.i(TAG,"amoutn in show balance is"+amount);
		Intent intent = new Intent(this,ViewBalance.class);
		intent.putExtra("AMOUNT", amount);
		startActivity(intent);
	}
}
