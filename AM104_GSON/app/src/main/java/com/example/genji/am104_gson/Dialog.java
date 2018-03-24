package com.example.genji.am104_gson;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

/**
 * Created by studente on 24/03/18.
 */

public class Dialog extends DialogFragment {

    public AlertDialog createDialog(Bundle savedInstanceState,List<Match> list) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Partite")
                .setItems(list, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
