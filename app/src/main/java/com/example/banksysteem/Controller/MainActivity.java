package com.example.banksysteem.Controller;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.banksysteem.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    //functie om menu item klik actie
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager manager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.nav_home:
                    manager.beginTransaction().replace(R.id.main_fragment_container, new OverzichtFragment()).commit();
                    break;
                case R.id.nav_more:
                    manager.beginTransaction().replace(R.id.main_fragment_container, new MeerFragment()).commit();
                    break;
                case R.id.nav_profile:
                    manager.beginTransaction().replace(R.id.main_fragment_container, new ProfielFragment()).commit();
                    break;
            }
            return true;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new OverzichtFragment()).commit();


    }


}
