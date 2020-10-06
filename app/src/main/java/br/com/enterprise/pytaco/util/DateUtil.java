package br.com.enterprise.pytaco.util;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    @NotNull
    public static String toDefaultFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    @NotNull
    public static String toAPIFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @NotNull
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date agora = Calendar.getInstance().getTime();
        return sdf.format(agora);
    }

    @NotNull
    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @NotNull
    public static String getStrDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date agora = Calendar.getInstance().getTime();
        return sdf.format(agora);
    }

    public static Date parse(String date) {
        return parse(date, "dd/MM/yyyy");
    }

    public static Date parse(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return Calendar.getInstance().getTime();
        }
    }

    @NotNull
    public static String formarData(int dia, int mes, int ano) {
        String diaStr = StringUtil.numberToStr(dia);
        String mesStr = StringUtil.numberToStr(mes);
        String anoStr = StringUtil.numberToStr(ano);

        diaStr = diaStr.length() == 2 ? diaStr : ("0" + diaStr);
        mesStr = mesStr.length() == 2 ? mesStr : ("0" + mesStr);
        return diaStr + "/" + mesStr + "/" + anoStr;
    }

    @NotNull
    public static Date addDay(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    @NotNull
    public static Date addDay(Date date) {
        return addDay(date, 1);
    }

}
