package com.nyit.librarysystem.admin;

import java.io.IOException;

import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AdminAddUser extends Activity{

	private EditText edtName,edtemailid,edtZip,edtPhone;
	private Spinner spState;
	private Button btnAddUser,btn;
	private DataBaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_adduser);
		
		try {
			db = new DataBaseHelper(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initUI();
		
		btnAddUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = edtName.getText().toString();
				//String address = edtAddress.getText().toString()+" "+spState.getSelectedItem()+" "+edtZip.getText().toString();
				String emailid=edtemailid.getText().toString();
			//	String State=spState.getSelectedItem().toString();
			//	String Zip=edtZip.getText().toString();
				String phone = edtPhone.getText().toString();
				if(TextUtils.isEmpty(name)||TextUtils.isEmpty(emailid)||TextUtils.isEmpty(phone))
				{
					Toast.makeText(getApplicationContext(),"You have not entered anything",Toast.LENGTH_LONG).show();
					return;
				}
				Long insID = db.addUser(name,emailid,phone);
				
				if (insID>0) {
//					Toast.makeText(getApplicationContext(), "User "+insID+" Added", Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();

					return;
				}
				
			}
		});
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				edtName.setText("");
				edtemailid.setText("");
				edtPhone.setText("");
			}
		});

	}
	private void initUI() {
		edtName = (EditText) findViewById(R.id.edtName);
		edtemailid = (EditText) findViewById(R.id.edtemailid);
	//	edtZip = (EditText) findViewById(R.id.edtZip);
		edtPhone = (EditText) findViewById(R.id.edtPhone);
		//spState = (Spinner) findViewById(R.id.spState);
		btnAddUser = (Button) findViewById(R.id.btnAddUser);
		btn=(Button)findViewById(R.id.btn1);

	}
}
