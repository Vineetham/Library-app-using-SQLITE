package com.nyit.librarysystem.reader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;

import java.io.IOException;

/**
 * Created by Pratik on 5/6/2017.
 */

public class ReaderBookCheckout extends Activity {

    TextView tv;
    EditText ed,ed1;
    Button btn,btn1;
    private Spinner spnId;

    private DataBaseHelper db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_bookcheckout);
        spnId=(Spinner)findViewById(R.id.spnRId);
        tv = (TextView) findViewById(R.id.tvbookcheckout);
        ed= (EditText) findViewById(R.id.edtbookcheckout);
        ed1=(EditText)findViewById(R.id.edtbookdate);
       btn= (Button) findViewById(R.id.btnbookcheckout);
        btn1=(Button)findViewById(R.id.btnbookclear);





        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String  accno=" ",date=" ",readerid=" ";

                String accno=ed.getText().toString();
              //  String readerid = spnId.getSelectedItem().toString();
            String date=ed1.getText().toString();
                String result=db.bookCheckOut(accno,date);
                tv.setText(result);

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ed.setText("");
                ed1.setText("");


            }
        });

    }
}
