package br.com.enterprise.pytaco.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.activity.BaseActivity;

public class AcitivityUtil {

    public static void hideKeyboard(@NotNull BaseActivity activity) {
        hideKeyboard(activity, activity.getCurrentFocus());
    }

    public static void hideKeyboard(@NotNull BaseActivity activity, View focusedView) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (focusedView != null) {
            focusedView.clearFocus();
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static ViewGroup getRootView(@NotNull Activity activity) {
        return (ViewGroup) ((ViewGroup) activity
                .findViewById(android.R.id.content)).getChildAt(0);
    }
}
