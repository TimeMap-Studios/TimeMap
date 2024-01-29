package com.example.timemap.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmationDialog {

    public static void askForConfirmation(Context context, String mensaje, final ConfirmacionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mensaje)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Si el usuario hace clic en "Sí", llamamos al callback con true
                        callback.onConfirmacion(true);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Si el usuario hace clic en "No", llamamos al callback con false
                        callback.onConfirmacion(false);
                        dialog.dismiss();
                    }
                });

        // Creamos y mostramos el cuadro de diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface ConfirmacionCallback {
        void onConfirmacion(boolean confirmado);
    }

}
