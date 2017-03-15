package com.example.andrew.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button sw = (Button)findViewById(R.id.myButton);
        sw.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 5);
            }
        });
        Button chat = (Button)findViewById(R.id.startChat);
        chat.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
        Button weather = (Button)findViewById(R.id.weatherButton);
        weather .setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.i(ACTIVITY_NAME, "User clicked weather forecast");
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 5) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");//   super.onDestory();

            Toast toast = Toast.makeText(this , messagePassed, Toast.LENGTH_LONG); //this is the ListActivity
            toast.show(); //display your message box

        }
    }

    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
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
        Log.i(ACTIVITY_NAME, "In onStart()");

     //   super.onDestory();
    }

}
