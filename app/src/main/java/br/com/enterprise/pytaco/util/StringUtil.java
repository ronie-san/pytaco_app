package br.com.enterprise.pytaco.util;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class StringUtil {

    @NotNull
    public static SpannableString textoSublinhado(String texto) {
        SpannableString content = new SpannableString(texto);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    @NotNull
    public static String numberToStr(Double valor) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        String result = df.format(valor);
        return result;
    }

    @NotNull
    public static String numberToStr(int valor) {
        return String.valueOf(valor);
    }
}
