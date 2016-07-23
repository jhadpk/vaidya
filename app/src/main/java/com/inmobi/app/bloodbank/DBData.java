package com.inmobi.app.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

import javax.xml.validation.Validator;

/**
 * Created by deepak.jha on 06/08/15.
 */
public class DBData {
    public static int insertsuccessful = 0;
    private static final String TAG = "DBData";
    private static final String DATABASE_NAME = "usersdb";
    private static final String DATABASE_TABLE = "users";
    private static final String AVLBL_DATABASE_TABLE = "available";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String BLOODGP = "bloodgp";
    private static final String EMAIL = "emailid";
    private static final String WEIGHT = "weight";
    private static final String DONOR = "donor";
    private static final Boolean PREGNANT = false;
    private static final String GENDER = "gender";
    private static final String USERAVAILABILITY = "availability";
    private static final String USERPHONE = "phone";
    private static final String USERLAT = "lat";
    private static final String USERLON = "lon";



    //private static final String PASSWORD = "password";

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ( "+
                    ID + " integer primary key autoincrement, " +
                    NAME + " text not null, " +
                    PHONE + " text not null, " +
                    BLOODGP + " text not null, " +
                    EMAIL + " text not null, " +
                    WEIGHT + " text, " +
                    DONOR + " text, " +
                    PREGNANT + " text, " +
                    GENDER + " text not null);";

    private static final String AVLBL_DATABASE_CREATE =
            "create table " + AVLBL_DATABASE_TABLE + " ( "+
                    ID + " integer primary key autoincrement, " +
                    USERAVAILABILITY + " text not null, " +
                    USERPHONE + " text not null, " +
                    USERLAT + " text not null, " +
                    USERLON + " text not null);";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBData(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL(DATABASE_CREATE);
            db.execSQL(AVLBL_DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }


    public void open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
    }

    public void insertdata(String name, String phone, String bloodgp, String email, String weight,
                           String donortype, Boolean pregnant, String gender) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(PHONE, phone);
        values.put(BLOODGP, bloodgp);
        values.put(EMAIL, email);
        values.put(WEIGHT, weight);
        values.put(DONOR, donortype);
        values.put(String.valueOf(PREGNANT), pregnant);
        values.put(GENDER, gender);

        db.insert(DATABASE_TABLE, null, values);
        insertsuccessful=1;
        db.close(); // Closing database connection
    }

    public void insertavlbl(String availability, String phone, String lat, String lon) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERAVAILABILITY, availability);
        values.put(USERPHONE, phone);
        values.put(USERLAT, lat);
        values.put(USERLON, lon);
        db.insert(AVLBL_DATABASE_TABLE, null, values);
        db.close(); // Closing database connection
    }


    public void close()
    {
        DBHelper.close();
    }

    public boolean Login(String phone) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + PHONE + " =? ", new String[]{phone});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

}