package com.nyit.librarysystem.reader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.nyit.helper.Book;
import com.nyit.helper.BookAdapter;
import com.nyit.helper.DataBaseHelper;
import com.nyit.librarysystem.R;

import java.io.IOException;
import java.util.ArrayList;
public class ReaderBookResList extends Activity {

    private DataBaseHelper db;
    private ListView listView;
    private TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_resbooklist);
        listView = (ListView) findViewById(R.id.listresbook);
        tx=(TextView)findViewById(R.id.textView1);
        try
        {
            db = new DataBaseHelper(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ArrayList<Book> bookList = new ArrayList<Book>();
        bookList= db.getAllResBookList();
        // Create the adapter to convert the array to views
        BookAdapter adapter = new BookAdapter(this, bookList);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
    }

}
