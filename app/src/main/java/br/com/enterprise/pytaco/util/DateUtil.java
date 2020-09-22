package br.com.enterprise.pytaco.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    @NotNull
    public static String toDefaultFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(date);
    }

    @NotNull
    public static String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date agora = Calendar.getInstance().getTime();
        return sdf.format(agora);
    }
}
