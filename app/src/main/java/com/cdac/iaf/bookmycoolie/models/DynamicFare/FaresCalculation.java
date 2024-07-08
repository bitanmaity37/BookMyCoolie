package com.cdac.iaf.bookmycoolie.models.DynamicFare;

import com.cdac.iaf.bookmycoolie.utils.TimeConversionUtil;

import java.util.ArrayList;

public class FaresCalculation {

   /*final Fares Raipur = new Fares("Raipur", 100,60, 120);
    final Fares Durg = new Fares("Durg", 80,50, 80);
    final Fares BYT = new Fares("BYT", 70,50, 80);
    final Fares BPHB = new Fares("BPHB", 70,40, 80);
    final Fares TLD = new Fares("TLD", 60,40, 80);*/


    static final ArrayList<Fares> fares = new ArrayList<>();
    public void doAll(){
      fares.add(new Fares(6,"Raipur", 100,60, 120));
      fares.add(new Fares(10,"Durg", 80,50, 80));
      fares.add(new Fares(9,"BYT", 70,50, 80));
      fares.add(new Fares(7,"BPHB", 70,40, 80));
      fares.add(new Fares(8,"TLD", 60,40, 80));
    }

    static final Fares fares2 = new Fares(100,150);

    public Integer calculateFare(Integer stationID, String start, String end, Integer weight, Boolean cart, Boolean wc2, Boolean wc4){
        Integer total = 0;
        System.out.println(start + " time "+ end);

        Fares currentStnFare = findFare(stationID);


        Integer duration = new TimeConversionUtil().getMinutesDiffForCost(start, end);
        System.out.println("duration "+duration+" weight "+weight);

        Integer noOfHalfHoursForCalculation = duration/30 - 1;
        Integer timeRemainder = duration%30;
        if(timeRemainder!=0){
                    noOfHalfHoursForCalculation = noOfHalfHoursForCalculation+1;
                }
        Integer costForWaiting = noOfHalfHoursForCalculation*currentStnFare.rateWaiting;
        //total = total+costForWaiting;

        int w1 = weight/40;
        int w2 = weight%40;
        if(w2!=0){
            w1 = w1+1;
        }
        System.out.println(w1+" "+w2+" "+currentStnFare.rateCoolie);
        Integer costForWeightage = w1*currentStnFare.rateCoolie;
        total = total+costForWeightage;

        if(cart && !wc2 && !wc4){
            total = total+currentStnFare.rateBarrow;
        }
        if(!cart && wc2 && !wc4){
            total = total+fares2.rateWheelChairByTwo;
        }
        if(!cart && !wc2 && wc4){
            total = total+fares2.rateWheelChairByFour;
        }

        System.out.println("total "+total);
        return total;

    }

    public Fares findFare(Integer s_id) {
        for (Fares fare : fares) {
            if (fare.getStationID() == s_id) {
                return fare;
            }
        }
        return null; // or throw an exception if not found
    }


}
