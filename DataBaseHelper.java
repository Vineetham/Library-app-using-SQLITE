package com.nyit.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nyit.librarysystem.ReaderLogin;
import com.nyit.librarysystem.reader.IssuedBookDetails;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DataBaseHelper";

	private Context mycontext;
	private String DB_PATH;
	ReaderLogin readerLogin=new ReaderLogin();


	private static String DB_NAME = "my_db.db";
	//private static String DB_NAME = "lib_db.sqbpro";
	public SQLiteDatabase myDataBase;

	public DataBaseHelper(Context context) throws IOException {
		super(context,DB_NAME,null,1);

		if (android.os.Build.VERSION.SDK_INT >= 17)
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		else
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";


		this.mycontext=context;
		boolean dbexist = checkdatabase();
		if (dbexist) {
			opendatabase();
		} else {
			System.out.println("Database doesn't exist");
			createdatabase();
		}
	}

	public void createdatabase() throws IOException {
		boolean dbexist = checkdatabase();
		if(!dbexist) {
			this.getReadableDatabase();
			try {
				copydatabase();
			} catch(IOException e) {
				e.printStackTrace();
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkdatabase() {

		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			checkdb = dbfile.exists();
		} catch(SQLiteException e) {
			System.out.println("Database doesn't exist");
		}
		return checkdb;
	}

	private void copydatabase() throws IOException {
		//Open your local db as the input stream
		InputStream myinput = mycontext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outfilename = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myoutput = new FileOutputStream(outfilename);

		// transfer byte to inputfile to outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myinput.read(buffer))>0) {
			myoutput.write(buffer,0,length);
		}

		//Close the streams
		myoutput.flush();
		myoutput.close();
		myinput.close();
	}

	public void opendatabase() throws SQLException {
		//Open the database
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
	}

	public synchronized void close() {
		if(myDataBase != null) {
			myDataBase.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}


	public String getPassword(String id){
		String password = "";
		String selectQuery = "SELECT password FROM admin WHERE id = "+"'" +id+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			password = cursor.getString(0);
		}else {
			return "Wrong User ID";
		}
		cursor.close();
		db.close();
		// return user
		return password;
	}

	public boolean getUserID(int id){
		int userid=id;
		boolean res=false;
		String selectQuery = "SELECT readerid FROM reader" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		//cursor.moveToFirst();

		if (cursor.moveToFirst()){
			do{
				//isbnList.add(cursor.getString(cursor.getColumnIndex("isbn")));

				if(cursor.getCount() > 0)
				{
					int temp= cursor.getInt(0);
					if(userid ==temp) {res=true;}
				}

			}while(cursor.moveToNext());

		}

		cursor.close();
		db.close();
		// return user
		return res;
	}



	public ArrayList<String> getAllISBN() {
		ArrayList<String> isbnList = new ArrayList<String>();

		String sql = "SELECT isbn FROM book";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				isbnList.add(cursor.getString(cursor.getColumnIndex("isbn")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return isbnList;
	}

	public ArrayList<String> getAllBranch() {
		ArrayList<String> branchList = new ArrayList<String>();

		String sql = "SELECT name FROM branch";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				branchList.add(cursor.getString(cursor.getColumnIndex("name")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return branchList;
	}

	public ArrayList<String> getAllPositioin() {

		ArrayList<String> positionList = new ArrayList<String>();

		String sql = "SELECT position FROM location";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				positionList.add(cursor.getString(cursor.getColumnIndex("position")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return positionList;
	}

	public String getByBookISBN(String isbn)
	{
		String title="";
		String selectQuery = "SELECT title FROM bookinfo WHERE isbn= '" +isbn+"'" ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			title = cursor.getString(0);
		}else
		{
			title= "book not found";
		}
		cursor.close();
		db.close();
		// return user
		return title;

	}

	public String getBybookId(String bookid)
	{
		String title="";
		String selectQuery = "SELECT title FROM bookcopies, bookinf WHERE bookid = "+"'" +bookid+"' and book.isbn=bookinfo.isbn" ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			title = cursor.getString(0);
		}else
		{
			title= "book not found";
		}
		cursor.close();
		db.close();
		// return user
		return title;

	}
	public ArrayList<String> getBookTitles(String Title)
	{
		ArrayList<String> titles = new ArrayList<String>();
		String selectQuery = "SELECT title FROM bookinf WHERE title LIKE"+"'" +"%"+Title+"%"+"' " ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()){
			do{
				titles.add(cursor.getString(cursor.getColumnIndex("title")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		// return user
		return titles;

	}


	public ArrayList<String> getAuthors(String author)
	{
		ArrayList<String> authors = new ArrayList<String>();
		String selectQuery = "SELECT name FROM author WHERE name LIKE"+"'" +"%"+author+"%"+"' " ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()){
			do{
				authors.add(cursor.getString(cursor.getColumnIndex("name")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		// return user
		return authors;

	}


	public ArrayList<Integer> getBookByAccNo(int titleid)
	{
		ArrayList<Integer> accno = new ArrayList<Integer>();
		String selectQuery = "SELECT accno FROM bookcopies WHERE titleid ="+"'" +titleid+"' " ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()){
			do{
				accno.add(cursor.getInt(cursor.getColumnIndex("accno")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		// return user
		return accno;

	}

	public int getBookByTitle(String Title)
	{
		int titleid = 0;
		String selectQuery = "SELECT titleid FROM bookinf WHERE title = "+"'" +Title+"' " ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			titleid = cursor.getInt(0);
		}else
		{
			titleid= -1;
		}
		cursor.close();
		db.close();
		// return user
		return titleid;

	}

	public int getBookByAccno(int accno)
	{
		int titleid = 0;
		String selectQuery = "SELECT titleid FROM bookcopies WHERE accno = "+"'" +accno+"' " ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			titleid = cursor.getInt(0);
		}else
		{
			titleid= -1;
		}
		cursor.close();
		db.close();
		// return user
		return titleid;

	}

	public int getBookByAuthor(String author)
	{
		int titleid=0;
		String selectQuery = "SELECT titleid FROM bookinf, author WHERE author.authorid=bookinf.authorid AND name = "+"'" +author+"' " ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			titleid = cursor.getInt(0);
		}else
		{
			titleid= -1;
		}
		cursor.close();
		db.close();
		// return user
		return titleid;

	}

	public int getBookID(String selectedISBN) {

		int book_id;
		String selectQuery = "SELECT bookid FROM book WHERE isbn = "+"'" +selectedISBN+"'" ;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			book_id = cursor.getInt(0);
		}else
		{
			return 0;
		}
		cursor.close();
		db.close();
		// return user
		return book_id;
	}


    public String getDate(String date) throws ParseException {
        String res="d";
        int rid=readerLogin.getReaderid();
        SQLiteDatabase db = this.getReadableDatabase();
        float fine;
        String getdate= "SELECT date FROM bookinf WHERE date = "+"'" +date+"' " ;

        Cursor cursor=db.rawQuery(getdate,null);
        cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            do {
                ArrayList<String> ex = new ArrayList<String>();
                ex.add(cursor.getString(cursor.getColumnIndex("date")));

                Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse(ex.get(0));


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return date;
    }

    public Long addBookLocation(int bookID, int branchID, String selectedposition) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("bookid", bookID);
		values.put("branchid", branchID);
		values.put("position", selectedposition);

		long insid = db.insert("location", null, values);
		db.close(); // Closing database connection

		return insid;
	}

	public int getBranchID(String selectedBranch) {
		int branch_id;
		String selectQuery = "SELECT branchid FROM branch WHERE name = "+"'" +selectedBranch+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			branch_id = cursor.getInt(0);
		}else {
			return 0;
		}
		cursor.close();
		db.close();
		// return user
		return branch_id;
	}

	public ArrayList<String> getAllPublisher() {
		ArrayList<String> publisherList = new ArrayList<String>();

		String sql = "SELECT publishername FROM publisher";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				publisherList .add(cursor.getString(cursor.getColumnIndex("publishername")));
				// do what ever you want here
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return publisherList;
	}

	public Long addAuthor(String author) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", author);

		long insid = db.insert("author", null, values);
		db.close(); // Closing database connection

		return insid;
	}

	public int addBook(int bookid,int noc) {


		SQLiteDatabase db = this.getWritableDatabase();
		for (int i=0;i<noc;i++) {
			ContentValues values = new ContentValues();
			values.put("titleid", bookid);
			long insid = db.insert("bookcopies", null, values);
			if(insid<0)
				return -1;


		}
		db.close(); // Closing database connection
		updateBorrowTable(bookid);
		return 2;
	}

	public void updateBorrowTable(int bookid){
		SQLiteDatabase db = this.getWritableDatabase();
		String checkQuery = "SELECT * FROM bookcopies  WHERE titleid= " + bookid;
		Cursor cursor = db.rawQuery(checkQuery, null);
		cursor.moveToFirst();
		if(cursor.moveToFirst()) {
			do {
				String accno = cursor.getString(cursor.getColumnIndex("accno"));
				ContentValues values = new ContentValues();
				values.put("accno", accno);
				values.put("readerid", "");
				values.put("bdate", "");
				values.put("returned", 1);
				long value = db.insert("borrow", null, values);
				//db.close();
			}while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
	}

	public String bookCheckOut(String accno,String date) {
		SQLiteDatabase db = this.getWritableDatabase();
		int temp;
		int readerid = readerLogin.getReaderid();
		String result="";
		String checkQuery = "SELECT returned FROM borrow WHERE accno= " + accno;
		//result="ololo";
		Cursor cursor = db.rawQuery(checkQuery, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			temp = cursor.getInt(0);
			if (temp == 1) {

				String updatequery="UPDATE borrow SET readerid="+"'"+readerid+"'"+", returned=0 ,bdate="+"'"+date+"'"+" WHERE accno="+"'"+accno+"'";
				db.execSQL(updatequery);

				/*ContentValues values = new ContentValues();
				values.put("accno", accno);
				values.put("readerid", readerid);
				values.put("bdate", date);
				values.put("returned", 0);
				long value = db.insert("borrow", null, values);

*/            result = "book successfully Issued";

			} else if (temp == 0) {

				result = "book not available";
			} else {
				result = "book not found";
			}
		}

		cursor.close();
		db.close();

		return result;
	}

	public int getAuthorID(String authorName) {

		int author_id;
		String selectQuery = "SELECT authorid FROM author WHERE name = "+"'" +authorName+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			author_id = cursor.getInt(0);
		}else {
			return 0;
		}
		cursor.close();
		db.close();
		// return user
		return author_id;

	}

	public int getPublisherID(String selectedPublisher) {
		int publisher_id;
		String selectQuery = "SELECT publisherid FROM publisher WHERE publishername = "+"'" +selectedPublisher+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			publisher_id = cursor.getInt(0);
		}else {
			return 0;
		}
		cursor.close();
		db.close();
		// return user
		return publisher_id;
	}

	public String getFine(String bookid) throws Exception {
		float fine;
		String res="kmjjj";
		String selectQuery = "SELECT returned FROM borrow WHERE bookid = '" +bookid+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			int temp=cursor.getInt(0);
			if(temp==0)
			{
				res="Book not returned yet, please return book first";

			}else if(temp==1){
				//res=" dxzexb";
				res=calculatefine(bookid);
			}
		}else {
			res="book not found";

		}


		db.close();
		// return user
		return res;
	}
	public String calculatefine(String bookid)
	{
		String res="d";
		int rid=readerLogin.getReaderid();
		SQLiteDatabase db = this.getReadableDatabase();
		float fine;
		String getdate="SELECT bdate,rdate FROM borrow WHERE bookid ='"+bookid+"' AND readerid ="+rid;
		Cursor cursor=db.rawQuery(getdate,null);
		cursor.moveToFirst();

		if(cursor.getCount()>0)
		{
			try
			{
				ArrayList<String> ex = new ArrayList<String>();
				ex.add(cursor.getString(cursor.getColumnIndex("bdate")));
				ex.add(cursor.getString(cursor.getColumnIndex("rdate")));

				Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse(ex.get(0));
				Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse(ex.get(1));
				long diff = date2.getTime() - date1.getTime();
				diff=diff/(24*60*60*1000);

				//Calendar calender=Calendar.getInstance();

				int days=Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();

				//res= String.valueOf( TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
				//final long days = ChronoUnit.between(date1, date2);
				res=String.valueOf(diff);
				//res=String.valueOf(days);

			}
			catch(Exception e)
			{
				res=e.toString();
				System.out.print(res);
			}
		}
		cursor.close();
		return res;
	}

	public int addBookInfo(int titleid, String title,int authorID,int publisherID,String edition,int price, String yop, int Noc,String racno,String date) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("titleid",titleid);
		values.put("title", title);
		values.put("authorid", authorID);
		values.put("publisherid", publisherID);
		values.put("edition", edition);
		values.put("price",price);
		values.put("yop",yop);
		values.put("noc",Noc);
		values.put("racno",racno);
		values.put("date",date);
		long insid = db.insert("bookinf", null, values);
	//	db.update("bookinfo",values ,"bookinfo.isbn=book.isbn","noc=noc+1");

		db.close(); // Closing database connection


		if(insid > 0) {
			String query = "SELECT titleid from bookinf where date= " + "'" + date + "' ";
			//AND readerid="+"'"+readerID+"'
			SQLiteDatabase db1 = this.getReadableDatabase();
			Cursor cursor = db1.rawQuery(query, null);
			// Move to first row
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				return cursor.getInt(0);
			}
		}
		return -1;
	}

	public String returnBook(String accno)
	{
		String rdate="01-01-2014";
		String res="";

		String checkQuery="SELECT returned from borrow where accno= "+"'" +accno+"' ";
		//AND readerid="+"'"+readerID+"'
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(checkQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			int temp = cursor.getInt(0);
			if(temp==0)
			{
				//res=" 1 temp="+ temp;
				String updatequery="UPDATE borrow SET rdate="+"'"+rdate+"'"+", returned=1 WHERE accno="+"'"+accno+"'";
				db.execSQL(updatequery);
				res="Book successfully returned";
			} else {
				res="Book already returned";
			}
		}else {
			res="Book not found";
		}
		//String updatequery="UPDATE borrow SET rdate="+"'"+rdate+"'"+" WHERE bookid="+"'"+bookid+"'";
		cursor.close();
		//db.execSQL(updatequery);
		db.close();
		//res=res+" updated ";
		return res;
	}

	public Long addUser(String name, String emailid, String phone) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("emailid", emailid);
		values.put("phone", phone);
		long insid = db.insert("reader", null, values);
		db.close(); // Closing database connection

		return insid;
	}

	public ArrayList<Branch> getAllBranchList() {

		ArrayList<Branch> branchList;
		String sql = "SELECT * FROM branch";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		branchList = new ArrayList<Branch>();
		if (cursor.moveToFirst()){
			do{
				Branch branch = new Branch();
				branch.setName(cursor.getString(cursor.getColumnIndex("name")));
				branch.setLocation(cursor.getString(cursor.getColumnIndex("location")));

				branchList.add(branch);
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return branchList;

	}
	public String getReaderName(int readerid) {

		String readerName;
		String selectQuery = "SELECT name FROM reader WHERE readerid = "+"'" +readerid+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			readerName = cursor.getString(0);
		}else {
			return null;
		}
		cursor.close();
		db.close();
		// return user
		return readerName;
	}

	public ArrayList<IssuedBook> getIssuedBookDetails()
	{
		ArrayList<IssuedBook> bookList=new ArrayList<IssuedBook>();
		SQLiteDatabase db=this.getWritableDatabase();
		String checkQuery="SELECT DISTINCT readerid from borrow WHERE returned = 0";
		Cursor cursor=db.rawQuery(checkQuery,null);
		Log.i("==>>","data1 "+DatabaseUtils.dumpCursorToString(cursor));
		cursor.moveToFirst();
		if(cursor.moveToFirst()) {
			do {
				IssuedBook book = new IssuedBook();
				int readerid = cursor.getInt(cursor.getColumnIndex("readerid"));
				book.setReaderId(readerid);
				ArrayList<String> accnoList = new ArrayList<String>();
				SQLiteDatabase db1 = this.getWritableDatabase();
				String checkquery1 = "SELECT  accno FROM borrow WHERE readerid=" + readerid +" AND returned = 0";
				Cursor innerCursor = db1.rawQuery(checkquery1, null);
                Log.i("==>>","data2 "+DatabaseUtils.dumpCursorToString(innerCursor));
				innerCursor.moveToFirst();
				/*if (innerCursor.moveToFirst()) {*/
					do {
						accnoList.add(innerCursor.getString(0));
					} while (innerCursor.moveToNext());
				//}
				book.setAccnoList(accnoList);
				book.setReaderName(getReaderName(readerid));
				book.setReaderId(readerid);
				innerCursor.close();
				db.close();
				bookList.add(book);
			} while (cursor.moveToNext());
		}
			cursor.close();
			db.close();
			return bookList;
		}


	public ArrayList<Book> getAllBookList() {

		ArrayList<Book> bookList;
		String sql = "SELECT accno,bookinf.titleid,title  FROM bookcopies,bookinf Where bookcopies.titleid=bookinf.titleid";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		bookList = new ArrayList<Book>();
		if (cursor.moveToFirst()){
			do{
				Book book = new Book();
				book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				book.setBookId(cursor.getString(cursor.getColumnIndex("accno")));
				book.setTitleId(cursor.getString(cursor.getColumnIndex("titleid")));
				bookList.add(book);
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return bookList;

	}
	public String getAuthorName(int authorid) {
		String sql = "SELECT name  FROM author where authorid= " + "'" + authorid + "' ";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			return cursor.getString(cursor.getColumnIndex("name"));
			}


		cursor.close();
		db.close();
		return "";

	}

	public String getPublisherName(int publisherid) {
		String sql = "SELECT publishername  FROM publisher where publisherid= " + "'" + publisherid + "' ";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			return cursor.getString(cursor.getColumnIndex("publishername"));

		}
		cursor.close();
		db.close();
		return "";

	}
		public Book getAllBookDetails(int titleid) {
		String sql = "SELECT titleid,title,authorid,publisherid,edition,price,noc,yop,racno,date  FROM bookinf where  titleid= " + "'" + titleid + "' ";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			Book book = new Book();
			book.setTitleId(cursor.getString(cursor.getColumnIndex("titleid")));
			book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			book.setEdition(cursor.getString(cursor.getColumnIndex("edition")));
			book.setCost(cursor.getInt(cursor.getColumnIndex("price")));
			book.setNoc(cursor.getInt(cursor.getColumnIndex("noc")));
			book.setYop(cursor.getInt(cursor.getColumnIndex("yop")));
			book.setRacno(cursor.getString(cursor.getColumnIndex("racno")));
			book.setDate(cursor.getString(cursor.getColumnIndex("date")));

			book.setAuthor(getAuthorName(cursor.getInt(cursor.getColumnIndex("authorid"))));
			book.setPublisher(getPublisherName(cursor.getInt(cursor.getColumnIndex("publisherid"))));

			return book;
		}

		cursor.close();
		db.close();
		return null;

	}

	public ArrayList<Book> getAllResBookList()
	{

		int readerid = readerLogin.getReaderid();
		ArrayList<Book> bookList;
		String sql = "SELECT title FROM  reserve,book,bookinfo Where  reserve.bookid=book.bookid AND book.isbn=bookinfo.isbn AND reserve.readerid= "+readerid;

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		bookList = new ArrayList<Book>();
		if (cursor.moveToFirst()){
			do{
				Book book = new Book();
				book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				//book.setBookId(cursor.getString(cursor.getColumnIndex("bookid")));

				bookList.add(book);
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return bookList;

	}

	public ArrayList<FrequentBorrowers> getFrequentBorrowers(int branchID) {

		ArrayList<FrequentBorrowers> frBorrowers;
		//	String sql = "SELECT readerid, count(*) AS count FROM borrow WHERE branchid = "+branchID+" GROUP BY readerid ORDER BY count DESC";
		String sql = "SELECT name, count(*) AS count FROM borrow,reader WHERE branchid = "+branchID+" and borrow.readerid = reader.readerid GROUP BY name ORDER BY count(*) DESC";
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		frBorrowers = new ArrayList<FrequentBorrowers>();
		if (cursor.moveToFirst()){
			do{
				FrequentBorrowers borrower = new FrequentBorrowers();
				borrower.setName(cursor.getString(0));
				borrower.setCount(cursor.getInt(1));

				frBorrowers.add(borrower);

			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return frBorrowers;

	}

	public ArrayList<FrequentBorrowers> getFrequentBooks(int branchID) {

		ArrayList<FrequentBorrowers> frBorrowers;

		String sql = "SELECT title, count(*) AS count FROM borrow,book,bookinfo WHERE branchid = "+branchID+" and book.bookid = borrow.bookid and bookinfo.isbn = book.isbn GROUP BY title ORDER BY count DESC";
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		frBorrowers = new ArrayList<FrequentBorrowers>();
		if (cursor.moveToFirst()){
			do{
				FrequentBorrowers borrower = new FrequentBorrowers();
				borrower.setName(cursor.getString(0));
				borrower.setCount(cursor.getInt(1));

				frBorrowers.add(borrower);
				// do what ever you want here
			}while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return frBorrowers;
	}

	public ArrayList<String> getAllReaderList() {

		ArrayList<String> readerNameList = new ArrayList<String>();
		String selectQuery = "SELECT name FROM reader";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			do{
				readerNameList.add(cursor.getString(0));
			}while(cursor.moveToNext());

		}
		cursor.close();
		db.close();
		// return user
		return readerNameList;
	}

	public int getReaderID(String name) {

		int reader_id;
		String selectQuery = "SELECT readerid FROM reader WHERE name = "+"'" +name+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			reader_id = cursor.getInt(0);
		}else {
			return 0;
		}
		cursor.close();
		db.close();
		// return user
		return reader_id;
	}



	public ArrayList<Float> getReaderFine(int readerID) {

		ArrayList<Float> fineList = new ArrayList<Float>();
		String selectQuery = "SELECT fine FROM borrow WHERE readerid = "+readerID;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			do{
				fineList.add(cursor.getFloat(0));
			}while(cursor.moveToNext());


		}
		cursor.close();
		db.close();
		// return user
		return fineList;
	}


	public int getReaderPassword(String name){
		int readerid = 0;
		String selectQuery = "SELECT readerid FROM reader WHERE name = "+"'" +name+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			readerid = cursor.getInt(0);
		}else {
			return -1;
		}
		cursor.close();
		db.close();
		// return user
		return readerid;
	}

	public int checkReaderLogin(int id) {

		int reader_id;
		String selectQuery = "SELECT readerid FROM reader WHERE readerid = "+id;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){

			reader_id = cursor.getInt(0);
			Log.e("ReaderID",reader_id+"");
		}else {
			return 0;
		}
		cursor.close();
		db.close();
		// return user
		return reader_id;
	}



}
