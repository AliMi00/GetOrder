package com.example.getorder.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.getorder.R;
import com.example.getorder.model.OrderStatus;
import com.example.getorder.view.fragment.OrderDailyReportFragment;
import com.example.getorder.view.fragment.ProductsFragment;
import com.example.getorder.view.fragment.SetOrderFragment;
import com.example.getorder.view.fragment.WaitingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add dummy data to db
//        ProductService productService = new ProductService(getApplication());
//        productService.insert(new Product("p1","dec1",25,10,0));
//        productService.insert(new Product("p2","dec2",35,20,0));
//        productService.insert(new Product("p3","dec3",45,30,0));
//
//        Order order = new Order(2,25,20,5,OrderStatus.OPEN.ordinal(),20200101);
//        List<OrderDetails> od = new ArrayList<>();
//        od.add(new OrderDetails(-1,5,"",25,20,1,0));
//        OrdersServices ordersServices = new OrdersServices(getApplication());
//        ordersServices.alitest(order);



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
                    Toast.makeText(MainActivity.this,""+ String.valueOf(OrderStatus.NEW.ordinal()), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProductsFragment()).commit();
                    break;
                case R.id.nav_order:
                    Toast.makeText(MainActivity.this, String.valueOf(OrderStatus.NEW.ordinal()), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SetOrderFragment.newInstance(0)).commit();
                    break;
                case R.id.nav_waiting:
                    Toast.makeText(MainActivity.this, "fra3", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WaitingFragment()).commit();
                    break;
                case R.id.nav_report_day:
                    //todo add report day activity and navigate to it
                    Toast.makeText(MainActivity.this, "fra4", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, OrderDailyReportFragment.newInstance()).commit();
                    break;
                case R.id.nav_report_month:
                    //todo add report day activity and navigate to it
                    Toast.makeText(MainActivity.this, "fra5", Toast.LENGTH_SHORT).show();
                    break;


            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };
}
