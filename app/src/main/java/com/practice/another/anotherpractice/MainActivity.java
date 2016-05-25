package com.practice.another.anotherpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //start onCreate ...
    //most of the code will be in here
    String  spinnerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText buyPrice = (EditText)findViewById(R.id.txtFieldBuy);
        final EditText unitQuantity = (EditText)findViewById(R.id.txtFieldShare);
        final EditText sellPrice = (EditText)findViewById(R.id.txtFieldSell);

    //make editText focusable when clicked..
        buyPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyPrice.setFocusableInTouchMode(true);
            }
        });

        unitQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitQuantity.setFocusableInTouchMode(true);
            }
        });

        sellPrice.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sellPrice.setFocusableInTouchMode(true);
            }
        });
        //default code for toolbar,floating actionbar and drawer..
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //spinner
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);

        //populating spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.brokerage,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //setlistner to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //parent.getItemAtPosition(position);
                if(position == 0){
                    spinnerValue = "MAYBANK";
                }else if(position == 1){
                    spinnerValue = "PUBLICBANK";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String buy = buyPrice.getText().toString();
                String unit = unitQuantity.getText().toString();
                String sell = sellPrice.getText().toString();
                boolean flag = false;
                int checkUnit = 0;

                //to avid invalid number format..
                if(unit.compareTo("") == 1){
                    checkUnit = Integer.parseInt(unit.trim());
                    checkUnit = checkUnit % 100;
                }


                if(buy.compareTo("") == 0 || unit.compareTo("") == 0 || sell.compareTo("") == 0){
                    Snackbar.make(view, "please fill all the information", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
                else{
                    if(checkUnit != 0){
                        Snackbar.make(view, "isi dalam gandaan seratus", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        Intent calc = new Intent("com.practice.another.anotherpractice.BrokerageCalculation");
                        calc.putExtra("buyPrice",buy);
                        calc.putExtra("unitNo",unit);
                        calc.putExtra("sellPrice",sell);
                        calc.putExtra("brokerName",spinnerValue);
                        startActivity(calc);
                    }

                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //ends of onCreate method


    //start of onBackPressed
    //detects what happen when back button pressed
    private static long back_pressed;
    @Override
    public void onBackPressed() {

        if (back_pressed + 200 > System.currentTimeMillis()){
            super.onBackPressed();

        }else{
            back_pressed = System.currentTimeMillis();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }else
            {
                super.onBackPressed();
            }
        }

    }
    //ends onBackPressed method


    //start onNavigationItemSelected
    //drawer of the apps
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calculator) {
            // Handle the camera action
            Log.d("myTrade drawer", "navigation selected : calculator");
            Intent openCalc = new Intent(this, MainActivity.class);
            startActivity(openCalc);

//        } else if (id == R.id.nav_journal) {
//            Intent openjournal = new Intent(this,JournalActivity.class);
//            startActivity(openjournal);
        } else if (id == R.id.nav_slideshow) {
            Intent openSearch = new Intent(this,SearchStock.class);
            startActivity(openSearch);
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //ends onNavigationItemSelected (drawer)
}
