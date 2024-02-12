package com.example.timemap.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timemap.model.Event;
import com.example.timemap.model.User;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class DatabaseController {
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    public DatabaseController(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public DatabaseController createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e("creationError",mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DatabaseController open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e("openError", "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    // QUERIES DOWN BELOW
    public Set<Event> getUserEvents(User currentUser) {
        try {
            String sql ="SELECT * FROM event WHERE 'user_id' = ?";
            Set<Event> dbEvents = new TreeSet<>();
            Cursor cursor = mDb.rawQuery(sql, new String[]{String.valueOf(currentUser.getId())});
            if (cursor != null && cursor.moveToFirst()) {
                while(cursor.moveToNext()){
                    Event e = new Event();
                    e.setEventId(cursor.getLong(0));
                    e.setName(cursor.getString(1));
                    e.setDescription(cursor.getString(2));
                    e.setEndTime(cursor.getLong(3));
                    e.setUser(currentUser);
                    e.setFilters(cursor.getString(5));
                    dbEvents.add(e);
                }
                return dbEvents;
            }
        } catch (Exception ex) {
            Log.e("", "getUserEvents(User user) >>"+ ex.toString());
            throw ex;
        }
        return null;
    }

    public boolean addNewEvent(Event newEvent){
        mDb.beginTransaction();
        //review
        try {
            ContentValues values = new ContentValues();
            values.put("name",newEvent.getName());
            values.put("description",newEvent.getDescription());
            values.put("limit",newEvent.getRemainingTime());
            values.put("user_id",newEvent.getUser().getId());
            values.put("tag", newEvent.getFiltersAsString());
            mDb.insert("event", null, values);
            mDb.setTransactionSuccessful();
            return true;
        }
        catch(Exception e){
            Log.e("addNewEvent",e.getMessage());
            return false;
        }
        finally{
            mDb.endTransaction();
        }
    }

    public User queryGetUser(String pass, String username) {
        try{
            String sql = "SELECT * FROM user WHERE pass = ? AND username = ?";
            @SuppressLint("Recycle") Cursor cursor = mDb.rawQuery(sql, new String[]{ pass, username });
            if (cursor != null && cursor.moveToFirst()) {
                User user = new User();
                user.setId(cursor.getLong(0));
                user.setUsername(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPass(cursor.getString(3));
                return user;
            }
        } catch (Exception e) {
            Log.e("queryGetUser", "getUserEvents(User user) >>"+ e.toString());
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean queryEmailExists(String email){
        try{
            String query = "SELECT * from user WHERE email = ?";
            Cursor cursor = mDb.rawQuery(query,new String[]{email});
            return cursor.moveToFirst();
        }
        catch (Exception e){
            Log.e("queryEmailExists", "getUserEvents(User user) >>"+ e.toString());
            throw new RuntimeException(e);
        }
    }

    public boolean queryUserExists(String user){
        try{
            String query = "SELECT * from user WHERE username = ?";
            Cursor cursor = mDb.rawQuery(query,new String[]{user});
            return cursor.moveToFirst();
        }
        catch (Exception e){
            Log.e("queryUserExists", "getUserEvents(User user) >>"+ e.toString());
            throw new RuntimeException(e);
        }
    }

    public void addNewUser(User newUser){
        mDb.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("username", newUser.getUsername());
            values.put("email", newUser.getEmail());
            values.put("pass", newUser.getPass());
            mDb.insert("user", null, values);
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
    }
}
