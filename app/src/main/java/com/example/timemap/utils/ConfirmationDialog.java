package com.example.timemap.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * A utility class for displaying a confirmation dialog with a callback interface.
 */
public class ConfirmationDialog {

    /**
     * askForConfirmation:
     * Displays a confirmation dialog with the specified message and invokes the callback based on user input.
     *
     * @param context  The context in which the dialog should be displayed.
     * @param mensaje  The message to be displayed in the confirmation dialog.
     * @param callback A ConfirmacionCallback interface to handle user confirmation.
     */
    public static void askForConfirmation(Context context, String mensaje, final ConfirmationCallback callback) {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the message and positive/negative button callbacks
        builder.setMessage(mensaje)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If the user clicks "YES" invoke the callback with true
                        callback.onConfirmation(true);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If the user clicks "NO" invoke the callback with false
                        callback.onConfirmation(false);
                        dialog.dismiss();
                    }
                });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * An interface for handling confirmation callbacks.
     */
    public interface ConfirmationCallback {
        /**
         * Invoked when the user confirms or denies the action.
         *
         * @param confirmado True if the user confirms, false if denied.
         */
        void onConfirmation(boolean confirmado);
    }

}
