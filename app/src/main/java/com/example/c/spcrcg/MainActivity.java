package com.example.c.spcrcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOGTAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView)findViewById(R.id.textView);

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());

        final SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Context context = this;

        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(context, "onReadyForSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginningOfSpeech() {
                Toast.makeText(context, "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                Toast.makeText(context, "onEndOfSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error) {
                switch(error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        Toast.makeText(context, "ERROR_AUDIO", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        Toast.makeText(context, "ERROR_CLIENT", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        Toast.makeText(context, "ERROR_INSUFFICIENT_PERMISSIONS", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        Toast.makeText(context, "ERROR_NETWORK", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        Toast.makeText(context, "ERROR_NETWORK_TIMEOUT", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        Toast.makeText(context, "ERROR_NO_MATCH", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        Toast.makeText(context, "ERROR_RECOGNIZER_BUSY", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        Toast.makeText(context, "ERROR_SERVER", Toast.LENGTH_LONG).show();
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        Toast.makeText(context, "ERROR_SPEECH_TIMEOUT", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, "unknown error", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onResults(Bundle results) {
                List<String> recData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String getData = new String();
                for(Iterator<String> it = recData.iterator(); it.hasNext(); ){
                    getData += it.next();
                    if(it.hasNext()) getData += ", ";
                }
                text.setText(text.getText() + "\n" + getData);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                List<String> recData = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String getData = "PartialResults: ";
                for(Iterator<String> it = recData.iterator(); it.hasNext(); ){
                    getData += it.next();
                    if(it.hasNext()) getData += ", ";
                }
                text.setText(text.getText() + "\n" + getData);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        final Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v == button){
                    recognizer.startListening(intent);
                }
            }
        });
    }
}
