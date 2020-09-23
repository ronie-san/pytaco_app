package br.com.enterprise.pytaco.util;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class StringUtil {

    @NotNull
    public static SpannableString textoSublinhado(String texto) {
        SpannableString content = new SpannableString(texto);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    @NotNull
    public static String numberToStr(Double valor) {
        return String.format(Locale.getDefault(), "%.0f", valor);
    }

    @NotNull
    public static String numberToStr(int valor) {
        return String.valueOf(valor);
    }
}
