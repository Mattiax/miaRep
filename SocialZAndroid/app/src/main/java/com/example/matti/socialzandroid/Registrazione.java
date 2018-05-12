package com.example.matti.socialzandroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import risposta.Risposta;

public class Registrazione extends Fragment {

    private static EditText nome,cognome,indirizzo,telefono,email, password,confPassword, nascita;
    private static RadioGroup sesso,privacy;
    private Button submit,reset;
    private TextView genderR,privacyR;
    DatePickerDialog.OnDateSetListener date;
    static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registrazione, container, false);
        email = view.findViewById(R.id.email_sign_in);
        password = view.findViewById(R.id.password_sign_in);
        nascita = view.findViewById(R.id.nascita_sign_in);
        nome = view.findViewById(R.id.nome_sign_in);
        cognome = view.findViewById(R.id.cognome_sign_in);
        indirizzo = view.findViewById(R.id.indirizzo_sign_in);
        telefono = view.findViewById(R.id.telefono_sign_in);
        confPassword = view.findViewById(R.id.password_confirm_sign_in);
        sesso= view.findViewById(R.id.gender_sign_in);
        privacy= view.findViewById(R.id.privacy_sign_in);
        submit= view.findViewById(R.id.submit);
        reset= view.findViewById(R.id.reset);
        privacyR= view.findViewById(R.id.privacy_required);
        genderR= view.findViewById(R.id.gender_required);
        context= getActivity().getApplicationContext();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Calendar c = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                nascita.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(c.getTime()));
            }
        };
        nascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nascita.setError(null);
                new DatePickerDialog(context, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(controlloCampi())
                    sendJson();
            }
        });
        privacy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                privacyR.setVisibility(View.INVISIBLE);
            }
        });
        sesso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                genderR.setVisibility(View.INVISIBLE);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    private boolean controlloCampi(){
        boolean ris=true;
        if(nome.length()<=0){
            nome.setError(getString(R.string.error_field_required));
            ris=false;
        }
        if(cognome.length()<=0){
            cognome.setError(getString(R.string.error_field_required));
            ris=false;
        }
        if(nascita.length()<=0){
            nascita.setError(getString(R.string.error_field_required));
            ris=false;
        }
        if(email.length()<=0){
            email.setError(getString(R.string.error_field_required));
            ris=false;
        }else if(!email.getText().toString().contains("@")){
            email.setError(getString(R.string.error_invalid_email));
            ris=false;
        }
        if(password.length()<=0){
            password.setError(getString(R.string.error_field_required));
            ris=false;
        }else if(password.length()<6){
            password.setError(getString(R.string.error_invalid_password));
            ris=false;
        }
        if(!confPassword.getText().toString().equals(password.getText().toString())){
            confPassword.setError(getString(R.string.error_incorrect_password));
            ris=false;
        }
        if(sesso.getCheckedRadioButtonId()<0){
            genderR.setVisibility(View.VISIBLE);
            ris=false;
        }
        if(privacy.getCheckedRadioButtonId()<0){
            privacyR.setVisibility(View.VISIBLE);
            ris=false;
        }
        return ris;
    }

    private boolean sendJson(){
        JSONObject ris= new JSONObject();
        try {
            ris.put("nome", nome.getText().toString());
            ris.put("cognome", cognome.getText().toString());
            ris.put("dataNascita", nascita.getText().toString());
            ris.put("email", email.getText().toString());
            ris.put("password", password.getText().toString());
            ris.put("sesso", sesso.getCheckedRadioButtonId()==R.id.male_sign_in?"M":"F");
            ris.put("privacy", privacy.getCheckedRadioButtonId()==R.id.agree_sign_in?1:0);
            ris.put("indirizzo", indirizzo.length()>0?indirizzo.getText().toString():"");
            ris.put("telefono", telefono.length()>0?telefono.getText().toString():"");
            new DoSignIn(ris.toString(),getActivity().getApplicationContext()).execute();
        }catch(JSONException e){}
        return false;
    }

    public static void showResponse(int ris){
        final String text=(ris== Risposta.OK?context.getString(R.string.success):
                     ris== Risposta.UNIQUE_FAIL?context.getString(R.string.error_already_registered):
                     context.getString(R.string.general_error)
        );
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.sign_in));
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(text.equals(context.getString(R.string.success)))
                            reset();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private static void reset(){
        nome.setText(null);
        cognome.setText(null);
        nascita.setText(null);
        telefono.setText(null);
        indirizzo.setText(null);
        email.setText(null);
        password.setText(null);
        confPassword.setText(null);
        privacy.check(-1);
        sesso.check(-1);
    }
}
