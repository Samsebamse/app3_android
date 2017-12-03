package com.example.sami.mappe3;


import java.util.ArrayList;


public class CalculateSavings {

    private long quitDateInMillis;
    private long consumerConsumption;
    private long tobaccoPrice;

    private long moneySaved;
    private long tobaccoSaved;
    private long healthSaved;

    private ArrayList<Boolean> healthApproach;


    public CalculateSavings(long quitDateInMillis) {
        this.quitDateInMillis = quitDateInMillis;

        healthApproach = new ArrayList<>();
    }

    public CalculateSavings(long quitDateInMillis, long consumerConsumption, long tobaccoPrice){
        this.quitDateInMillis = quitDateInMillis;
        this.consumerConsumption = consumerConsumption;
        this.tobaccoPrice = tobaccoPrice;

        healthApproach = new ArrayList<>();
    }
    public void refreshHealthAchievement(){

        healthApproach.clear();

        long durationInMillis = System.currentTimeMillis() - quitDateInMillis;

        long milliSecond = 1;
        long second = milliSecond * 1000;
        long minute = second * 60;
        long hour = minute * 60;
        long day = hour * 24;


        if(durationInMillis >= minute * 20){
            healthApproach.add(true);
        }
        if(durationInMillis >= hour * 8){
            healthApproach.add(true);
        }
        if(durationInMillis >= hour * 24){
            healthApproach.add(true);
        }
        if(durationInMillis >= hour * 48){
            healthApproach.add(true);
        }
        if(durationInMillis >= hour * 72){
            healthApproach.add(true);
        }
        if(durationInMillis >= day * 14){
            healthApproach.add(true);
        }
        if(durationInMillis >= day * 28){
            healthApproach.add(true);
        }
        if(durationInMillis >= day * 90){
            healthApproach.add(true);
        }
        if(durationInMillis >= day * 365){
            healthApproach.add(true);
        }

        healthSaved = healthApproach.size();
    }

    public void refreshConsumerSavings(){

        long durationInDays = (System.currentTimeMillis() - quitDateInMillis)/(1000*60*60*24);

        this.moneySaved = (consumerConsumption/7) * tobaccoPrice * durationInDays;
        this.tobaccoSaved = (consumerConsumption/7) *  durationInDays;

    }

    public long getMoneySaved(){
        return moneySaved;
    }
    public long getTobaccoSaved(){
        return tobaccoSaved;
    }
    public long getHealthSaved(){
        return healthSaved;
    }
    public ArrayList<Boolean> getHealthApproach(){
        return healthApproach;
    }

}
