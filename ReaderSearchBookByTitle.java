package com.nyit.librarysystem.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;
import com.nyit.librarysystem.admin.AdminAddBookDetails;

import java.io.IOException;
import java.util.ArrayList;


public class ReaderSearchBookByTitle extends Activity {
    private DataBaseHelper db;
    Button searchButtonByTitle;

    TextView info;
    String res;
    AutoCompleteTextView titleTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_searchbytitle);
        info = (TextView) findViewById(R.id.tvinfo1);
        searchButtonByTitle= (Button) findViewById(R.id.reader_searchbytitle1);

        res = "";

        //Find TextView control
        titleTextView= (AutoCompleteTextView) findViewById(R.id.books1);
        //Set the number of characters the user must type before the drop down list is shown



        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        titleTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                //  if(query.length() == 1) {
                ArrayList<String> titles = db.getBookTitles(query);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReaderSearchBookByTitle.this, android.R.layout.select_dialog_singlechoice, titles);
                titleTextView.setThreshold(1);
                //Set the adapter
                titleTextView.setAdapter(adapter);
                //  }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchButtonByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTextView.getText().toString().trim();
                int titleid = db.getBookByTitle(title);
                if(titleid>0) {

                    Intent i = new Intent(ReaderSearchBookByTitle.this, AdminAddBookDetails.class);
                    i.putExtra("titleid", titleid);
                    startActivity(i);

                }
                else {
                    info.setText("book not found");
                }

              //  info.setText(res);

                Toast.makeText(getApplicationContext(), titleTextView.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}






