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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;
import com.nyit.librarysystem.admin.AdminAddBookDetails;
import com.nyit.librarysystem.admin.AdminSearchBook;

import java.io.IOException;
import java.util.ArrayList;


public class ReaderSearchbyAuthor extends Activity {
    private DataBaseHelper db;
    Button searchButtonByAuthor;

    TextView info;
    String res;
    AutoCompleteTextView authorTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_searchbookbyauthor);
        info = (TextView) findViewById(R.id.tvinfo2);
        searchButtonByAuthor= (Button) findViewById(R.id.reader_searchbyAuthor2);

        res = "";

        //Find TextView control

        //Find TextView control
        authorTextView= (AutoCompleteTextView) findViewById(R.id.books2);
        //Set the number of characters the user must type before the drop down list is shown



        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        authorTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                //  if(query.length() == 1) {
                ArrayList<String> authors = db.getAuthors(query);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReaderSearchbyAuthor.this, android.R.layout.select_dialog_singlechoice, authors);
                authorTextView.setThreshold(1);
                //Set the adapter
                authorTextView.setAdapter(adapter);
                //  }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchButtonByAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String author = authorTextView.getText().toString().trim();
                int titleid = db.getBookByAuthor(author);
                if(titleid>0) {
                    Intent i = new Intent(ReaderSearchbyAuthor.this, AdminAddBookDetails.class);
                    i.putExtra("titleid", titleid);
                    startActivity(i);
                }
                else {
                    info.setText("book not found");
                }

             //   info.setText(res);

                Toast.makeText(getApplicationContext(), authorTextView.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}






