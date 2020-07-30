package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class NavigateActivity extends AppCompatActivity{

        Button current;
        Button tomorrow;
        private TextToSpeech textToSpeach;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_navigate);
            current = (Button)findViewById(R.id.current);
            tomorrow = (Button)findViewById(R.id.tomo);

            textToSpeach = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int text = textToSpeach.setLanguage(Locale.UK);
                        speak("Welcome to weather forecast app");
                    } }});

            current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(NavigateActivity.this, HomeActivity.class);
                    startActivity(i);
                }
            });
            tomorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(NavigateActivity.this, LocationActivity.class);
                    startActivity(i);
                }
            });
        }
    private void speak(String text){
        textToSpeach.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
