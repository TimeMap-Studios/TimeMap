package com.example.timemap.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/***
 * Clase con lógica de creacion de la base de datos. Incluye métodos para validar, abrir y cerrar conexion a la base de datos.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "timemap.db";
    public static String DATABASE_PATH = "";
    private SQLiteDatabase timemapDB;
    private Context context;
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = "/data/data/"+context.getPackageName()+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if(timemapDB!=null){
            timemapDB.close();
        }
        super.close();
    }
    /***
     * Check if the database exists on device or not.
     * Crea base de datos temporal con el path del dispositivo virtual. Si la base de datos se ha creado y existe, cierra la conexion y devulve true.
     * De lo contrario devuelve false.
     */
    private boolean tryCreate() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("timemap - error", e.getMessage());
        }
        if (tempDB != null){tempDB.close();}
        return tempDB != null;
    }
    /***
    * Copia la base de datos desde la carpeta assets hasta el directorio virtual.
    * */
    public void copyDataBaseFromAssets() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            String outputFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("timemap - copyDatabase", e.getMessage());
        }
    }

    public void copyDataBaseToDownloads() throws IOException {
        try {
            InputStream myInput = new FileInputStream(DATABASE_PATH + DATABASE_NAME);
            OutputStream myOutput = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+DATABASE_NAME);
            Log.e("ruta",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+DATABASE_NAME);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("timemap - copyDatabase", e.getMessage());
        }
    }

    /***
     * lanza una excepcion si la base de datos no se abre.
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        timemapDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    /***
    * Comprueba si la base de datos existe a través del método tryCreate().
     * si no existe, copia la base de datos de la carpeta assets al dispositivo virtual.
    * */
    public void createDataBase() throws IOException {
        boolean dbExist = tryCreate();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBaseFromAssets();
                Log.e("timemap - create", "base de datos copiada en el dispositivo virtual");
            } catch (IOException e) {
                Log.e("timemap - create", e.getMessage());
            }
        }
    }
}