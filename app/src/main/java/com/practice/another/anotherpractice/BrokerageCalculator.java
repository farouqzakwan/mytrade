package com.practice.another.anotherpractice;

/**
 * Created by epic on 12/03/2016.
 */
public class BrokerageCalculator {

    //MAYBANK RATE..
    private static float MAYBANK_MINIMUM_BROKERAGE_FEE = 8;
    private static double MAYBANK_BROKERAGE_FEE = 0.001;
    private static double MAYBANK_INTRADAY_RATE = 0;
    private static double MAYBANK_CLEARING_FEE = 0.0032;

    //PUBLICBANK RATE..
    private static float PUBLICBANK_MINIMUM_BROKERAGE_FEE = 12;
    private  static double PUBLICBANK_BROKERAGE_FEE = 0.0042;
    private static double PUBLICBANK_INTRADAY_RATE = 0;
    private static double PUBLICBANK_CLEARING_FEE = 0.0032;

    private int volume;
    private float buyPrice;
    private float sellPrice;
    private String broker;

    private double totalBuy;
    private double totalSell;
    private double profitLoss;
    private double percentProfitLoss;

    BrokerageCalculator(int volume,float buyPrice,float sellPrice,String broker){
        this.volume = volume;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.broker = broker;
    }

    public void calculate(){
        float buyingPrice = buyPrice * volume;
        float sellingPrice = sellPrice * volume;
        double buyClearing = 0;
        double sellClearing = 0;
        double brokerageCharge = 0;

        //calculate buy + charge..
        if(broker.compareTo("MAYBANK") == 0){
            brokerageCharge = MAYBANK_BROKERAGE_FEE * buyingPrice;
            if (brokerageCharge < MAYBANK_MINIMUM_BROKERAGE_FEE){
                brokerageCharge = MAYBANK_MINIMUM_BROKERAGE_FEE;
                buyClearing = MAYBANK_CLEARING_FEE * buyingPrice;
            }
        }else if(broker.compareTo("PUBLICBANK") == 0){
            brokerageCharge = PUBLICBANK_BROKERAGE_FEE * buyingPrice;
            if(brokerageCharge < PUBLICBANK_MINIMUM_BROKERAGE_FEE){
                brokerageCharge = PUBLICBANK_MINIMUM_BROKERAGE_FEE;
                buyClearing = PUBLICBANK_CLEARING_FEE * buyingPrice;
            }
        }

        this.totalBuy = buyingPrice + brokerageCharge + buyClearing;
        brokerageCharge = 0;

        //calculate sell + charge
        if(broker.compareTo("MAYBANK") == 0){
            brokerageCharge = MAYBANK_BROKERAGE_FEE * sellingPrice;
            if (brokerageCharge < MAYBANK_MINIMUM_BROKERAGE_FEE){
                brokerageCharge = MAYBANK_MINIMUM_BROKERAGE_FEE;
                sellClearing = MAYBANK_CLEARING_FEE * sellingPrice;
            }
        }else if(broker.compareTo("PUBLICBANK") == 0){
            brokerageCharge = PUBLICBANK_BROKERAGE_FEE * sellingPrice;
            if(brokerageCharge < PUBLICBANK_MINIMUM_BROKERAGE_FEE){
                brokerageCharge = PUBLICBANK_MINIMUM_BROKERAGE_FEE;
                sellClearing = PUBLICBANK_CLEARING_FEE * sellingPrice;
            }
        }

        this.totalSell = sellingPrice - brokerageCharge - sellClearing;

        this.profitLoss = this.totalSell - this.totalBuy;
        this.percentProfitLoss = (this.profitLoss / this.totalBuy) * 100;
    }

    public double getPercentProfitLoss() {
        return percentProfitLoss;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public double getTotalSell() {
        return totalSell;
    }

    public double getTotalBuy() {
        return totalBuy;
    }
}
