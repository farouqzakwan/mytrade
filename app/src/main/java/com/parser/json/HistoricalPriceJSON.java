package com.parser.json;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epic on 12/05/2016.
 */
public class HistoricalPriceJSON {

    public String[] date = {"day 1","day 2","day 3","day 4","day 5","day 6","day 7","day 8","day 9","day 10"};
    public float[] shadowBottom = {0,0,0,1,0,0,0,0,0,0};
    public float[] shadowTop = {0,0,0,2,0,0,0,0,0,0};
    public float[] bodyBottom = {0,0,0, 1.07f,0,0,0,0,0,0};
    public float[] bodyTop = {0,0,0,2,0,0,0,0,0,0};
    public String[] volume = {"black","black","black","black","black","black","black","black","black","black"};
    public String[] color = {"black","black","black","black","black","black","black","black","black","black"};

    private String openPricePlaceholder;
    private String closedPricePlaceholder;
    private String highPricePlaceholder;
    private String lowPricePlaceholder;
    private String volumePlaceholder;

    private String urlJSON;
    private JSONArray stockTicker;

    public HistoricalPriceJSON(String urlJSON){
        this.urlJSON = urlJSON;
    }

    public void parseJSON(){

      JSONObject jsonObject = null;

        try{
         //  jsonObject = new JSONObject(urlJSON);
         //   stockTicker = jsonObject.optJSONArray("");
                stockTicker = new JSONArray(urlJSON);

            for (int i = 0;i<10;i++){

                jsonObject = stockTicker.getJSONObject(i);
                float open = 0;
                float closed = 0;
                float low = 0;
                float high = 0;

               //date[i] = jsonObject.getString("date");
                //   date[i] = "hari ni";
                date[i] = jsonObject.optString("date");
                openPricePlaceholder = jsonObject.optString("open_price");

                //[TODO] check the condition.I am not sure with it
                //when both condition are wrong..
                if (openPricePlaceholder.compareTo("-") == 0) {
                    open = 0;
                }else{
                    open = Float.parseFloat(openPricePlaceholder);
                }

                highPricePlaceholder = jsonObject.optString("high_price");
                if (highPricePlaceholder.compareTo("-") == 0) {

                    high = 3f;
                }else{
                    high = Float.parseFloat(highPricePlaceholder);
                }

                lowPricePlaceholder = jsonObject.optString("low_price");
                if (lowPricePlaceholder.compareTo("-")== 0){

                    low = 0f;
                }else{
                    low = Float.parseFloat(lowPricePlaceholder);
                }

                closedPricePlaceholder = jsonObject.optString("close_price");
                if (closedPricePlaceholder.compareTo("-") == 0){
                    closed = 2f;
                }else{
                    closed = Float.parseFloat(closedPricePlaceholder);
                }
                volumePlaceholder = jsonObject.optString("volume");
                volume[i] = volumePlaceholder;

                //put value into the candlestick shadow...
                shadowBottom[i] = low;
                shadowTop[i] = high;

                //put value into the body of the candlestick..
                //compare if open > closed .. -> red
                //if closed > open.. -> green
                //else doesnt matter..

                if (open > closed){
                    color[i] = "red";
                    bodyBottom[i] = closed;
                    bodyTop[i] = open;
                }else if (closed > open){
                    color[i] = "green";
                    bodyBottom[i] = open;
                    bodyTop[i] = closed;
                }else{
                    color[i] = "black";
                    bodyTop[i] = open;
                    bodyBottom[i] = closed;
                }

                Log.d("MY TRADE>>>>", "parseJSON: "+date[i]);
            }
        }catch(JSONException e){
            Log.d("MYTrade", "parseJSON: failed");
        }
    }

    public float[] getBodyBottom() {
        return bodyBottom;
    }

    public float[] getBodyTop() {
        return bodyTop;
    }

    public String[] getColor() {
        return color;
    }

    public String[] getDate() {
        return date;
    }

    public float[] getShadowTop() {
        return shadowTop;
    }

    public float[] getShadowBottom() {
        return shadowBottom;
    }
    public String[] getVolume() {
        return volume;
    }
}
