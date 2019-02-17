package com.nyit.librarysystem.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nyit.helper.Book;
import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;

import java.io.IOException;
import java.util.ArrayList;

public class AdminAddBookDetails extends Activity{
    private LinearLayout llRadio, llExisting, llnewBook, llCommon;
    private TextView accno1,edtTitleid1, edtTitle1, edtAuthor1,  edtNoc1, edtCost1, edtYop1,spPublisher1,edition1,rackno1,edtDate1;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_addbookdetails);
        Intent intent = getIntent();
        int titleid = intent.getIntExtra("titleid",-1);
       // int accno = intent.getIntExtra("accno",-1);

        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Book book = db.getAllBookDetails(titleid);

        llCommon = (LinearLayout) findViewById(R.id.llCommon);
        llnewBook = (LinearLayout) findViewById(R.id.llnewBook);
        edition1 = (TextView) findViewById(R.id.edition1);
        rackno1 = (TextView) findViewById(R.id.rackno1);
        spPublisher1 = (TextView) findViewById(R.id.spPublisher1);
        edtDate1=(TextView)findViewById(R.id.edtDate1);

       edtTitleid1= (TextView) findViewById(R.id.edtTitleid1);
        edtAuthor1 = (TextView) findViewById(R.id.edtAuthor1);
        edtTitle1 = (TextView) findViewById(R.id.edtTitle1);
        edtCost1 = (TextView) findViewById(R.id.edtCost1);
        edtYop1 = (TextView) findViewById(R.id.edtYop1);
    //    edtDate1 = (TextView) findViewById(R.id.edtDate1);
        edtNoc1 = (TextView) findViewById(R.id.edtNoc1);

       edtTitleid1.setText(""+book.getTitleid());
        edtAuthor1.setText(book.getAuthor());
        edtTitle1.setText(book.getTitle());
        edition1.setText(book.getEdition());
        edtCost1.setText(""+book.getCost());
        edtDate1.setText(""+book.getDate());
        edtNoc1.setText(""+book.getNoc());
        edtYop1.setText(""+book.getYop());
        rackno1.setText(""+book.getRacno());
        spPublisher1.setText(book.getPublisher());

    }


}
