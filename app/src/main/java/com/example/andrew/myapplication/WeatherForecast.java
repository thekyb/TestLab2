package com.example.andrew.myapplication; import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherForecast extends AppCompatActivity { /** ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more information. */

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        /* ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more information.*/ 
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } /** ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more information. */
    public Action getIndexApiAction() { Thing object = new Thing.Builder().setName("WeatherForecast Page") 
        .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]")).build();
        return new Action.Builder(Action.TYPE_VIEW).setObject(object).setActionStatus(Action.STATUS_TYPE_COMPLETED).build();
    } 
    @Override public void onStart() { super.onStart(); 
        /* ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more information.*/ 
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
        new ForecastQuery().execute();
    } 
    @Override public void onStop() 
    { 
        super.onStop();
        /* ATTENTION: This was auto-generated to implement the App Indexing API. See https://g.co/AppIndexing/AndroidStudio for more information.*/
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    class ForecastQuery extends AsyncTask<Object, Object, InputStream> {
        private String minTem;
        private String maxTem;
        private String currentTem;
        private Bitmap pictureTem;

        @Override
        protected InputStream doInBackground(Object... params) {
            try {
                String weathercasturl = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
                URL url = new URL(weathercasturl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                parse(conn.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        List parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

                parser.nextTag();
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    if (name.equals("temperature")) {
                        currentTem = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTem = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxTem = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (name.equals("weather")) {
                        String iconfile = parser.getAttributeValue(null, "icon") + ".png";

                        if (fileExistence(iconfile)) {
                            pictureTem = HttpUtils.getImage("http://openweathermap.org/img/w/" + iconfile);
                            FileOutputStream outputStream = openFileOutput(iconfile, Context.MODE_PRIVATE);
                            pictureTem.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                        FileInputStream fis;
                        try {
                            fis = new FileInputStream(getBaseContext().getFileStreamPath(iconfile));
                            pictureTem = BitmapFactory.decodeStream(fis);
                            Log.i(ACTIVITY_NAME, "image from local directory : "+ iconfile);
                            publishProgress(100);
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        boolean fileExistence(String fName){
            File file = getBaseContext().getFileStreamPath(fName);
            return file.exists();
        }

        protected void onProgressUpdate(Object... progress) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(InputStream result) {
            ((TextView) findViewById(R.id.minTem)).setText(getMinTem());
            ((TextView) findViewById(R.id.maxTem)).setText(getMaxTem());
            ((TextView) findViewById(R.id.currentTem)).setText(getCurrentTem());
            ((ImageView) findViewById(R.id.weatherImage)).setImageBitmap(getPictureTem());
        }

        String getMinTem() {
            return minTem;
        }
        String getMaxTem() {
            return maxTem;
        }
        String getCurrentTem() {
            return currentTem;
        }
        Bitmap getPictureTem() {
            return pictureTem;
        }
    }

}
