package com.example.crytowallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;
import com.encryption.generator.EncryptionGeneration;

public class LoginPage extends Activity {
	private static final String TAG = "Home";
	public static final int MENU_HOME = Menu.FIRST + 1;
	public static final int MENU_LOGOUT = Menu.FIRST + 2;
	public static final int MENU_ABOUT = Menu.FIRST + 3;
	private DbxAccountManager mDbxAcctMgr;
	EditText uname, pwd, pin;

	private DbxAccountManager mAccountManager;
	private DbxDatastoreManager mDatastoreManager;
	private DbxDatastore datastore;
	private Button mLinkButton;
	EncryptionGeneration eg;

	SessionManagement session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		mAccountManager=null;
		mDatastoreManager=null;
		datastore=null;
		
		session = new SessionManagement(getApplicationContext());
		/*
		 * mAccountManager = DbxAccountManager.getInstance(
		 * getApplicationContext(), "u1us9ym3k9ovp1a", "ihunbc7xdktaxak");
		 * 
		 * // Set up the datastore manager if
		 * (mAccountManager.hasLinkedAccount()) { try { mDatastoreManager=null;
		 * // Use Dropbox datastores // mAccountManager=null; //
		 * mDatastoreManager=null; mDatastoreManager = DbxDatastoreManager
		 * .forAccount(mAccountManager.getLinkedAccount());
		 * 
		 * 
		 * } catch (Exception e) {
		 * System.out.println("Account was unlinked remotely"); } }
		 */
		/*
		 * if (mDatastoreManager == null) { // Account isn't linked yet, use
		 * local datastores mDatastoreManager = DbxDatastoreManager
		 * .localManager(mAccountManager); }
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// setContentView(R.layout.activity_login_page);

		getMenuInflater().inflate(R.menu.login_page, menu);
		menu.add(0, MENU_HOME, 0, "Home");
		menu.add(0, MENU_LOGOUT, 0, "Logout");
		menu.add(0, MENU_ABOUT, 0, "About");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case MENU_LOGOUT:
			Log.i(TAG, "Insided logout");
			try {
				mDatastoreManager.shutDown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mDatastoreManager = null;
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return super.onOptionsItemSelected(item);
	}

	public void goToHome(View v) {

		Log.i(TAG, "Inide goToHome click");
		Intent intent = new Intent(this, Home.class);
		startActivity(intent);

	}

	public void checkAuthentication(View v) {

		Log.i(TAG, "Inide checkAuthentication click");

		uname = (EditText) findViewById(R.id.editText1);
		pwd = (EditText) findViewById(R.id.editText2);
		pin = (EditText) findViewById(R.id.editText3);
		Log.i(TAG, "uname entered is " + uname.getText().toString());
		String pubKey;
		if (uname.getText().toString().isEmpty()
				|| pwd.getText().toString() == ""
				|| pwd.getText().toString() == "") {
			Log.i(TAG, "Inside check fields");
			final AlertDialog alert123 = new AlertDialog.Builder(this).create();
			alert123.setTitle("Login FAailed");
			alert123.setMessage("Some of the fields are empty");
			alert123.setButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					alert123.cancel();

				}
			});
			alert123.show();
		} else {
			eg = new EncryptionGeneration();
			try {
				eg.generateKeys(uname.getText().toString(), pwd.getText()
						.toString(), pin.getText().toString());
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				final AlertDialog alert = new AlertDialog.Builder(
						this).create();
				alert.setTitle("Login Failed");
				alert.setMessage("Username/Password is incorrect");
				alert.setButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {
								alert.cancel();

							}
						});
				alert.show();
			}
			pubKey = eg.getPublicKey();

			// mAccountManager.startLink((Activity) LoginPage.this, 0);

			if (mAccountManager == null) {
				mAccountManager = DbxAccountManager.getInstance(
						getApplicationContext(), "u1us9ym3k9ovp1a",
						"ihunbc7xdktaxak");
			}

			if (mAccountManager.hasLinkedAccount()) {
				try {
					// Use Dropbox datastores
					mDatastoreManager = DbxDatastoreManager
							.forAccount(mAccountManager.getLinkedAccount());
					// Hide link button
					Toast.makeText(getApplicationContext(),
							"Accout already linked!!", Toast.LENGTH_LONG)
							.show();

					// mLinkButton.setVisibility(View.GONE);
				} catch (DbxException.Unauthorized e) {
					System.out.println("Account was unlinked remotely");
				}
			} else {
				mAccountManager.startLink((Activity) LoginPage.this, 0);
			}

			try {

				if (datastore == null) {
					try {
						datastore = mDatastoreManager.openDefaultDatastore();
						datastore.sync();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					}
				}
				DbxTable tasksTbl = datastore.getTable("users");
				Log.i(TAG, "Before getrecord nameis "
						+ uname.getText().toString());
				DbxTable.QueryResult results = tasksTbl.query();
				Log.i(TAG, "restult is !!: " + results);

				try {
					DbxRecord task2 = tasksTbl.get(uname.getText().toString());

					if (task2 != null) {
						Log.i(TAG, "User " + uname.toString() + " exists");
						String gen_pub = task2.getString("public_key");
						Log.i(TAG, "User " + uname.toString() + " exists. Pub key from db is :"+gen_pub);
						if (!gen_pub.equals(pubKey)) {
							final AlertDialog alert = new AlertDialog.Builder(
									this).create();
							alert.setTitle("Login Failed");
							alert.setMessage("Username/Password is incorrect");
							alert.setButton("OK",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											alert.cancel();

										}
									});
							alert.show();
						} else {
							// DbxRecord task3 = task3.getString(name);
							session.createLoginSession(uname.getText()
									.toString(), uname.getText().toString()
									+ "@gmail.com");
							Intent intent = new Intent(this, Home.class);
							startActivity(intent);
						}

					} else {
						final AlertDialog alert = new AlertDialog.Builder(this)
								.create();
						alert.setTitle("Login Failed");
						alert.setMessage("Username/Password is incorrect");
						alert.setButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										alert.cancel();

									}
								});
						alert.show();
						Log.i(TAG, "User " + uname + "  DOES NOT exists");

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Log.i(TAG,"User "+uname+" exists");

				/*
				 * DbxFields queryParams = new DbxFields().set("RecordID",
				 * true); DbxTable.QueryResult results =
				 * tasksTbl.query(queryParams); DbxRecord firstResult =
				 * results.iterator().next();
				 * 
				 * String recordid=tasksTbl.get("RecordID").toString();
				 * if(recordid.equals(uname)){
				 * Log.i(TAG,"User "+uname+" exists");
				 * 
				 * } else { Log.i(TAG,"User "+uname+" exists");
				 * 
				 * } Log.i(TAG,"Does ramya exists"); //DbxTable.QueryResult
				 * results = tasksTbl.query(queryParams); //DbxRecord
				 * firstResult = results.iterator().next(); DbxTable.QueryResult
				 * results = tasksTbl.query();
				 * Log.i(TAG,"restult is !!: "+results);
				 * Toast.makeText(getApplicationContext(),
				 * "restult is !!: "+results, Toast.LENGTH_LONG) .show();
				 */

			} catch (DbxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
