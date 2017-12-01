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
import java.util.Date;


public class ProfileFragment extends Fragment {

    SharedPreferences profile;
    TextView displayTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile = PreferenceManager.getDefaultSharedPreferences(getActivity());
        displayTime = (TextView) view.findViewById(R.id.view_duration);

        displayDuration();
        everySecondCall();

    }

    private void everySecondCall() {

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                displayDuration();
                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    private void displayDuration() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        displayTime.setText(currentDateTimeString);
    }
}
