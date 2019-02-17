package com.nyit.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nyit.librarysystem.R;

import java.util.ArrayList;

public class BookIssuedAdapter extends ArrayAdapter<IssuedBook> {
    public BookIssuedAdapter(@NonNull Context context, ArrayList<IssuedBook> books) {
        super(context,0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IssuedBook Issuedbook = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book1, parent, false);
        }
        // Lookup view for data population
        TextView readerid = (TextView) convertView.findViewById(R.id.readerid1);
        TextView readername = (TextView) convertView.findViewById(R.id.readername);

        TextView accno1 = (TextView) convertView.findViewById(R.id.issuedaccno);
        // Populate the data into the template view using the data object
        readerid.setText(""+Issuedbook.getReaderId());
        readername.setText(Issuedbook.getReaderName());
        //  accno1.setText(Issuedbook.getAccnoList());

        String accnoList= "";
        for(int i= 0;i<Issuedbook.getAccnoList().size();i++){
            if(i==0)
                accnoList+=Issuedbook.getAccnoList().get(i);
            else
            accnoList+=" , "+Issuedbook.getAccnoList().get(i);
        }
        accno1.setText(accnoList);
        // Return the completed view to render on screen
        return convertView;
    }
}
