package com.example.landfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "landfinder.db";
    private static final int DATABASE_VERSION = 2; // Incremented version to trigger onUpgrade()

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";

    // Properties Table
    private static final String TABLE_PROPERTIES = "properties";
    private static final String PROP_ID = "id";
    private static final String PROP_NAME = "name";
    private static final String PROP_LOCATION = "location";
    private static final String PROP_PRICE = "price";
    private static final String PROP_IMAGE = "image";

    // Appointments Table
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String APP_ID = "id";
    private static final String APP_PROP_ID = "property_id";
    private static final String APP_NAME = "name";
    private static final String APP_PHONE = "phone";
    private static final String APP_DATE = "date";
    private static final String APP_TIME = "time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " TEXT NOT NULL, " +
                    USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                    USER_PASSWORD + " TEXT NOT NULL)";

            String CREATE_PROPERTIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROPERTIES + " (" +
                    PROP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PROP_NAME + " TEXT NOT NULL, " +
                    PROP_LOCATION + " TEXT NOT NULL, " +
                    PROP_PRICE + " TEXT NOT NULL, " +
                    PROP_IMAGE + " TEXT NOT NULL)";

            String CREATE_APPOINTMENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_APPOINTMENTS + " (" +
                    APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    APP_PROP_ID + " INTEGER NOT NULL, " +
                    APP_NAME + " TEXT NOT NULL, " +
                    APP_PHONE + " TEXT NOT NULL, " +
                    APP_DATE + " TEXT NOT NULL, " +
                    APP_TIME + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + APP_PROP_ID + ") REFERENCES " + TABLE_PROPERTIES + "(" + PROP_ID + "))";

            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_PROPERTIES_TABLE);
            db.execSQL(CREATE_APPOINTMENTS_TABLE);

            Log.d("DatabaseHelper", "Tables created successfully.");
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
            onCreate(db);
            Log.d("DatabaseHelper", "Database upgraded successfully.");
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error upgrading database: " + e.getMessage());
        }
    }

    public boolean insertUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error inserting user.");
            return false;
        } else {
            Log.d("DatabaseHelper", "User inserted successfully.");
            return true;
        }
    }

    public boolean insertProperty(String name, String location, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROP_NAME, name);
        values.put(PROP_LOCATION, location);
        values.put(PROP_PRICE, price);
        values.put(PROP_IMAGE, image);

        long result = db.insert(TABLE_PROPERTIES, null, values);
        return result != -1;
    }

    public boolean insertAppointment(int propertyId, String name, String phone, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_PROP_ID, propertyId);
        values.put(APP_NAME, name);
        values.put(APP_PHONE, phone);
        values.put(APP_DATE, date);
        values.put(APP_TIME, time);

        long result = db.insert(TABLE_APPOINTMENTS, null, values);
        return result != -1;
    }

    public Cursor getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + "=? AND " + USER_PASSWORD + "=?", new String[]{email, password});
    }

    public Cursor getAllProperties() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES, null);
    }

    public Cursor getPropertyById(int propertyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES + " WHERE " + PROP_ID + "=?", new String[]{String.valueOf(propertyId)});
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
    }


    public Cursor getAppointmentsForProperty(int propertyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " + APP_PROP_ID + "=?", new String[]{String.valueOf(propertyId)});
    }
}
