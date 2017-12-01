package com.example.sami.mappe3;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private LinearLayout linearLayout;
    private Button createUser;

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

        createUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        userQuitDate = findViewById(R.id.userinput_quitdate);
                        userConsumption = findViewById(R.id.userinput_consumption);
                        userPrice = findViewById(R.id.userinput_price);

                        String quit = userQuitDate.getText().toString();
                        String consume = userConsumption.getText().toString();
                        String price = userPrice.getText().toString();

                        SharedPreferences.Editor editor = profile.edit();
                        editor.putString("quitdate", quit);
                        editor.putString("consumption", consume);
                        editor.putString("price", price);
                        editor.commit();

                        isProfileCreated();

            }
        });

    }

    private void isProfileCreated() {

        profile = PreferenceManager.getDefaultSharedPreferences(this);
        String stopDate = profile.getString("quitdate", null);
        String consume = profile.getString("consumption", null);
        String price = profile.getString("price", null);

        if(stopDate != null || consume != null || price != null){
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
                        Toast.makeText(getApplicationContext(), "MY PROFILE VIEW", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        currentFragment = new ProfileFragment();
                        break;
                    case R.id.view_motivation:
                        Toast.makeText(getApplicationContext(), "MOTIVATION VIEW", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        currentFragment = new MotivationFragment();

                        break;
                    case R.id.view_achievement:
                        Toast.makeText(getApplicationContext(), "ACHIEVEMENT VIEW", Toast.LENGTH_SHORT).show();
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

