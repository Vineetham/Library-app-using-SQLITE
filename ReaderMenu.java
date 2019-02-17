package com.nyit.librarysystem.reader;

import com.nyit.librarysystem.R;
import com.nyit.librarysystem.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReaderMenu extends Activity implements OnClickListener{

	private Button btn1,btn2,btn3,btn4,btn5,btn6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_menu);
		setTitle("Faculty Menu");

		/*ReaderLogin rl=new ReaderLogin();
		int x=rl.getReaderid();
		System.out.println("sffsfsfsfsnbfsbnfsbfsbfjsbfjsbfsjbsbgjsbgjbbgjbg"+x);*/

		initUI();
		setListener();
	}

	private void setListener() {
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);


	}

	private void initUI() {
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);
		btn6 = (Button) findViewById(R.id.button6);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.button1:
				Intent i1 = new Intent(ReaderMenu.this,ReaderSearchBook.class);
				startActivity(i1);
				break;
			case R.id.button2:
				//book checkout
				Intent i2 = new Intent(ReaderMenu.this,ReaderBookCheckout.class);
				startActivity(i2);
				break;
			case R.id.button3:
				Intent i3 = new Intent(ReaderMenu.this,ReaderBookReturn.class);
				startActivity(i3);
				break;


			case R.id.button4:
				Intent i4 = new Intent(ReaderMenu.this,ReaderBookandBookId.class);
				startActivity(i4);
				break;
			case R.id.button5:
				//finish();
				Intent i5=new Intent(ReaderMenu.this, IssuedBookDetails.class);
				startActivity(i5);
				break;
			case R.id.button6:
				finish();
				Intent i6=new Intent(ReaderMenu.this, MainActivity.class);
				startActivity(i6);
			default:
				break;
		}
	}
}
