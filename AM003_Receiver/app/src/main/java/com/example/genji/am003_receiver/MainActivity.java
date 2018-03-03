package com.example.genji.am003_receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private TextView errore;
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button launch = findViewById(R.id.launch);
        final Intent intent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        errore = findViewById(R.id.error);
        rb=findViewById(R.id.updateCurrent);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    //questo intent può essere usato una volta sola poi viene assegnato un valore nullo
                    case R.id.oneShot:
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_ONE_SHOT);
                        break;
                        //questo non crea nessun intent
                    case R.id.noCreate:
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_NO_CREATE);
                        break;
                        //questo non cambia l'inte e rimane sempre uguale
                    case R.id.immutable:
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
                        break;
                        //questo elimina l'attuale intent per poi dare la possibilità di creare uin altro
                    case R.id.cancelCurrent:
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        break;
                        //aggiorna l'attuale intent
                    case R.id.updateCurrent:
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent , PendingIntent.FLAG_UPDATE_CURRENT);
                        break;
                }
                launch.setVisibility(View.VISIBLE);
            }
        });

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });
    }

    public void startAlert() {
        try {
            rb.setClickable(true);
            pendingIntent.send();
        } catch (Exception e) {
            errore.setText("Pending error\n" + e.getMessage());
        }
    }
}
