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

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProfileFragment extends Fragment {

    private SharedPreferences profile;
    private TextView displayDays, displayHours, displayMinutes, displaySeconds;
    private TextView savedMoney, savedTobacco, savedTrophy;

    private long quitDateInMillis;
    private Handler everySecond;
    private Runnable runnableSecond;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.my_profile));

        profile = PreferenceManager.getDefaultSharedPreferences(getActivity());
        quitDateInMillis = profile.getLong("quitdate", 0);

        displayDays = (TextView) view.findViewById(R.id.view_days);
        displayHours = (TextView) view.findViewById(R.id.view_hours);
        displayMinutes = (TextView) view.findViewById(R.id.view_minutes);
        displaySeconds = (TextView) view.findViewById(R.id.view_seconds);

        savedMoney = (TextView) view.findViewById(R.id.saved_money);
        savedTobacco = (TextView) view.findViewById(R.id.saved_tobacco);
        savedTrophy = (TextView) view.findViewById(R.id.saved_trophy);

    }


    @Override
    public void onResume() {
        displaySavings();
        displayDuration();
        everySecondCall();
        super.onResume();
    }

    @Override
    public void onPause() {
        everySecond.removeCallbacks(runnableSecond);
        super.onPause();
    }

    private void everySecondCall() {

        everySecond = new Handler();
        final int delay = 1000; // Every second
        everySecond.postDelayed(runnableSecond = new Runnable() {
            public void run() {
                displayDuration();
                everySecond.postDelayed(this, delay);
            }}, delay);
    }

    public void displayDuration() {

        long diff = System.currentTimeMillis() - quitDateInMillis;
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(diff);

        long days = calendar.get(Calendar.DAY_OF_YEAR)-1;
        long hours = calendar.get(Calendar.HOUR_OF_DAY);
        long minutes = calendar.get(Calendar.MINUTE);
        long seconds = calendar.get(Calendar.SECOND);

        String textDays = String.valueOf(days) + "\n" + getString(R.string.days);
        String textHours = String.valueOf(hours) + "\n" + getString(R.string.hours);
        String textMinutes = String.valueOf(minutes) + "\n" + getString(R.string.minutes);
        String textSeconds = String.valueOf(seconds) + "\n" + getString(R.string.seconds);

        displayDays.setText(textDays);
        displayHours.setText(textHours);
        displayMinutes.setText(textMinutes);
        displaySeconds.setText(textSeconds);

    }

    public void displaySavings(){

        long tobaccoConsumed = profile.getLong("consumption", 0);
        long tobaccoPrice = profile.getLong("price", 0);

        CalculateSavings calculateSavings = new CalculateSavings(quitDateInMillis, tobaccoConsumed, tobaccoPrice);
        calculateSavings.refreshConsumerSavings();
        calculateSavings.refreshHealthAchievement();

        long moneySavings = calculateSavings.getMoneySaved();
        long tobaccoSavings = calculateSavings.getTobaccoSaved();
        long trophySavings = calculateSavings.getHealthSaved();

        String moneyText = String.valueOf(moneySavings + "\n" + getString(R.string.saved_money));
        String tobaccoText = String.valueOf(tobaccoSavings + "\n" + getString(R.string.saved_tobacco));
        String trophyText = String.valueOf(trophySavings + "\n" + getString(R.string.saved_trophy));

        savedMoney.setText(String.valueOf(moneyText));
        savedTobacco.setText(String.valueOf(tobaccoText));
        savedTrophy.setText(String.valueOf(trophyText));

    }
}
