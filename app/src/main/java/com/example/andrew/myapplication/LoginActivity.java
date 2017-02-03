package com.example.andrew.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");

        SharedPreferences prefs = this.getSharedPreferences("DefaultInfos", Context.MODE_PRIVATE);
        EditText loginName = (EditText)findViewById(R.id.loginname);

        loginName.setText(prefs.getString("DefaultEmail", "email@domain.com"));
        Button b1 = (Button) findViewById(R.id.loginbutton);
        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText loginName = (EditText)findViewById(R.id.loginname);
                SharedPreferences prefs = getSharedPreferences("DefaultInfos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("DefaultEmail", loginName.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        super.onStart();
    }
    protected void onResume(){
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }
    protected void onPause(){
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }
    protected void onStop(){
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }
    protected void onDestory(){
        Log.i(ACTIVITY_NAME, "In onDestory()");
        super.onDestroy();
    }

}
