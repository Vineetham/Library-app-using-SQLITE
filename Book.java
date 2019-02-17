package com.nyit.helper;

public class Book {
    private String Title;
    private String BoookId,Titleid,date;
    private int noc,yop,cost,accno;
    private String author;
    private String publisher;

    public String getBoookId() {
        return BoookId;
    }

    public void setBoookId(String boookId) {
        BoookId = boookId;
    }

    public void setTitleid(String titleid) {
        Titleid = titleid;
    }

/*public int getAccno()
{
    return accno;
}
public void setAccno(int accno){this.accno = accno; }*/
    public int getNoc() {
        return noc;
    }

    public void setNoc(int noc) {
        this.noc = noc;
    }

    public int getYop() {
        return yop;
    }

    public void setYop(int yop) {
        this.yop = yop;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getRacno() {
        return racno;
    }

    public void setRacno(String racno) {
        this.racno = racno;
    }

    private String edition;
    private String racno;

    public String getTitle() {
        return Title;
    }
    public void setTitle(String name) {Title = name;}
    public String getBookId() {
        return BoookId;
    }
    public void setBookId(String id) {
        BoookId = id;
    }

    public String getTitleid() {
        return Titleid;
    }

    public void setTitleId(String id1){
        Titleid=id1;
    }

}
