package com.example.andrew.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button b1 = (Button) findViewById(R.id.button2);
//        Button sw = (Button)findViewById(R.id.switch1);

    }
}
