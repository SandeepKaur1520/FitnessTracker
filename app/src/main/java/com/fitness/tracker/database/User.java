package com.fitness.tracker.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;


public class User {

    public static boolean verifyUser(SQLiteDatabase mydatabase, String email, String password) {
        boolean status =false;
        try {
            String query ="Select 'email','password' from 'UserInfo' where 'email'='"+email+"' and 'password'='"+password+"'";

            Cursor resultSet = mydatabase.rawQuery(query,null);
            resultSet.moveToFirst();
            String eMail = resultSet.getString(0);
            String pass = resultSet.getString(1);
            if(eMail.equals(email)&&pass.equals(password)){
                status = true;
            }

        }catch (Exception E){
            Log.e("Expection",E.toString());
        }

        return  status;
    }

    public static Boolean createUser(SQLiteDatabase mydatabase, String eMail, String Pass, String firstName, String lastName, String gender) {

        boolean status =false;

        String query ="CREATE TABLE IF NOT EXISTS `UserInfo` (\n" +
                "\t`email` VARCHAR(100),\n" +
                "\t`password` VARCHAR(100),\n" +
                "\t`First_Name` VARCHAR(100),\n" +
                "\t`Last_Name` VARCHAR(100),\n" +
                "\t`Gender` VARCHAR(100),\n" +
                "\tPRIMARY KEY (`email`)\n" +
                ");";

        Cursor resultSet = mydatabase.rawQuery(query,null);
        ContentValues Values = null;
        Values.put("email", eMail);
        Values.put("password", Pass);
        Values.put("First_Name", firstName);
        Values.put("Last_Name", lastName);
        Values.put("Gender", gender);
        long i = mydatabase.insert("UserInfo",null,Values);
        Log.e("Insert ",""+i);
        resultSet.moveToFirst();
        return status;
    }
}

