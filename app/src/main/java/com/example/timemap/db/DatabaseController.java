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

/***
 * Clase que se encarga de manejar la base de datos SQLite. Las conexiones se abren desde las clases controladoras, pero siempre se cierran desde aqui.
 * */
public class DatabaseController {
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    /**
     * Constructor. Recibe el contexto de la aplicacion e instancia un DatabaseHelper
     * @param context
     */
    public DatabaseController(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    /**
     * Crea la base de datos a través de la instancia de DatabaseHelper
     * @return
     * @throws SQLException
     */
    public DatabaseController createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e("creationError",mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    /**
     * Intenta abrir la conexion a través de DatabaseHelper
     * @return
     * @throws SQLException
     */
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

    // EVENT QUERIES DOWN BELOW

    /**
     * Devuelve un Set con los eventos relacionados con un usuario.
     * @param currentUser
     * @return
     */
    public Set<Event> getUserEvents(User currentUser) {
        try {
            String sql ="SELECT * FROM event WHERE user_id = ?";
            Set<Event> dbEvents = new TreeSet<>();
            Cursor cursor = mDb.rawQuery(sql, new String[]{String.valueOf(currentUser.getId())});
            if (cursor != null && cursor.moveToFirst()) {
                do{
                    Event e = new Event();
                    e.setEventId(cursor.getLong(0));
                    e.setName(cursor.getString(1));
                    e.setDescription(cursor.getString(2));
                    e.setEndTime(cursor.getLong(3));
                    e.setUser(currentUser);
                    e.setFilters(cursor.getString(5));
                    dbEvents.add(e);
                }
                while(cursor.moveToNext());
                return dbEvents;
            }
        } catch (Exception ex) {
            Log.e("", "getUserEvents(User user) >>"+ ex.toString());
            throw ex;
        }
        finally {
            mDb.close();
        }
        return null;
    }

    /**
     * Inicia una transacción e inserta la información relacionada con el evento en la base de datos.
     * Además, setea el id del evento en memoria según el asignado en la base de datos.
     * @param newEvent
     * @return true si se añadio el evento, false de lo contrario
     */
    public boolean addNewEvent(Event newEvent){
        mDb.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("name", newEvent.getName());
            values.put("description", newEvent.getDescription());
            values.put("time_limit", newEvent.getEndTime().getAsMilliseconds());
            values.put("user_id", newEvent.getUser().getId());
            values.put("tag", newEvent.getFiltersAsString());
            long eventId = mDb.insert("event", null, values);

            if(eventId == -1) {
                Log.e("addNewEvent", "Error adding the event");
                return false;
            } else {
                newEvent.setEventId(eventId);
                mDb.setTransactionSuccessful();
                return true;
            }
        } catch(Exception e) {
            Log.e("addNewEvent", e.getMessage());
            return false;
        } finally {
            mDb.endTransaction();
            mDb.close();
        }
    }

    /**
     * Elimina las referencias en la base de datos que coincidan con el evento.
     * @param event
     * @return true si se eliminó el registro. false de lo contrario
     */
    public boolean removeEvent(Event event){
        int deletedRows = 0;
        mDb.beginTransaction();
        try{
            String selection = "id = ?";
            String[] selectionArgs = { String.valueOf(event.getEventId()) };
            deletedRows = mDb.delete("event", selection, selectionArgs);
            mDb.setTransactionSuccessful();
        }
        catch(Exception e){
            Log.e("removeEvent",e.getMessage());
        }
        finally {
            mDb.endTransaction();
            mDb.close();
        }
        return deletedRows>0;
    }

    /***
     * Actualiza las referencias a un determinado evento en la base de datos.
     * @param event
     * @return true si el evento fue editado, false de lo contrario
     */
    public boolean updateEvent(Event event){
        int updatedRows = 0;
        mDb.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put("name", event.getName());
            values.put("description", event.getDescription());
            values.put("time_limit", event.getRemainingTime());
            values.put("tag", event.getFiltersAsString());
            String selection = "id = ?";
            String[] selectionArgs = { String.valueOf(event.getEventId()) };
            updatedRows = mDb.update("event", values, selection, selectionArgs);
            mDb.setTransactionSuccessful();
        }
        catch(Exception e){
            Log.e("removeEvent",e.getMessage());
        }
        finally {
            mDb.endTransaction();
            mDb.close();
        }
        return updatedRows > 0;
    }

    // USER QUERIES DOWN BELOW
    /**
     * REALIZA UNA CONSULTA A LA BASE DE DATOS para comprobar si la contraseña y usuario
     * proporcionados coinciden con algún registro.
     * @param pass
     * @param username
     * @return Objeto usuario si se encontró coincidencia, null de lo contrario
     */
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
        finally {
            mDb.close();
        }
        return null;
    }

    /**
     * Realiza una consulta a la base de datos para comprobar si el email coincide con algun registro.
     * @param email
     * @return true si coincide, false si no coincide
     */
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
        finally {
            mDb.close();
        }
    }

    /**
     * Determina si el username coincide con algún registro en la base de datos
     * @param user
     * @return true si coincide, false de lo contrario.
     */
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
        finally {
            mDb.close();
        }
    }

    /**
     * Agraga un nuevo usuario a la base de datos a través de la info extraida del objeto User.
     * Además, asigna a dicho objeto en memoria el id asignado en la base de datos.
     * @param newUser
     * @return true si se añadio, false de lo contrario
     */
    public boolean addNewUser(User newUser){
        mDb.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("username", newUser.getUsername());
            values.put("email", newUser.getEmail());
            values.put("pass", newUser.getPass());
            long userId = mDb.insert("user", null, values);
            if(userId == -1) {
                Log.e("addNewUser", "Error adding the user");
                return false;
            } else {
                newUser.setId(userId);
                mDb.setTransactionSuccessful();
                return true;
            }
        } finally {
            mDb.endTransaction();
            mDb.close();
        }
    }

    /**
     * Actualiza el regiistro de usuario en la base de datos que coincida con el de params
     * @param user
     * @return true si se actualizo el registro, false de lo contrario
     */
    public boolean updateUser(User user){
        int updatedRows = 0;
        mDb.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("email", user.getEmail());
            values.put("pass", user.getPass());
            String selection = "id = ?";
            String[] selectionArgs = { String.valueOf(user.getId()) };
            updatedRows = mDb.update("user", values, selection, selectionArgs);
            mDb.setTransactionSuccessful();
        }
        catch(Exception e){
            Log.e("updateUser",e.getMessage());
        }
        finally {
            mDb.endTransaction();
            mDb.close();
        }
        return updatedRows > 0;
    }

    /***
     * Elimina cualquier registro en la base de datos que coincida con el id del user pasado por params
     * @param user
     * @return true si se elimino algun registro, false de lo contrario
     */
    public boolean removeUser(User user){
        int deletedRows = 0;
        mDb.beginTransaction();
        try{
            String selection = "id = ?";
            String[] selectionArgs = { String.valueOf(user.getId()) };
            deletedRows = mDb.delete("user", selection, selectionArgs);
            mDb.setTransactionSuccessful();
        }
        catch(Exception e){
            Log.e("removeUser",e.getMessage());
        }
        finally {
            mDb.endTransaction();
            mDb.close();
        }
        return deletedRows>0;
    }

    public DatabaseHelper getHelper(){
        return mDbHelper;
    }
}