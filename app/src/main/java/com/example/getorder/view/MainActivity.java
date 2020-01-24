package com.example.getorder.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.getorder.R;
import com.example.getorder.db.OrderDb;
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderStatus;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //nav drawer
        drawerLayout = findViewById(R.id.drawer_layout);

        //for click
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        //for 3 line on top
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //check if activity first start load home fragment
        if (savedInstanceState == null) {
            //todo correcting this part after creating fragments
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new MessageFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_message);
        }

    }

    @Override
    public void onBackPressed() {
        //to close navigation if navigation is open
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    //for item nav listener
    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_product:
                    Toast.makeText(MainActivity.this, "ali", Toast.LENGTH_SHORT).show();

                    //todo make the fragment and go there
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new classname()).commit();
                    break;

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };
}
