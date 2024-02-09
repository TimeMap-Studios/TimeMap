package com.example.timemap.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.timemap.model.Event;
import com.example.timemap.model.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    public TestAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Set<Event> getUserEvents(User user) {
        try {
            String sql ="SELECT * FROM event WHERE 'user_id' = " + user.getId() + ";";
            Set<Event> dbEvents = new TreeSet<>();
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                while(mCur.moveToNext()){
                    Event e = new Event();
                    e.setEventId(mCur.getLong(0));
                    e.setName(mCur.getString(1));
                    e.setDescription(mCur.getString(2));
                    e.setEndTime(mCur.getLong(3));
                    //e.setUser(mCur.getString(4));
                    e.setFilters(mCur.getString(5));
                    dbEvents.add(e);
                }
                return dbEvents;
            }
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return null;
    }

    public User getUser(String pass, String username) {
        try{
            String sql = "SELECT *  FROM user WHERE password = ? AND username = ?;";
            @SuppressLint("Recycle") Cursor cursor = mDb.rawQuery(sql, new String[]{pass, username});
            if (cursor != null && cursor.moveToFirst()) {
                User user = new User();
                user.setId(cursor.getLong(0));
                user.setUsername(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPass(cursor.getString(3));
                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
