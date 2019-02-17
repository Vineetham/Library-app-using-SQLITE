package com.nyit.librarysystem.admin;

import java.io.IOException;
import java.util.ArrayList;

import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class AdminFineAverage extends Activity{


	private DataBaseHelper db;
	private AutoCompleteTextView actReaderName;
	private Button btnFineAvg;
	private TextView tvAvgFine;
	private ArrayList<String> allreaderNameList;
	private ArrayAdapter<String> arrayAdapter;
	String name;
	int readerID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//actReaderName = (AutoCompleteTextView) findViewById(R.id.actReaderName);

		try {
			db = new DataBaseHelper(this);
			allreaderNameList = db.getAllReaderList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < allreaderNameList.size(); i++) {
			Log.e("Name", allreaderNameList.get(i));
		}

		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allreaderNameList);

		actReaderName.setAdapter(arrayAdapter);

		actReaderName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 name = arrayAdapter.getItem(position).toString();
				 readerID = db.getReaderID(name);
				hideSoftKeyboard();
			}

			private void hideSoftKeyboard(){
				InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			}
		});


	}
}
