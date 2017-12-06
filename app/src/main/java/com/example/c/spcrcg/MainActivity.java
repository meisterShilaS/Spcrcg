package com.example.c.spcrcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOGTAG = MainActivity.class.getSimpleName();
    private TextView text;

    private void writeText(String s){
        text.setText(text.getText() + "\n" + s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.textView);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        final SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Context context = this;

        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                writeText("onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                writeText("onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                writeText("onEndOfSpeech");
            }

            // https://qiita.com/CST_negi/items/aac8337b4748a658473f

            @Override
            public void onError(int error) {
                switch(error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        writeText("ERROR_AUDIO");
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        writeText("ERROR_CLIENT");
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        writeText("ERROR_INSUFFICIENT_PERMISSIONS");
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        writeText("ERROR_NETWORK");
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        writeText("ERROR_NETWORK_TIMEOUT");
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        writeText("ERROR_NO_MATCH");
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        writeText("ERROR_RECOGNIZER_BUSY");
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        writeText("ERROR_SERVER");
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        writeText("ERROR_SPEECH_TIMEOUT");
                        break;
                    default:
                        writeText("unknown error");
                        break;
                }
            }

            @Override
            public void onResults(Bundle results) {
                List<String> recData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String getData = "Results: ";
                for(Iterator<String> it = recData.iterator(); it.hasNext(); ){
                    getData += it.next();
                    if(it.hasNext()) getData += ", ";
                }
                writeText(getData);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                List<String> recData = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String getData = "PartialResults: ";
                for(Iterator<String> it = recData.iterator(); it.hasNext(); ){
                    getData += it.next();
                    if(it.hasNext()) getData += ", ";
                }
                writeText(getData);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        final Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v == button){
                    recognizer.startListening(intent);
                }
            }
        });

        final Button stopButton = (Button)findViewById(R.id.button);
        stopButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v == stopButton){
                    recognizer.stopListening();
                }
            }
        });
    }
}
