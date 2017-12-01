package com.example.sami.mappe3;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class ProfileFragment extends Fragment {

    private SharedPreferences profile;
    private TextView displayCurrentDate, displayDays, displayHours, displayMinutes, displaySeconds;

    private long quitDateInMillis;
    private Handler handler;
    private Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile = PreferenceManager.getDefaultSharedPreferences(getActivity());
        displayCurrentDate = (TextView) view.findViewById(R.id.view_duration);
        displayDays = (TextView) view.findViewById(R.id.view_days);
        displayHours = (TextView) view.findViewById(R.id.view_hours);
        displayMinutes = (TextView) view.findViewById(R.id.view_minutes);
        displaySeconds = (TextView) view.findViewById(R.id.view_seconds);

        quitDateInMillis = profile.getLong("quitdate", 0);

    }


    @Override
    public void onResume() {
        displayDuration();
        everySecondCall();
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private void everySecondCall() {

        handler = new Handler();
        final int delay = 1000; // 1000 milliseconds = 1 second
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                displayDuration();
                handler.postDelayed(this, delay);
            }}, delay);
    }


    private void displayDuration() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        displayCurrentDate.setText(currentDateTimeString);

        long diff = System.currentTimeMillis() - quitDateInMillis;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(diff);

        long days = calendar.get(Calendar.DAY_OF_YEAR);
        long hours = calendar.get(Calendar.HOUR_OF_DAY);
        long minutes = calendar.get(Calendar.MINUTE);
        long seconds = calendar.get(Calendar.SECOND);

        displayDays.setText(String.valueOf(days));
        displayHours.setText(String.valueOf(hours));
        displayMinutes.setText(String.valueOf(minutes));
        displaySeconds.setText(String.valueOf(seconds));

        System.out.println("TRÅDEN KJØRER");
    }
}
