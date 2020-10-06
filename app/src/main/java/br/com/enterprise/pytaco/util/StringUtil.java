package br.com.enterprise.pytaco.util;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class StringUtil {

    @NotNull
    public static SpannableString textoSublinhado(String texto) {
        SpannableString content = new SpannableString(texto);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    @NotNull
    public static String numberToStr(Double valor) {
        return doubleToStr(valor, "#.##");
    }

    @NotNull
    public static String priceToStr(Double valor) {
        return doubleToStr(valor, "0.00");
    }

    @NotNull
    public static String numberToStr(int valor) {
        return String.valueOf(valor);
    }

    public static Double strToNumber(String valor) {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return strToNumber(valor.replace(",", "."));
        }
    }

    private static String doubleToStr(Double valor, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(valor);
    }
}
