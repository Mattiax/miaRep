package com.example.matti.tris;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by MATTI on 04/02/2018.
 */

public class Dati implements Observer {

    private String[][] mosse;
    private boolean turnoX;
    private int click;
    private String playerX, playerO;

    public Dati(String player1, String player2) {
        mosse = new String[][]{{"n", "n", "n"}, {"n", "n", "n"}, {"n", "n", "n"}};
        turnoX = false;
        click = 9;
        playerX = player1;
        playerO = player2;
    }

    @Override
    public void update(Observable observable, Object o) {
        MainActivity.Osservatore my = (MainActivity.Osservatore) (observable);
        click--;
        int corX = my.pos.charAt(0) - 48;
        int corY = my.pos.charAt(1) - 48;
        MainActivity.table[corX][corY].setClickable(false);
        if (turnoX) {
            MainActivity.table[corX][corY].setBackgroundResource(R.drawable.ex);
            mosse[corX][corY] = "x";
            turnoX = false;
        } else {
            MainActivity.table[my.pos.charAt(0) - 48][my.pos.charAt(1) - 48].setBackgroundResource(R.drawable.circle);
            turnoX = true;
            mosse[corX][corY] = "o";
        }
        boolean vittoriaXO[] = {false, false};
        for (int i = 0; i < 3; i++) {
            int matchX = 0, matchO = 0;
            for (int j = 0; j < 3; j++) {
                if (mosse[i][j].equals("x")) {
                    matchX++;
                } else if (mosse[i][j].equals("o")) {
                    matchO++;
                }
            }
            if (matchX == 3) {
                vittoriaXO[0] = true;
                break;
            } else if (matchO == 3) {
                vittoriaXO[1] = true;
                break;
            }
        }
        if (!vittoriaXO[0] && !vittoriaXO[1]) {
            for (int i = 0; i < 3; i++) {
                int matchX = 0, matchO = 0;
                for (int j = 0; j < 3; j++) {
                    if (mosse[j][i].equals("x")) {
                        matchX++;
                    } else if (mosse[j][i].equals("o")) {
                        matchO++;
                    }
                }
                if (matchX == 3) {
                    vittoriaXO[0] = true;
                    break;
                } else if (matchO == 3) {
                    vittoriaXO[1] = true;
                    break;
                }
            }
        }
        if (!vittoriaXO[0] && !vittoriaXO[1]) {
            int matchXs = 0, matchOs = 0, matchXi = 0, matchOi = 0;
            for (int i = 0; i < 3; i++) {
                if (mosse[i][i].equals("x")) {
                    matchXs++;
                } else if (mosse[i][i].equals("o")) {
                    matchOs++;
                }
                if (mosse[i][2 - i].equals("x")) {
                    matchXi++;
                } else if (mosse[i][2 - i].equals("o")) {
                    matchOi++;
                }
            }
            if (matchXs == 3 || matchXi == 3) {
                vittoriaXO[0] = true;
            } else if (matchOs == 3 || matchOi == 3) {
                vittoriaXO[1] = true;
            }
        }
        if (vittoriaXO[0]) {
            MainActivity.dialogVictory(playerX);
        } else if (vittoriaXO[1]) {
            MainActivity.dialogVictory(playerO);
        } else if (click == 0) {
            MainActivity.dialogVictory("");
        }
    }
}
