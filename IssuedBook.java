package com.nyit.helper;

import java.util.ArrayList;

public class IssuedBook {
    private int readerId;
    private String readerName;

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public ArrayList<String> getAccnoList() {
        return accnoList;
    }

    public void setAccnoList(ArrayList<String> accnoList) {
        this.accnoList = accnoList;
    }

    private ArrayList<String> accnoList;

}
