package com.practice.another.anotherpractice;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Flashscreen extends AppCompatActivity{

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flashscreen);

        Thread runThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        try{
                            Thread.sleep(3000);
                            Intent startMain = new Intent("com.practice.another.anotherpractice.MainActivity");
                            startActivity(startMain);
                        }catch (Exception e){
                            Log.e("myTrade","error multithread");
                        }
                    }
                }
        );
        runThread.start();

    }
    //ends onCreate method

}


