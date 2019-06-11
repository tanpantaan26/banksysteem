package com.example.banksysteem.Controller;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.banksysteem.R;

/**
 * @author Inge
 */
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private String gebruikersnaam;
    private String wachtwoord;

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
                    Bundle gebruikerIngelogd = new Bundle();
                    gebruikerIngelogd.putString("Gebruikersnaam", gebruikersnaam);
                    gebruikerIngelogd.putString("Wachtwoord", wachtwoord);
                    MeerFragment meerFragment = new MeerFragment();
                    meerFragment.setArguments(gebruikerIngelogd);
                    manager.beginTransaction().replace(R.id.main_fragment_container, meerFragment).commit();
                    break;
                case R.id.nav_profile:
                    manager.beginTransaction().replace(R.id.main_fragment_container, new ProfielFragment()).commit();
                    break;
                case R.id.nav_transaction:
                    manager.beginTransaction().replace(R.id.main_fragment_container, new BoekingFragment()).commit();
                    break;
            }
            return true;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        gebruikersnaam = extras.getString("Gebruikersnaam");
        wachtwoord = extras.getString("Wachtwoord");


        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new OverzichtFragment()).commit();


    }

}
