package com.nyit.librarysystem;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nyit.librarysystem.reader.about;

import java.util.Calendar;

public class MainActivity extends Activity {

	Button btnAdmin,btnReader,btnabout,btnclose;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			btnAdmin = (Button) findViewById(R.id.btnAdmin);
		btnReader = (Button) findViewById(R.id.btnReader);
		btnabout = (Button) findViewById(R.id.btnabout);
		btnclose=(Button)findViewById(R.id.btnclose);


		btnAdmin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,AdminLogin.class);
				startActivity(i);
			}
		});

		btnReader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,ReaderLogin.class);
				startActivity(i);
			}
		});
		btnabout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,about.class);
				startActivity(i);
			}
		});

		btnclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					System.exit(0);
				}
		});


	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
