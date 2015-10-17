package com.tvoyagryvnia.util;

import java.math.BigDecimal;

public class NumberFormatter {

    public static Float cutFloat(float value){
        BigDecimal roundfinalPrice = new BigDecimal(value).setScale(4,BigDecimal.ROUND_HALF_UP);
        return roundfinalPrice.floatValue();
    }
    public static Float cutFloat(float value,int scale){
        BigDecimal roundfinalPrice = new BigDecimal(value).setScale(scale,BigDecimal.ROUND_HALF_UP);
        return roundfinalPrice.floatValue();
    }
}
