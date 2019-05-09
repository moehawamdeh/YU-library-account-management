package jo.edu.yu.yarmouklibrary.view.Utilts;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;

public class LibraryUtils {
    public static void showSnackBar(View view, String msg, String action, View.OnClickListener listener){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction(action, listener);
        snackbar.show();
    }
    public static void showMessageOKCancel(String message, Context context, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create()
                .show();
    }
}
