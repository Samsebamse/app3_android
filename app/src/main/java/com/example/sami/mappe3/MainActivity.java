package com.example.sami.mappe3;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private LinearLayout linearLayout;
    private Button createUser;

    private DatePickerDialog.OnDateSetListener date;
    private Calendar calendar;
    private long quitDateInMillis;
    private int year, monthOfYear, dayOfMonth;

    EditText userQuitDate, userConsumption, userPrice;
    SharedPreferences profile;

    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createUser = (Button) findViewById(R.id.button_create_profile);
        userQuitDate = (EditText) findViewById(R.id.userinput_quitdate);
        userConsumption = (EditText) findViewById(R.id.userinput_consumption);
        userPrice = (EditText) findViewById(R.id.userinput_price);

        isProfileCreated();
    }

    private void setUpNavDrawer() {

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void createNewUser() {

        calendarHandler();

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long consume = Long.parseLong(userConsumption.getText().toString());
                long price = Long.parseLong(userPrice.getText().toString());

                Date date = new Date();
                calendar.set(year, monthOfYear, dayOfMonth, date.getHours(), date.getMinutes(), date.getSeconds());
                quitDateInMillis = calendar.getTimeInMillis();

                SharedPreferences.Editor editor = profile.edit();
                editor.putLong("quitdate", quitDateInMillis);
                editor.putLong("consumption", consume);
                editor.putLong("price", price);
                editor.commit();

                isProfileCreated();

            }
        });

    }

    private void calendarHandler() {

        calendar = Calendar.getInstance();
        userQuitDate.setInputType(InputType.TYPE_NULL);


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                MainActivity.this.monthOfYear = monthOfYear;
                MainActivity.this.dayOfMonth = dayOfMonth;
                userQuitDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR)));
            }
        };

        userQuitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void isProfileCreated() {

        profile = PreferenceManager.getDefaultSharedPreferences(this);
        long stopDate = profile.getLong("quitdate", 0);
        long consume = profile.getLong("consumption", 0);
        long price = profile.getLong("price", 0);

        if(stopDate != 0 || consume != 0 || price != 0){
            linearLayout = (LinearLayout) findViewById(R.id.view_signup);
            linearLayout.removeAllViewsInLayout();
            createUser.setVisibility(View.INVISIBLE);
            createUser.setEnabled(false);
            setUpNavDrawer();
            drawerClickHandler();
            fragmentHandler();
        }

        else{
            createNewUser();
        }


    }

    private void fragmentHandler() {
        if (currentFragment == null) {
            currentFragment = new ProfileFragment();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_area, currentFragment);
        fragmentTransaction.commit();

    }

    private void drawerClickHandler() {

        final NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.view_profile:
                        drawerLayout.closeDrawers();
                        currentFragment = new ProfileFragment();
                        break;
                    case R.id.view_motivation:
                        drawerLayout.closeDrawers();
                        currentFragment = new MotivationFragment();

                        break;
                    case R.id.view_achievement:
                        drawerLayout.closeDrawers();
                        currentFragment = new AchievementFragment();
                        break;
                    default:
                        break;

                }
                fragmentHandler();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

