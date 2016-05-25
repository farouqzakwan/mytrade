package com.practice.another.anotherpractice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import android.service.voice.VoiceInteractionSession;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.CandleEntry;
import com.parser.json.HistoricalPriceJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import fragment.FinancialRatioFragment;
import fragment.GraphFragment;

public class SearchStock extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

     String url = "http://farouqzakwan.com/historical.php?search=";
     String url2 = "http://farouqzakwan.com/financialratio.php?search=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stock);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button searchBtn = (Button)findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText keyword = (EditText)findViewById(R.id.edtSearch);String searchUrl = keyword.getText().toString();
                searchUrl=url+searchUrl;


                //use volley
                RequestQueue queue = Volley.newRequestQueue(SearchStock.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Toast.makeText(SearchStock.this, "search : "+response, Toast.LENGTH_SHORT).show();


                                HistoricalPriceJSON json = new HistoricalPriceJSON(response);
                                json.parseJSON();

                                //get the value and stored into the array...


                                String[] date = json.getDate();
                                float[] shadowBottom = json.getShadowBottom();
                                float[] shadowTop = json.getShadowTop();
                                float[] bodyBottom = json.getBodyBottom();
                                float[] bodyTop = json.getBodyTop();
                               // int[] volume = json.getVolume();
                                String[] color = json.getColor();


                                //create the graph.......
                                //boilerplate code...
                                ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();
                                ArrayList<String> labels = new ArrayList<String>();
                                //this in the loops from json...
                                //-----------------------------------high,---------low,--------open low,-----closed high
                                entries.add(new CandleEntry(0, shadowTop[9], shadowBottom[9], bodyBottom[9], bodyTop[9]));
                                entries.add(new CandleEntry(1, shadowTop[8], shadowBottom[8], bodyBottom[8], bodyTop[8]));
                                entries.add(new CandleEntry(2, shadowTop[7], shadowBottom[7], bodyBottom[7], bodyTop[7]));
                                entries.add(new CandleEntry(3, shadowTop[6], shadowBottom[6], bodyBottom[6], bodyTop[6]));
                                entries.add(new CandleEntry(4, shadowTop[5], shadowBottom[5], bodyBottom[5], bodyTop[5]));
                                entries.add(new CandleEntry(5, shadowTop[4], shadowBottom[4], bodyBottom[4], bodyTop[4]));
                                entries.add(new CandleEntry(6, shadowTop[3], shadowBottom[3], bodyBottom[3], bodyTop[3]));
                                entries.add(new CandleEntry(7, shadowTop[2], shadowBottom[2], bodyBottom[2], bodyTop[2]));
                                entries.add(new CandleEntry(8, shadowTop[1], shadowBottom[1], bodyBottom[1], bodyTop[1]));
                                entries.add(new CandleEntry(9, shadowTop[0], shadowBottom[0], bodyBottom[0], bodyTop[0]));

                                labels.add(date[9]);
                                labels.add(date[8]);
                                labels.add(date[7]);
                                labels.add(date[6]);
                                labels.add(date[5]);
                                labels.add(date[4]);
                                labels.add(date[3]);
                                labels.add(date[2]);
                                labels.add(date[1]);
                                labels.add(date[0]);


                                GraphFragment graph = new GraphFragment();
                                graph.populate(entries,labels);
                                FinancialRatioFragment ratio = new FinancialRatioFragment();
                                
                                //create the graph
                                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction fs = fm.beginTransaction();
                                FrameLayout flGraph = (FrameLayout)findViewById(R.id.fragmentPlaceholder);
                                if(flGraph != null){
                                    fs.replace(R.id.fragmentPlaceholder,graph);
                                }else{
                                    fs.add(R.id.fragmentPlaceholder,graph);
                                }

                                fs.commit();

                                //adding financial ratio
                                 android.support.v4.app.FragmentManager fm2 = getSupportFragmentManager();
                                FragmentTransaction fs2 = fm2.beginTransaction();
                                FrameLayout flRatio = (FrameLayout)findViewById(R.id.fragmentRatio);
                                if (flRatio != null){
                                    fs2.replace(R.id.fragmentRatio,ratio);
                                }else{
                                    fs2.add(R.id.fragmentRatio,ratio);
                                }
                                    fs2.commit();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchStock.this, "error : "+error, Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);


            }
        });

        //for drawer and navigation...
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

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
        }// else if (id == R.id.nav_manage) {

       // } else if (id == R.id.nav_share) {

       // } else if (id == R.id.nav_send) {

       // }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //ends onNavigationItemSelected (drawer)

}

