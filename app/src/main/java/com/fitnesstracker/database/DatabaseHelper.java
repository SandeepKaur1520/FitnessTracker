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
        /**This table stores personal information of user and which kind of service he/she wants to avail*/
        String UserInfo = "CREATE TABLE IF NOT EXISTS `UserInfo` (\n" +
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
                ");";

        /**This table stores initial info about user's current Physique*/
        String GymInfo = "CREATE TABLE IF NOT EXISTS `GymInfo` (\n" +
                "  `SrNo` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `email` varchar(100) NOT NULL ,\n" +
                "  `SkinnyArms` varchar(100),\n"+
                "  `WeakChest` varchar(100),\n"+
                "  `BeerBelly` varchar(100),\n"+
                "  `ThinLegs` varchar(100),\n"+
                "  `BodyActiveness` varchar(50) \n" +
                ");";

        /**This table stores data of daily goals which user wants to achive on daily basis */

        String DailyGoals = "CREATE TABLE IF NOT EXISTS `DailyGoals` (\n" +
                "  `SrNo` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `email` varchar(100) NOT NULL,\n" +
                "  `Steps` varchar(100),\n"+
                "  `Calories` varchar(100),\n"+
                "  `Water` varchar(100),\n"+
                "  `Sleep` varchar(100)\n"+
                ");";

        /**This table stores initial info about user's periods*/
//        String PeriodInfo = "CREATE TABLE IF NOT EXISTS `PeriodInfo` (\n" +
//                "  `SrNo` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "  `email` varchar(100) NOT NULL,\n" +
//                "  `PeriodLength` varchar(100),\n"+
//                "  `CycleLength` varchar(100),\n"+
//                "  `LastStartDate` varchar(100)\n"+
//                ");";



        String PeriodInfo="CREATE TABLE IF NOT EXISTS `Periodinfo` (\n" +
                "  `SrNo` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `email` varchar(100) NOT NULL,\n" +
                "  `PeriodLength` INTERGER,\n"+
                "  `CycleLength` INTEGER,\n"+
                "  `LastStartDate` varchar(100),\n"+
                "  `LastEndDate` varchar(100),\n"+
                "  `PeriodStatus` varchar(100) NOT NULL\n"+//periodstatus = end when end button is press else = start when start is press
                ");";

        db.execSQL(UserInfo);
        db.execSQL(GymInfo);
        db.execSQL(DailyGoals);
        db.execSQL(PeriodInfo);
//        db.execSQL(PeriodHistory);



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
    public boolean verifyUser(String email, String password) {

        boolean status=false;
        SQLiteDatabase db = this.getReadableDatabase();


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


    public boolean updateHealthInfo(String email, double height, double weight, String date) {
            boolean status=false;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Height",String.valueOf(height));
            values.put("Weight",String.valueOf(weight));
            values.put("DOB",String.valueOf(date));
            int i=db.update("UserInfo",values,"email = ?",new String[] {email});
            Log.e("Update :",String.valueOf(i));
//            String x = '"'+email+'"';
//            String query ="Select Height,Weight,DOB from UserInfo where email = "+x+";";
//            Log.e("Query out : ",query);
//            Cursor resultSet = db.rawQuery(query,null);
//            Log.e("Cursor : ",resultSet.toString());
//            if (resultSet.moveToFirst()){
//                String Height = resultSet.getString(0);
//                String Weight = resultSet.getString(1);
//                String Password = resultSet.getString(2);
//                Log.e("Query in : ",query);
//                Log.e("Height : ",Height);
//                Log.e("Weight : ",Weight);
//                Log.e("DOB :",Password);
//            }else{
//
//            }
              if(i==1){
                  status=true;
              }else {
                  status=false;
              }


        return status;
    }

    public boolean updateSelectedField(String email, String subService, String service) {
        boolean status=false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Service",String.valueOf(service));
        values.put("SubService",String.valueOf(subService));
        int i=db.update("UserInfo",values,"email = ?",new String[] {email});
        Log.e("Update :",String.valueOf(i));

        String x = '"'+email+'"';
        String query ="Select Service,SubService from UserInfo where email = "+x+";";
        Log.e("Query out : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        Log.e("Cursor : ",resultSet.toString());
            if (resultSet.moveToFirst()){
                String serv = resultSet.getString(0);
                String subserv = resultSet.getString(1);
               Log.e("Query in : ",query);
                Log.e("Service : ",serv);
                Log.e("SubService : ",subserv);

            }

        if(i==1){
            status=true;
        }else {
            status=false;
        }
        return status;
    }

    public Boolean updateBodyinfo(String email, boolean skinnyArms, boolean weakChest, boolean beerBelly, boolean thinLegs) {
            Boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email",email);
        values.put("SkinnyArms",String.valueOf(skinnyArms));
        values.put("WeakChest",String.valueOf(weakChest));
        values.put("BeerBelly",String.valueOf(beerBelly));
        values.put("ThinLegs",String.valueOf(thinLegs));
        double i = db.insert("GymInfo", null, values);
        if(i==-1){
            status=false;
        }else{
            status=true;
        }
      //db.update("GymInfo",values,"email = ?",new String[] {email});
      //  Log.e("Update :",String.valueOf(i));
            return status;
    }

    public Boolean updateBodyActiveness(String email, String bodyActiveness) {
            Boolean status=false;
            SQLiteDatabase db =this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("BodyActiveness",bodyActiveness);
            int i = db.update("GymInfo",values,"email = ?",new String[] {email});
            Log.e("Update :",String.valueOf(i));

            ContentValues value =new ContentValues();
             value.put("email",email);
              value.put("Steps","1000");
             value.put("Calories","1000");
             value.put("Water","20");
             value.put("Sleep","24");

             double j =db.insert("DailyGoals",null,value);


            if(i>=0){
            status=true;
            }else{
                status=false;
            }
        db.close();
                return status;
    }

    public Boolean insertPeriodInfo(String email, String periodLength, String cycleLength, String lastStartDate, String lastEndDate, String periodStatus) {
            Boolean status = false;
            SQLiteDatabase db = this.getWritableDatabase();
            int x =Integer.parseInt(cycleLength);
            int y= Integer.parseInt(periodLength);
            ContentValues values =new ContentValues();
            values.put("PeriodLength",y);
            values.put("CycleLength",x);
            values.put("LastStartDate",lastStartDate);
            values.put("LastEndDate",lastEndDate);
            values.put("PeriodStatus",periodStatus);
            values.put("email",email);
            double i =db.insert("PeriodInfo",null,values);
            if(i==-1){
                status=false;
            }else{
                status=true;
            }
            db.close();
            return  status;

    }

    public String[] getUserProfile(String email) {

            String x = '"'+email+'"';
            SQLiteDatabase db = this.getWritableDatabase();
            String query ="Select * from UserInfo where email = "+x+";";
            Log.e("Query out : ",query);
            Cursor resultSet = db.rawQuery(query,null);
            Log.e("Cursor : ",resultSet.toString());
            if (resultSet.moveToFirst()) {
                String userID = resultSet.getString(0);
                String Email = resultSet.getString(1);
                String Password = resultSet.getString(2);
                String FirstName = resultSet.getString(3);
                String LastName = resultSet.getString(4);
                String Gender = resultSet.getString(5);
                String Height = resultSet.getString(6);
                String Weight = resultSet.getString(7);
                String DOB = resultSet.getString(8);
                String Service = resultSet.getString(9);
                String SubService = resultSet.getString(10);
                String profile[]={userID,Email,Password,FirstName,LastName,Gender,Height,Weight,DOB,Service,SubService};
                for (int k=0 ;k<11;k++){
                    String msg = "profile db :"+k;
                    Log.e(msg, profile[k]);
                }
                return profile;

            }else {
                return null;
            }

    }
    public Boolean insertDailyGoals(String email,String sleep,String water,String calories,String steps){
        Boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put("email",email);
        values.put("Steps",steps);
        values.put("Calories",calories);
        values.put("Water",water);
        values.put("Sleep",sleep);

        double i =db.insert("DailyGoals",null,values);
        if(i==-1){
            status=false;
        }else{
            status=true;
        }
        return status;

    }

    public String[] getDailyGoals(String email) {
        String x = '"'+email+'"';
        String goals[]= new String[4];
        SQLiteDatabase db = getRDatabase();
        String query ="Select MAX(SrNo),Steps,Calories,Water,Sleep from DailyGoals where email = "+x+";";
        Log.e("Query out : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        Log.e("Cursor : ",resultSet.toString());
        if (resultSet.moveToFirst()) {
            String Steps = resultSet.getString(1);
            String Calories = resultSet.getString(2);
            String water = resultSet.getString(3);
            String sleep = resultSet.getString(4);
            goals[0] =Steps;
            goals[1]=Calories;
            goals[2]=water;
            goals[3]=sleep;

            return goals;

        }else {
            goals[0]="Nothing";
            return goals;
        }

    }

    public String[] getPeriodInfo(String email) {
        String x = '"'+email+'"';
        String periodINfo[]= new String[3];
        SQLiteDatabase db = getRDatabase();
        String query ="Select Min(SrNo),PeriodLength,CycleLength,LastStartDate from PeriodInfo where email = "+x+";";
        Log.e("Query out : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        Log.e("Cursor : ",resultSet.toString());
        if (resultSet.moveToFirst()) {
            String periodLength = resultSet.getString(1);
            String cycleLegth = resultSet.getString(2);
            String lastStartDate = resultSet.getString(3);

            periodINfo[0]=periodLength;
            periodINfo[1]=cycleLegth;
            periodINfo[2]=lastStartDate;
            return periodINfo;

        }else {
            periodINfo = null;
            return periodINfo;
        }
    }

    public Cursor getCompletedPeriodsHistory(String email) {
        String x = '"'+email+'"';
        String y = "\"end\"";
        String periodINfo[]= new String[3];
        SQLiteDatabase db = getRDatabase();
        String query ="Select * from PeriodInfo where email = "+x+" and PeriodStatus = "+y+" ;";
        Log.e("Query out cursor : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        Cursor temp =resultSet;
        Log.e("Return Cursor",resultSet.toString());

        return resultSet;
    }

    public int getAvgCycleLength(String email) {
            String x = '"'+email+'"';
            String y = "\"end\"";
            int length=0;

            SQLiteDatabase db = getReadableDatabase();
            String query ="Select AVG(CycleLength) from PeriodInfo where email = "+x+";";
            Log.e("Query out cursor : ",query);
            Cursor resultSet = db.rawQuery(query,null);
            if(resultSet.moveToFirst()){
                length = Integer.parseInt(resultSet.getString(0));
            }
            Log.e("Return Length",""+length);
            return length;
    }

    public Cursor getLatestCompletedPeriods(String email) {
        String x = '"'+email+'"';
        String y = "\"end\"";
        String periodINfo[]= new String[3];
        SQLiteDatabase db = getRDatabase();
        String query ="Select MAX(SrNo),email,PeriodLength,CycleLength,LastStartDate,LastEndDate from PeriodInfo where email = "+x+" and PeriodStatus = "+y+" ;";
        Log.e("Query out cursor : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        Cursor temp =resultSet;
        Log.e("Return Cursor",resultSet.toString());

        return resultSet;
    }

    public int getAvgPeriodLength(String email) {
        String x = '"'+email+'"';
        String y = "\"end\"";
        int length=0;

        SQLiteDatabase db = getReadableDatabase();
        String query ="Select AVG(PeriodLength) from PeriodInfo where email = "+x+"  and PeriodStatus = "+y+";";
        Log.e("Query out cursor : ",query);
        Cursor resultSet = db.rawQuery(query,null);
        if(resultSet.moveToFirst()){
            length = Integer.parseInt(resultSet.getString(0));
        }
        Log.e("Return Length",""+length);
        return length;
    }
}