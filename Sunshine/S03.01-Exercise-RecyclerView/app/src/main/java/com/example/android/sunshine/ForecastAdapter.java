package com.example.android.sunshine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by abstr on 1/27/2018.
 */

public class ForecastAdapter
        extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private String[] weatherData;

    public ForecastAdapter(){}

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.forecast_list_item,parent,false);

        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        holder.weatherTextView.setText(weatherData[position]);

    }

    @Override
    public int getItemCount() {

        if(weatherData == null)
            return 0;
        else
            return weatherData.length;
    }

    public void setWeatherData(String[] weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }

    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder{

        public final TextView weatherTextView;

        public ForecastAdapterViewHolder (View view){
            super(view);
            weatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);
        }


    }

}
