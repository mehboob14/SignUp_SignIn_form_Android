package com.example.signup_signin;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

    public class DatabaseHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "SignUp";
        private static final String TABLE_CONTACTS = "SIGNIN";
        private static final String KEY_EMAIL = "Email";
        private static final String KEY_USERNAME = "Username";
        private static final String KEY_PASSWORD = "Password";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                    + KEY_EMAIL + " TEXT PRIMARY KEY,"
                    + KEY_USERNAME + " TEXT,"
                    + KEY_PASSWORD + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }

        public UserInfo getuser(String email) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_EMAIL, KEY_USERNAME, KEY_PASSWORD},
                    KEY_EMAIL + "=?", new String[]{email}, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                UserInfo user = new UserInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                cursor.close();
                return user;
            }
            return null;
        }

        public void AddUser(UserInfo userData) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_EMAIL, userData.getEmail());
            values.put(KEY_USERNAME, userData.getUsername());
            values.put(KEY_PASSWORD, userData.getPassword());
            db.insert(TABLE_CONTACTS, null, values);
            db.close();
        }
    }
