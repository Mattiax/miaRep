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
                errore.setText("");
                switch (checkedId) {
                    //questo intent può essere usato una volta sola poi viene assegnato un valore nullo
                    case R.id.oneShot:
                        errore.setText("Viene eseguito solo una volta");
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_ONE_SHOT);
                        break;
                        //questo non crea nessun pendingintent se quello attuale non esiste
                    case R.id.noCreate:
                        errore.setText("Se precedentemente non è stato creato nessun pendingintent, questo non verrà creato.");
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_NO_CREATE);
                        break;
                        //questo non passa i nuovi argomenti e rimane sempre uguale
                    case R.id.immutable:
                        errore.setText("Eventuali argomenti passati all'intnt non verranno passati");
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
                        break;
                        //questo elimina l'attuale intent per poi dare la possibilità di creare uin altro
                    case R.id.cancelCurrent:
                        errore.setText("Se precedentemente è stato creato un pendingintent, questo verrà cancellato e si dovrà richiamare un altro pending con getActivity()");
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        break;
                        //aggiorna l'attuale intent
                    case R.id.updateCurrent:
                        errore.setText("Se precedentemente è stato creato un pendingintent, a quello attuale verranno passate le nuove informazioni");
                        pendingIntent = PendingIntent.getActivity(MainActivity.this, 234324243, intent,PendingIntent.FLAG_UPDATE_CURRENT);
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
