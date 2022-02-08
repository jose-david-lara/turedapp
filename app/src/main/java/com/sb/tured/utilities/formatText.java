package com.sb.tured.utilities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class formatText {


    public String integerFormat(int i) {
        String s;
        if(i < 10000){
            DecimalFormat df = new DecimalFormat("$#,###.##");
            s = df.format(i);
        }else if (i > 9000 && i < 100000) {
            DecimalFormat df = new DecimalFormat("$##,###.##");
            s = df.format(i);
        }else if(i > 99000 && i < 1000000){
            DecimalFormat df = new DecimalFormat("$###,###.##");
            s = df.format(i);
        }else if(i > 900000 && i < 100000000){
            DecimalFormat df = new DecimalFormat("$###,###,###.##");
            s = df.format(i);
        }else{
            s = Integer.toString(i);
        }

        return s;
    }

    public String longFormat(long i) {
        String s;
        if(i < 10000){
            DecimalFormat df = new DecimalFormat("$#,###.##");
            s = df.format(i);
        }else if (i > 9000 && i < 100000) {
            DecimalFormat df = new DecimalFormat("$##,###.##");
            s = df.format(i);
        }else if(i > 99000 && i < 1000000){
            DecimalFormat df = new DecimalFormat("$###,###.##");
            s = df.format(i);
        }else if(i > 900000 && i < 100000000){
            DecimalFormat df = new DecimalFormat("$###,###,###.##");
            s = df.format(i);
        }else{
            s = Long.toString(i);
        }

        return s;
    }

    public String stringFormat(String i) {
        String s;
        BigDecimal value = new BigDecimal(i);
        if(i.length() < 8){
            DecimalFormat df = new DecimalFormat("$#,###.##");
            s = df.format(value);
        }else if (i.length() > 7 && i.length() < 9) {
            DecimalFormat df = new DecimalFormat("$##,###.##");
            s = df.format(value);
        }else if(i.length() > 8 && i.length() < 10){
            DecimalFormat df = new DecimalFormat("$###,###.##");
            s = df.format(value);
        }else if(i.length() > 10 && i.length() < 11){
            DecimalFormat df = new DecimalFormat("$###,###,###.##");
            s = df.format(value);
        }else{
            s = i;
        }

        return s;
    }

}
