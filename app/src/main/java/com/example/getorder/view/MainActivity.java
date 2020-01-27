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
import com.example.getorder.model.Product;
import com.example.getorder.services.ProductService;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ProductService productService = new ProductService(getApplication());
//        productService.insert(new Product("p1","dec1",25,10,0));
//        productService.insert(new Product("p2","dec2",35,20,0));
//        productService.insert(new Product("p3","dec3",45,30,0));

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProductsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_product);
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
                    Toast.makeText(MainActivity.this, "fra1", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProductsFragment()).commit();
                    break;
                case R.id.nav_order:
                    Toast.makeText(MainActivity.this, "fra2", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SetOrderFragment()).commit();
                    break;
                case R.id.nav_waiting:
                    Toast.makeText(MainActivity.this, "fra3", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WatingFragment()).commit();
                    break;
                case R.id.nav_report_day:
                    Toast.makeText(MainActivity.this, "fra4", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_report_month:
                    Toast.makeText(MainActivity.this, "fra5", Toast.LENGTH_SHORT).show();
                    break;


            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };
}
