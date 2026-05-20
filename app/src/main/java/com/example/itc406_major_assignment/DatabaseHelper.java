package com.example.itc406_major_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Hospital.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";

    public static final String COL_ID = "id";
    public static final String COL_FIRSTNAME = "firstname";
    public static final String COL_LASTNAME = "lastname";
    public static final String COL_GENDER = "gender";
    public static final String COL_ADDRESS = "address";
    public static final String COL_PHONE = "phone";
    public static final String COL_ROLE = "role";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_FIRSTNAME + " TEXT,"
                + COL_LASTNAME + " TEXT,"
                + COL_GENDER + " TEXT,"
                + COL_ADDRESS + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_ROLE + " TEXT,"
                + COL_USERNAME + " TEXT,"
                + COL_PASSWORD + " TEXT"
                + ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // INSERT USER
    public boolean insertUser(String firstname, String lastname,
                              String gender, String address,
                              String phone, String role,
                              String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_FIRSTNAME, firstname);
        values.put(COL_LASTNAME, lastname);
        values.put(COL_GENDER, gender);
        values.put(COL_ADDRESS, address);
        values.put(COL_PHONE, phone);
        values.put(COL_ROLE, role);
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    // GET ALL USERS
    public Cursor getAllUsers() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    // DELETE USER
    public void deleteUser(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USERS, "id=?", new String[]{String.valueOf(id)});
    }

    // UPDATE USER
    public boolean updateUser(int id,
                              String firstname,
                              String lastname,
                              String gender,
                              String address,
                              String phone,
                              String role,
                              String username,
                              String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_FIRSTNAME, firstname);
        values.put(COL_LASTNAME, lastname);
        values.put(COL_GENDER, gender);
        values.put(COL_ADDRESS, address);
        values.put(COL_PHONE, phone);
        values.put(COL_ROLE, role);
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);

        int result = db.update(TABLE_USERS,
                values,
                "id=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }

    // COUNT STAFF
    public int getStaffCount() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE role='Staff'", null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    // COUNT PATIENT
    public int getPatientCount() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE role='Patient'", null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    // GET SINGLE USER
    public Cursor getUserById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE id=?",
                new String[]{String.valueOf(id)});
    }
}