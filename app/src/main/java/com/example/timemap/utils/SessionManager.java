package com.example.timemap.utils;

import android.content.Context;
import android.util.Log;

import com.example.timemap.LoginActivity;
import com.example.timemap.controller.UserController;
import com.example.timemap.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase encargada de la logica de sesion de usuario
 */
public class SessionManager {
    private static final String FILENAME = "user_session.ser";
    private Context context;
    private static SessionManager instance;

    private SessionManager() {
        context = LoginActivity.getInstance().getApplicationContext();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /***
     * Salva la sesion actual del usuario serializando el objeto en un fichero.
     * @param user
     * @return true si la sesion se guardo correctamente
     */
    public boolean saveCurrentSession(User user) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.e("SessionManager", "User session saved successfully");
            return true;
        } catch (IOException e) {
            Log.e("SessionManager", "Error saving user session: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Limpia la sesion del usuario actual eliminando el contenido del fichero.
     * @return true si se elimino la sesiond e usuario
     */
    public boolean clearCurrentSession() {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.getChannel().truncate(0);
            fileOutputStream.close();
            Log.e("SessionManager", "User session cleared successfully");
            return true;
        } catch (IOException e) {
            Log.e("SessionManager", "Error clearing user session: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Determina si la sesion actual está vacia o iniciada preguntando si el fichero está o no vacio
     * @return true si no hay sesion iniciada.
     */
    public boolean emptySession() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            if (!file.exists() || file.length() == 0) {
                Log.d("SessionManager", "User session is empty");
                return true;
            } else {
                Log.d("SessionManager", "User session is not empty");
                return false;
            }
        } catch (Exception e) {
            Log.e("SessionManager", "Error checking if user session is empty: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User getSessionUser(){
        try {
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            User user = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            Log.d("SessionManager", "User session loaded successfully");
            return user;
        } catch (IOException | ClassNotFoundException e) {
            Log.e("SessionManager", "Error loading user session: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}


