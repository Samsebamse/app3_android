package com.example.sami.mappe3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class MotivationFragment extends Fragment {

    LinearLayout dreamGoalForm;
    LineChart lineChart;
    SharedPreferences profile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motivation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.motivation));

        dreamGoalForm = (LinearLayout) view.findViewById(R.id.form_dreamgoal);
        lineChart = (LineChart) view.findViewById(R.id.chart);

        isProfileCreated();



        addData();

    }

    public void addData(){
        int[][] dataObjects = {{1,2},{2,2},{3,1}};

        List<Entry> entries = new ArrayList<>();

        for (int [] data : dataObjects) {
            entries.add(new Entry(data[0], data[1]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh


    }
    public void isProfileCreated(){

        profile = PreferenceManager.getDefaultSharedPreferences(getActivity());
        long dreamgoal = profile.getLong("dreamgoalprice", 0);

        System.out.println(dreamgoal);

        if(dreamgoal != 0){
            lineChart.removeAllViewsInLayout();
        }
        else{
            dreamGoalForm.removeAllViewsInLayout();

        }

    }
}
