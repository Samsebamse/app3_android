package com.example.sami.mappe3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AchievementFragment extends Fragment {

    private List<Achievement> listAchievements;
    private ArrayAdapter<Achievement> adapter;
    private ListView listViewAchievements;
    private ArrayList<Boolean> check;

    private long quitDateInMillis;
    private Handler handler;
    private Runnable runnable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        check = new ArrayList<>();
        getActivity().setTitle("Achievement");
        listAchievements = new ArrayList<>();
        listViewAchievements = (ListView) view.findViewById(R.id.listview_achievements);

        SharedPreferences profile = PreferenceManager.getDefaultSharedPreferences(getActivity());
        quitDateInMillis = profile.getLong("quitdate", 0);


        fillDataInList();
    }

    @Override
    public void onResume() {
        checkForCompleteAchievement();
        displayListView();
        everySecondCall();
        displayListView();
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private void fillDataInList() {

        Achievement achievement1 = new Achievement("20 minutter: ", "Pulsen går ned", 0);
        Achievement achievement2 = new Achievement("8 timer: ", "Blodsirkulasjonen bedres", 0);
        Achievement achievement3 = new Achievement("24 timer: ", "Risikoen for hjerteinfarkt synker allerede", 0);
        Achievement achievement4 = new Achievement("48 timer: ", "Smak og luktesans bedres", 0);
        Achievement achievement5 = new Achievement("72 timer: ", "Lungekapasiteten øker og kroppen er nikotinfri", 0);
        Achievement achievement6 = new Achievement("2 uker: ", "Det kjennes lettere å bevege seg raskt", 0);
        Achievement achievement7 = new Achievement("3 uker: ", "Øker sjansen fem ganger til å slutte å røyke for godt", 0);
        Achievement achievement8 = new Achievement("3 måneder : ", "Mindre utsatt for luftveisinfeksjoner", 0);
        Achievement achievement9 = new Achievement("1 år: ", "Immunforsvaret styrkes", 0);

        listAchievements.add(achievement1);
        listAchievements.add(achievement2);
        listAchievements.add(achievement3);
        listAchievements.add(achievement4);
        listAchievements.add(achievement5);
        listAchievements.add(achievement6);
        listAchievements.add(achievement7);
        listAchievements.add(achievement8);
        listAchievements.add(achievement9);
    }

    public void displayListView() {
        adapter = new MyListAdapter();
        listViewAchievements.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewAchievements.setItemsCanFocus(false);
        listViewAchievements.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void everySecondCall() {

        handler = new Handler();
        final int delay = 1000*10; // 10 minutes
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                checkForCompleteAchievement();
                adapter.notifyDataSetChanged();
                handler.postDelayed(this, delay);
            }}, delay);
    }

    private void checkForCompleteAchievement(){

        check.clear();

        long milliSecond = 1;
        long second = milliSecond * 1000;
        long minute = second * 60;
        long hour = minute * 60;
        long day = hour * 24;

        long diff = (System.currentTimeMillis() - quitDateInMillis);


        if(diff >= minute * 20){
            check.add(true);
        }
        if(diff >= hour * 8){
            check.add(true);
        }
        if(diff >= hour * 24){
            check.add(true);
        }
        if(diff >= hour * 48){
            check.add(true);
        }
        if(diff >= hour * 72){
            check.add(true);
        }
        if(diff >= day * 14){
            check.add(true);
        }
        if(diff >= day * 28){
            check.add(true);
        }
        if(diff >= day * 90){
            check.add(true);
        }
        if(diff >= day * 365){
            check.add(true);
        }

    }

    private class MyListAdapter extends ArrayAdapter<Achievement> {

        public MyListAdapter() {
            super(getActivity(), R.layout.achievement_item, listAchievements);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.achievement_item, parent, false);
            }

            Achievement currentAchievement = listAchievements.get(position);

            TextView displayDate = itemView.findViewById(R.id.view_date);
            displayDate.setText(currentAchievement.getDate());

            TextView displayDescription = itemView.findViewById(R.id.view_description);
            displayDescription.setText(currentAchievement.getDescription());

            for(int i = 0; i < check.size(); i++){
                if (position == i && check.get(i) == true){
                    itemView.setBackgroundResource(R.color.verified);
                }
            }

            return itemView;

        }
    }
}
