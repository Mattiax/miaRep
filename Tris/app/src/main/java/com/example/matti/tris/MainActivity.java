package com.example.matti.tris;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;

public class MainActivity extends AppCompatActivity {

    public static ImageView[][] table;
    static DialogInterface.OnClickListener dismiss, newGame;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button start = findViewById(R.id.startBtn);
        final EditText player1 = findViewById(R.id.player1);
        final EditText player2 = findViewById(R.id.player2);
        context = this;
        table = new ImageView[3][3];
        table[0][0] = findViewById(R.id.b00);
        table[0][1] = findViewById(R.id.b01);
        table[0][2] = findViewById(R.id.b02);
        table[1][0] = findViewById(R.id.b10);
        table[1][1] = findViewById(R.id.b11);
        table[1][2] = findViewById(R.id.b12);
        table[2][0] = findViewById(R.id.b20);
        table[2][1] = findViewById(R.id.b21);
        table[2][2] = findViewById(R.id.b22);
        final Osservatore c = new Osservatore();

        final View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.refresh(String.valueOf(view.getTag()));
            }
        };

        dismiss = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        newGame = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        };

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player1.getText().length() > 0 && player2.getText().length() > 0) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            table[i][j].setOnClickListener(click);
                        }
                    }
                    start.setEnabled(false);
                    player1.setEnabled(false);
                    player2.setEnabled(false);
                    c.addObserver(new Dati(player1.getText().toString(), player2.getText().toString()));
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.no_player_name), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void dialogVictory(String ris) {
        if (ris.isEmpty()) {
            ris = context.getResources().getString(R.string.no_victory);
        } else {
            ris = context.getResources().getString(R.string.victory) + " " + ris;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(ris)
                .setTitle(context.getResources().getString(R.string.result));
        builder.setPositiveButton(R.string.new_game, newGame);
        builder.setNegativeButton(R.string.exit, dismiss);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    class Osservatore extends Observable {

        String pos;

        void refresh(String id) {
            pos = id;
            setChanged();
            notifyObservers();
        }
    }
}
