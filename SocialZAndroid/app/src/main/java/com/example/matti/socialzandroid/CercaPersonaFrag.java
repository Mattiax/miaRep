package com.example.matti.socialzandroid;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CercaPersonaFrag extends Fragment {

    private EditText email;
    private Button cerca;
    private static TextView nome, cognome, dataNascita, sesso, telefono, indirizzo;
    private static ScrollView layout;
    private static Context cStat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cerca_persona, container, false);
        email = view.findViewById(R.id.cercaEmail);
        cerca = view.findViewById(R.id.cercaBtn);
        nome = view.findViewById(R.id.nome_search);
        cognome = view.findViewById(R.id.cognome_search);
        dataNascita = view.findViewById(R.id.data_nascita_search);
        sesso = view.findViewById(R.id.sesso_search);
        telefono = view.findViewById(R.id.telefono_search);
        indirizzo = view.findViewById(R.id.indirizzo_search);
        layout = view.findViewById(R.id.layout_data);
        cStat = getActivity().getApplication().getApplicationContext();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.INVISIBLE);
                if (email.getText().toString().length() > 0) {
                    new DoCercaPersona(email.getText().toString()).execute();
                }
            }
        });
    }

    public static void mostraDati(String json) {
        try {
            JSONObject dati = new JSONObject(json);
            if (dati.length() <= 0) {
                Toast.makeText(cStat, cStat.getString(R.string.not_exist), Toast.LENGTH_LONG).show();
            } else {
                if (dati.getBoolean("visibile")) {
                    nome.setText(dati.getString("nome"));
                    cognome.setText(dati.getString("cognome"));
                    dataNascita.setText(dati.getString("dataNascita"));
                    sesso.setText(dati.getString("sesso"));
                    telefono.setText(dati.getString("telefono").isEmpty() ? "-" : dati.getString("telefono"));
                    indirizzo.setText(dati.getString("indirizzo").isEmpty() ? "-" : dati.getString("indirizzo"));
                    layout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(cStat, cStat.getString(R.string.privacy_error), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(cStat, cStat.getString(R.string.JSON_conversion_error), Toast.LENGTH_SHORT).show();
        }
    }
}
