package br.com.enterprise.pytaco.util;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.activity.BaseActivity;

public class AcitivityUtil {

    public static void hideKeyboard(@NotNull BaseActivity activity, View focusedView) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (focusedView != null) {
            focusedView.clearFocus();
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static void addButtonClickEffect(@NotNull View botao){
        botao.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}
