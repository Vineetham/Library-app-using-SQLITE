package com.nyit.librarysystem.reader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nyit.helper.Book;
import com.nyit.helper.BookAdapter;
import com.nyit.helper.BookIssuedAdapter;
import com.nyit.helper.DataBaseHelper;
import com.nyit.helper.IssuedBook;
import com.nyit.librarysystem.R;

import java.io.IOException;
import java.util.ArrayList;

public class IssuedBookDetails extends Activity {

    private DataBaseHelper db;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issuedbookdetails);

        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.listBook1);

        ArrayList<IssuedBook> bookList = new ArrayList<IssuedBook>();
        bookList= db.getIssuedBookDetails();
        // Create the adapter to convert the array to views
        BookIssuedAdapter adapter = new BookIssuedAdapter(this, bookList);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);





    }
}