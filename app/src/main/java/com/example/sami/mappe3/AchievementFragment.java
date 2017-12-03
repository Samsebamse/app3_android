package com.example.sami.mappe3;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AchievementFragment extends Fragment {

    private List<Achievement> listAchievements;
    private ArrayAdapter<Achievement> adapter;
    private ListView listViewAchievements;

    private CalculateSavings calculateSavings;
    private ArrayList<Boolean> checkAchievement;

    private long quitDateInMillis;
    private Handler everyTenMinute;
    private Runnable runnable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.achievement));
        SharedPreferences profile = PreferenceManager.getDefaultSharedPreferences(getActivity());
        quitDateInMillis = profile.getLong("quitdate", 0);

        calculateSavings = new CalculateSavings(quitDateInMillis);

        listAchievements = new ArrayList<>();
        listViewAchievements = (ListView) view.findViewById(R.id.listview_achievements);

        fillDataInList();
    }

    @Override
    public void onResume() {
        calculateSavings.refreshHealthAchievement();
        checkAchievement = calculateSavings.getHealthApproach();
        displayListView();
        everySecondCall();
        super.onResume();
    }

    @Override
    public void onPause() {
        everyTenMinute.removeCallbacks(runnable);
        super.onPause();
    }

    public void fillDataInList() {

        Achievement achievement1 = new Achievement("20 minutter: ", "Pulsen går ned", R.drawable.icon_trophy1);
        Achievement achievement2 = new Achievement("8 timer: ", "Blodsirkulasjonen bedres", R.drawable.icon_trophy2);
        Achievement achievement3 = new Achievement("24 timer: ", "Risikoen for hjerteinfarkt synker allerede", R.drawable.icon_trophy3);
        Achievement achievement4 = new Achievement("48 timer: ", "Smak og luktesans bedres", R.drawable.icon_trophy4);
        Achievement achievement5 = new Achievement("72 timer: ", "Lungekapasiteten øker og kroppen er nikotinfri", R.drawable.icon_trophy5);
        Achievement achievement6 = new Achievement("2 uker: ", "Det kjennes lettere å bevege seg raskt", R.drawable.icon_trophy6);
        Achievement achievement7 = new Achievement("3 uker: ", "Øker sjansen fem ganger til å slutte å røyke for godt", R.drawable.icon_trophy7);
        Achievement achievement8 = new Achievement("3 måneder : ", "Mindre utsatt for luftveisinfeksjoner", R.drawable.icon_trophy8);
        Achievement achievement9 = new Achievement("1 år: ", "Immunforsvaret styrkes", R.drawable.icon_trophy9);

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

        everyTenMinute = new Handler();
        final int delay = 1000*10; // 10 minutes
        everyTenMinute.postDelayed(runnable = new Runnable() {
            public void run() {
                calculateSavings.refreshHealthAchievement();
                checkAchievement = calculateSavings.getHealthApproach();
                adapter.notifyDataSetChanged();
                everyTenMinute.postDelayed(this, delay);
            }}, delay);
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
                itemView.setBackgroundResource(R.color.risks);
            }

            Achievement currentAchievement = listAchievements.get(position);

            TextView displayDate = itemView.findViewById(R.id.view_date);
            displayDate.setText(currentAchievement.getDate());

            TextView displayDescription = itemView.findViewById(R.id.view_description);
            displayDescription.setText(currentAchievement.getDescription());

            ImageView displayTrophy = itemView.findViewById(R.id.view_trophy_icon);
            displayTrophy.setBackgroundResource(currentAchievement.getResourceId());

            for(int i = 0; i < checkAchievement.size(); i++){
                if (position == i && checkAchievement.get(i) == true){
                    itemView.setBackgroundResource(R.color.verified);
                }
            }

            return itemView;

        }
    }
}
