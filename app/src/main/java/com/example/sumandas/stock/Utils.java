package com.example.sumandas.stock;

public class Utils {

    public static int compareTimestamps(String time1, String time2){
        String[] t1 = time1.split(":");
        String[] t2 = time2.split(":");
        if(Integer.parseInt(t1[0]) == Integer.parseInt(t2[0])){
            return Integer.parseInt(t1[0])- Integer.parseInt(t2[0])*-1;
        }else{
            return Integer.parseInt(t1[0]) - Integer.parseInt(t2[0])*-1;
        }
     }

}
