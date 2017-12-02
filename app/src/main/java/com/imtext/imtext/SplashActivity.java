package com.imtext.imtext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.vision.text.TextRecognizer;

/**
 * Created by Gaurav Agarwal on 02-12-2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
        while (!textRecognizer.isOperational()) {
        }
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
