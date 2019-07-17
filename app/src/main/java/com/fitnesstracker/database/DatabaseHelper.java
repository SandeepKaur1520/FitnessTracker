package com.fitnesstracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "Fitness";
    private static final int DB_VERSION = 1;
        public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS `UserInfo` (\n" +
//                "\t`email` VARCHAR(100),\n" +
//                "\t`password` VARCHAR(100),\n" +
//                "\t`First_Name` VARCHAR(100),\n" +
//                "\t`Last_Name` VARCHAR(100),\n" +
//                "\t`Gender` VARCHAR(100),\n" +
//                "\tPRIMARY KEY (`email`)\n" +
//                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS `userinfo` (\n" +
                "  `userID` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `email` varchar(100) NOT NULL UNIQUE,\n" +
                "  `password` varchar(100) NOT NULL,\n" +
                "  `First_Name` varchar(100) ,\n" +
                "  `Last_Name` varchar(100) ,\n" +
                "  `Gender` varchar(100) ,\n" +
                "  `Height` varchar(100) ,\n" +
                "  `Weight` varchar(100) ,\n" +
                "  `DOB` varchar(100) ,\n" +
                "  `Service` varchar(50) ,\n" +
                "  `SubService` varchar(50) \n" +
                ");");

//        SQLiteDatabase myDb = this.getWritableDatabase();
//        mydatabase=db;

//        ContentValues Values = new ContentValues();
//        Values.put("email", "sandeep@gmail.com");
//        Values.put("password", "123");
//        Values.put("First_Name", "Sandeep");
//        Values.put("Last_Name", "Kaur");
//        Values.put("Gender", "Female");
//        long i = myDb.insert("UserInfo",null,Values);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'UserInfo'");
        onCreate(db);
    }
    public SQLiteDatabase getWDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }
    public SQLiteDatabase getRDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }
    public  boolean verifyUser(String email, String password) {

        boolean status =false;
        SQLiteDatabase db = this.getReadableDatabase();
//       SQLiteQueryBuilder

        String x = '"'+email+'"';
        String y = '"'+password+'"';
        String[] selection = {email,password};
        String query ="Select userID,email,password from UserInfo where email = "+x+" and password = "+y+";";
        Log.e("Query out : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        Log.e("Cursor : ",resultSet.toString());
        if (resultSet.moveToFirst()){
            String userID = resultSet.getString(0);
            String Email = resultSet.getString(1);
            String Password = resultSet.getString(2);
            Log.e("Query in : ",query);
            Log.e("userID : ",userID);
            Log.e("Email : ",Email);
            Log.e("Passwaord :",Password);
            status = true;
        }else{
            status = false;
        }

        resultSet.close();
        db.close();
        return  status;
    }

    public boolean createUser(String eMail, String Pass, String firstName, String lastName, String gender) {

        SQLiteDatabase db = this.getWritableDatabase();
        boolean status =false;
        long i=0;
        try {
            ContentValues Values = new ContentValues();
            Values.put("email", eMail);
            Values.put("password", Pass);
            Values.put("First_Name", firstName);
            Values.put("Last_Name", lastName);
            Values.put("Gender", gender);
            i = db.insert("UserInfo", null, Values);
            Log.e("Insert ", "" + i);
        }catch (Exception e){
            throw e;

        }
        if(i==-1)
        {
            status=false;
        }else
        {
            status=true;
        }
        db.close();
        return status;
    }


    public void updateHealthInfo(String email, double height, double weight, String date) {
    }
}