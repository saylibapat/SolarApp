package com.meda.remeda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by swarada on 7/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DatabaseName = "Meda.db";
    private static final String TableName = "Credentials";
    private static final String ColumnName = "name";
    private static final String ColumnEmail = "email";
    private static final String ColumnUname = "uname";
    private static final String ColumnPass = "pass";
    private static final String TABLE_CREATE = "create table Credentials (uname text primary key not null,name text not null,email text not null,pass text not null);";
    public SQLiteDatabase db;

    private static final String TableName2 = "Reports";
    private static final String ColumnReportId="ReportId";
    private static final String ColumnUname1="uname1";
    private static final String ColumnModelNo="ModelNo";
    private static final String ColumnModelName="ModelName";
    private static final String ColumnManufacturer="Manufacturer";
    private static final String ColumnLocation="Location";
    private static final String ColumnDate="Date";
    private static final String ColumnImage="Image";
    private static final String ColumnBarcode="Barcode";
    private static final String ColumnLatitude="Latitude";
    private static final String ColumnLongitude="Longitude";

    private static final String TABLE_CREATE2="create table Reports (ReportId text primary key,uname1 text,ModelNo text,ModelName text not null,Manufacturer text not null,Location text,Barcode text not null,Date text,Image blob,Latitude double,Longitude double);";

    public void insertInformation(Report r)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ColumnReportId, r.getReportId());
        values.put(ColumnModelNo, r.getModelNo());
        values.put(ColumnModelName, r.getModelName());
        values.put(ColumnManufacturer, r.getManufacturer());
        values.put(ColumnDate, r.getDate());
        values.put(ColumnLocation, r.getLocation());
        values.put(ColumnBarcode, r.getBarcode());
        values.put(ColumnImage, r.getImage());
        values.put(ColumnUname1, r.getUsername());
        values.put(ColumnLatitude,r.getLatitude());
        values.put(ColumnLongitude,r.getLongitude());

        db.insert(TableName2,null,values);
        db.close();
    }



    public DatabaseHelper(Context context){
        super(context, DatabaseName, null, DATABASE_VERSION);
    }

    public void insertContact(Contacts c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnName, c.getName());
        values.put(ColumnEmail, c.getEmail());
        values.put(ColumnUname, c.getUsername());
        values.put(ColumnPass, c.getPassword());
        db.insert(TableName,null,values);
        db.close();
    }
    //boolean checkUser(String UserID, String password) {

        // array of columns to fetch
        //String[] columns = {
          //      ColumnUname
        //};
        //SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        //String selection = ColumnUname + " = ?" + " AND " + ColumnPass + " = ?";

        // selection arguments
//        String[] selectionArgs = {UserID, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
  /*      Cursor cursor = db.query(TableName, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }*/
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        this.db=db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS " + TableName;
        db.execSQL(query);
        String query1 = "DROP TABLE IF EXISTS " + TableName2;
        db.execSQL(query1);
        this.onCreate(db);
    }
    public boolean unamevalid(String username)
    {
        /*Cursor cursor = null;

        String selectQuery = "SELECT * FROM Credentials WHERE uname =username;";

        cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null && cursor.getCount()<2)
        {
            return true;
        }
        return false;*/

        String[] columns = {
                ColumnUname
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = ColumnUname + " = ?" + " AND " + ColumnPass + " = ?";

        // selection arguments
        String[] selectionArgs = {username};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TableName, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount ==0 ) {
            return true;
        }

        return false;
    }



}
