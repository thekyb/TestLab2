package com.example.andrew.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   setContentView(R.layout.activity_listitem);
        ib = (ImageButton)findViewById(R.id.imageButton);
        Log.i("ListItemsActivity", "tttttt"+ ib.getParent().toString());*/

        setContentView(R.layout.activity_listitem2);
        CheckBox cb = (CheckBox)findViewById(R.id.cb);
        Log.i("ListItemsActivity", "tttttt check "+ cb.getParent().toString());

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ib.setImageBitmap(imageBitmap);
        }
    }
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
        ImageButton ib = (ImageButton)findViewById(R.id.imageButton);
        ib.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });
        Switch sw = (Switch)findViewById(R.id.myswitch);
        sw.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text = "Switch is On";// "Switch is Off"
                int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

                if(!isChecked){
                    text = "Switch is Off";// "Switch is Off"
                    duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
                }
                Toast toast = Toast.makeText(ListItemsActivity.this, text, duration); //this is the ListActivity
                toast.show(); //display your message box
            }
        });

        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response", "ListItemsActivity passed: My information to share");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
//                                Intent intent = new Intent(ListItemsActivity.this, StartActivity.class);
//                                intent.putExtra("Response", "My information to share");
//                                setResult(Activity.RESULT_OK, intent);
//                                finish();

                                // User clicked OK button
                            }
                        })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();
            }
        });
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

        //super.onDestory();
    }

}

