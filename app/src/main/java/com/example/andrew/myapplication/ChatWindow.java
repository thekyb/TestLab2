package com.example.andrew.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.andrew.myapplication.ChatDatabaseHelper.ACTIVITY_NAME;
import static com.example.andrew.myapplication.ChatDatabaseHelper.KEY_ID;
import static com.example.andrew.myapplication.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.andrew.myapplication.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends AppCompatActivity {

    EditText myMsg;
    Button sb;
    ListView msgView;
    ArrayList<String> listItems;
    ChatAdapter<String> adapter;
    SQLiteDatabase mydb = null;
    Integer rowCount = 0;

    private class ChatAdapter<String> extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return listItems.size();
        }

        public String getItem(int position){
            return (String)listItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position).toString()  ); // get the string at position
            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listItems = new ArrayList<>();


        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        mydb = dbHelper.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_MESSAGE};
        Cursor myCursor = mydb.query(false, TABLE_NAME, null, null, null, null, null, null, null);
        Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + myCursor.toString());
        if(myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                listItems.add(myCursor.getString(myCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + myCursor.getString(myCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                rowCount++;
                myCursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + myCursor.getColumnCount());
        }
        myMsg = (EditText) findViewById(R.id.myMsg);
        sb = (Button) findViewById(R.id.sendbutton);
        msgView = (ListView) findViewById(R.id.chatList);
        adapter = new ChatAdapter<>(this);
        msgView.setAdapter(adapter);
        sb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listItems.add(myMsg.getText().toString());
                adapter.notifyDataSetChanged(); //this restarts the process of getCount()/

                String[] columns = {KEY_ID, KEY_MESSAGE};
                String insertQuery ="INSERT INTO messages_t Values ("+ rowCount.toString() + ", '" + myMsg.getText().toString() +"');";
                rowCount++;
                Log.i(ACTIVITY_NAME, insertQuery);
                mydb.execSQL(insertQuery);
                Cursor myCursor = mydb.rawQuery("SELECT * FROM messages_t;", null);
                if(myCursor.moveToFirst()) {
                    while (!myCursor.isAfterLast()) {
                        Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + myCursor.getString(myCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                        myCursor.moveToNext();
                    }
                }
                myMsg.setText("");
            }
        });

    }
}
