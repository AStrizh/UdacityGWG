/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        loadWeatherData();
    }


    public void loadWeatherData(){

        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        URL weatherURL = NetworkUtils.buildUrl(location);
        new FetchWeatherTask().execute(weatherURL);
    }

    public class FetchWeatherTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherURL = urls[0];
            String jsonWeatherResponse = null;

            try {
                jsonWeatherResponse  = NetworkUtils.getResponseFromHttpUrl(weatherURL);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                              .getSimpleWeatherStringsFromJson(
                              MainActivity.this, jsonWeatherResponse);

                StringBuilder builder = new StringBuilder();
                for(String weatherString : simpleJsonWeatherData )
                    builder.append((weatherString) + "\n\n\n");

                jsonWeatherResponse = builder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonWeatherResponse ;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("") ){
                mWeatherTextView.setText(s);
            }
        }
    }


}