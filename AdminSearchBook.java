package com.nyit.librarysystem.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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

import java.io.IOException;
import java.util.ArrayList;


public class AdminSearchBook extends Activity
{
    private DataBaseHelper db;
    Button searchButtonByTitle, searchButtonByAuthor;
    TextView info;
    int titleid;
    AutoCompleteTextView titleTextView,authorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_searchbook);

        info = (TextView) findViewById(R.id.tvinfo);
        searchButtonByAuthor= (Button) findViewById(R.id.btnSearchByAuthor);
        searchButtonByTitle= (Button) findViewById(R.id.btnSearchByTitle);
       // searchButtonByAccno= (Button) findViewById(R.id.btnSearchByAccno);


        //Find TextView control
         titleTextView= (AutoCompleteTextView) findViewById(R.id.books);
        authorTextView= (AutoCompleteTextView) findViewById(R.id.authors);
        //accnoTextView = (EditText)findViewById(R.id.accno);
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminSearchBook.this, android.R.layout.select_dialog_singlechoice, titles);
                    titleTextView.setThreshold(1);
                    //Set the adapter
                    titleTextView.setAdapter(adapter);
              //  }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        authorTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                //  if(query.length() == 1) {
                ArrayList<String> authors = db.getAuthors(query);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminSearchBook.this, android.R.layout.select_dialog_item, authors);
                authorTextView.setThreshold(1);
                //Set the adapter
                authorTextView.setAdapter(adapter);
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
                titleid = db.getBookByTitle(title);

                if(titleid>0) {

                    Intent i = new Intent(AdminSearchBook.this, AdminAddBookDetails.class);
                    i.putExtra("titleid", titleid);
                    //i.putExtra("Position", position);
                    startActivity(i);
                  /* ArrayList<Integer> accno = db.getBookAccNo(titleid);
                   for(int i = 0 ;i<accno.size();i++)
                    info.setText(accno.get(i)+" ");
                    *//*Intent i = new Intent(AdminSearchBook.this, AdminAddBookDetails.class);
                    i.putExtra("titleid",titleid);
                    //i.putExtra("Position", position);
                    startActivity(i);*/
                }
                else {
                      info.setText("book not found");
                }
                Toast.makeText(getApplicationContext(), titleTextView.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
        searchButtonByAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String author=authorTextView.getText().toString().trim();

               titleid =db.getBookByAuthor(author);


                if(titleid>0) {
                    Intent i = new Intent(AdminSearchBook.this, AdminAddBookDetails.class);
                    i.putExtra("titleid", titleid);
                    //i.putExtra("Position", position);
                    startActivity(i);
                   /* ArrayList<Integer> accno = db.getBookAccNo(titleid);
                    for(int i = 0 ;i<accno.size();i++)
                        info.setText(accno.get(i)+" ");
                    *//*Intent i = new Intent(AdminSearchBook.this, AdminAddBookDetails.class);
                    i.putExtra("titleid",titleid);
                    //i.putExtra("Position", position);
                    startActivity(i);*/
                }
                else {
                    info.setText("book not found");
                }

                Toast.makeText(getApplicationContext(), authorTextView.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

       /*searchButtonByAccno.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String accno=accnoTextView.getText().toString().trim();
               if(!TextUtils.isEmpty(accno)) {
                   titleid = db.getBookByAccno(Integer.parseInt(accno));
                   if(titleid > 0){
                       Intent i = new Intent(AdminSearchBook.this, AdminAddBookDetails.class);
                       i.putExtra("titleid", titleid);
                       i.putExtra("accno",Integer.parseInt(accno));
                       //i.putExtra("Position", position);
                       startActivity(i);
                   }else{
                       info.setText("book not found");
                   }
               }else{
                   //TODO make a toast for empty accno
               }
           }
       });*/






    }
}
