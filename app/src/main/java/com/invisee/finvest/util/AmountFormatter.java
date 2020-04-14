package com.invisee.finvest.util;

import android.net.ParseException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Fajar on 5/11/2015.
 */
public class AmountFormatter {

    public static String format(double amount){
        String amountOnString = "Rp. " + String.format("%1$,.2f", amount);
        amountOnString = amountOnString.replaceAll(",",".");
        StringBuilder formatedDecimalPlaces = new StringBuilder(amountOnString);
        formatedDecimalPlaces.setCharAt(amountOnString.length() - 3, ',');
        return formatedDecimalPlaces.toString();
    }

    public static String format(String amount){
        try {
            double amountOnDouble = Double.parseDouble(amount);
            String amountOnString = "Rp. " + String.format("%1$,.2f", amountOnDouble);
            amountOnString = amountOnString.replaceAll(",",".");
            StringBuilder formatedDecimalPlaces = new StringBuilder(amountOnString);
            formatedDecimalPlaces.setCharAt(amountOnString.length() - 3, ',');
            return formatedDecimalPlaces.toString();
        } catch (ParseException e) {
            return amount;
        }
    }

    public static String formatNonCurrency(double amount){
        return new DecimalFormat("#,###,###", new DecimalFormatSymbols(Locale.GERMAN)).format(amount);
    }
}
