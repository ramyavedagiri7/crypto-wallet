package com.example.crytowallet;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewBalance extends Activity {
	private static final String TAG = "ViewBalance";
	float balance=200;
	float amt=balance;
	String bid;
	RESTClient rc;
	//int value=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rc = new RESTClient();
		//bid=null;
		try {
			JSONObject price=rc.getJSONObj("https://api.bitcoinaverage.com/ticker/global/USD/");
			bid=price.get("bid").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_view_balance);
		TextView amt_text = (TextView) findViewById(R.id.textView4);
		Log.i(TAG,"amt text is:"+bid);
		amt_text.setText("Current Bit Coin Value(in Dollars): $"+bid);
		
		Intent intent = getIntent();
		String value = intent.getStringExtra("AMOUNT");
		Log.i(TAG,"value inside on create is "+value);
		if(value==null) {
			value="0";
		}
		TextView tt= (TextView) findViewById(R.id.textView2);
		amt= balance-Integer.parseInt(value);
		tt.setText("B"+amt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_balance, menu);
		
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
	public void goToGetBCPrice(View v) {
		try {
			JSONObject price=rc.getJSONObj("https://api.bitcoinaverage.com/ticker/global/USD/");
			bid=price.get("bid").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView amt_text = (TextView) findViewById(R.id.textView4);
		Log.i(TAG,"amt text is:"+bid);
		amt_text.setText("Current Bit Coin Value(in Dollars): $"+bid);
	}
	public void goToConvert(View v) {
		Log.i(TAG,"Inside goToConvert");
		//Toast tt= new Toast(this);
		Toast.makeText(getApplicationContext(), "$"+amt*(Float.parseFloat(bid)), 5000).show();
		
	//	tt.setText("$75508");
		/*TextView tv = new TextView(this);
		tv.setText("$75508");*/
		
	}
}
