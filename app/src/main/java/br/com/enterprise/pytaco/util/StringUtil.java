package br.com.enterprise.pytaco.util;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import org.jetbrains.annotations.NotNull;

public class StringUtil {

    @NotNull
    public static SpannableString textoSublinhado(String texto){
        SpannableString content = new SpannableString(texto);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }
}
