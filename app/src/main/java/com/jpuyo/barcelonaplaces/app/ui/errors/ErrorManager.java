package com.jpuyo.barcelonaplaces.app.ui.errors;

import android.content.Context;
import android.widget.Toast;

/**
 * Class used to show errors
 */
public class ErrorManager {

    private static Context context;

    public ErrorManager(Context context) {
        ErrorManager.context = context;
    }

    public void showError(int idError){
        Toast.makeText(context, idError, Toast.LENGTH_LONG).show();
    }
}
