package com.example.genji.am012_fragment;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Random;

/* see
 *
 * http://developer.android.com/training/animation/cardflip.html
 * http://developer.android.com/guide/topics/resources/animation-resource.html
 *
 */

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, RecognitionListener {

    private GestureDetectorCompat mDetector;
    private SpeechRecognizer voice;
    private Intent intent;
    private boolean isListening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch listening = findViewById(R.id.listen);
        isListening=false;
        mDetector = new GestureDetectorCompat(this, this);
        try {
            voice = SpeechRecognizer.createSpeechRecognizer(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        startListening();
        voice.setRecognitionListener(this);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, Fragment1.newInstance())
                .commit();
        listening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isListening=b;
                startListening();
            }
        });
    }

    private void changeFragment(int in, int out) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(in, out)
                .replace(R.id.fragment, Dado.newInstance(new Random().nextInt(6) + 1))
                .commit();
        startListening();
    }

    private void startListening() {
        if(isListening)voice.startListening(intent);
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int i) {
        //Log.d("error", "" + i);
        if (i == SpeechRecognizer.ERROR_NO_MATCH) {
            startListening();
        } else if (i != SpeechRecognizer.ERROR_CLIENT && i != SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.linearLayout), getResources().getString(R.string.audio_error), Snackbar.LENGTH_LONG);
            snackbar.setDuration(Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (data != null) {
            String ris = data.get(0).toLowerCase();
            if (ris.contains("lancia") || ris.contains("tira") || ris.contains("dado") || ris.contains("gira")) {
                changeFragment(R.animator.fade_in, R.animator.fade_out);
            } else {
                startListening();
            }
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {
    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        voice.startListening(intent);
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        voice.stopListening();
        voice.cancel();
        voice.destroy();
        finish();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        float angle = (float) Math.toDegrees(Math.atan2(e1.getY() - e2.getY(), e2.getX() - e1.getX()));
        //Log.d("SCROLL: ", "x " + v + "  y  " + v1 + " e1 " + e1.getX() + " e2 " + e2.getY() + " angolo " + angle);
        if (angle > -45 && angle <= 45) {
            changeFragment(R.animator.flip1_dx, R.animator.flip2_dx);
            return true;
        }

        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
            changeFragment(R.animator.flip1_sx, R.animator.flip2_sx);
            return true;
        }

        if (angle < -45 && angle >= -135) {
            changeFragment(R.animator.flip1_down, R.animator.flip2_down);
            return true;
        }

        if (angle > 45 && angle <= 135) {
            changeFragment(R.animator.flip1_up, R.animator.flip2_up);
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        voice.stopListening();
        voice.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        voice.setRecognitionListener(this);
        startListening();
    }
}
