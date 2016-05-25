package com.practice.another.anotherpractice;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class BrokerageCalculation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brokerage_calculation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent calc = getIntent();

        String buy = calc.getStringExtra("buyPrice");
        String unit = calc.getStringExtra("unitNo");
        String sell = calc.getStringExtra("sellPrice");
        String broker = calc.getStringExtra("brokerName");

        ImageView img = (ImageView)findViewById(R.id.imgBack);

        //set the image on the back
        if (broker.compareTo("MAYBANK") == 0){
            img.setImageResource(R.drawable.maybank);
        }else{
            img.setImageResource(R.drawable.pbe);
        }

        int volume = Integer.parseInt(unit);
        float buyPrice = Float.parseFloat(buy);
        float sellPrice = Float.parseFloat(sell);

        BrokerageCalculator calculator = new BrokerageCalculator(volume,buyPrice,sellPrice,broker);
        calculator.calculate();

        double totalBuy = calculator.getTotalBuy();
        double totalSell = calculator.getTotalSell();
        double profitLoss = calculator.getProfitLoss();
        double percentProfitLoss = calculator.getPercentProfitLoss();

        TextView totalBuyText = (TextView)findViewById(R.id.totalbuytxt);
        TextView totalSellText = (TextView)findViewById(R.id.totalselltxt);
        TextView profitLossText = (TextView)findViewById(R.id.profitRM);
        TextView percentageText = (TextView)findViewById(R.id.profitPercentage);

        totalBuyText.setText("RM"+String.format("%.3f",totalBuy));
        totalSellText.setText("RM"+String.format("%.3f",totalSell));
        profitLossText.setText(String.format("%.3f",profitLoss));
        percentageText.setText(String.format("%.3f",percentProfitLoss)+"%");
    }

}
