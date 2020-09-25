package br.com.enterprise.pytaco.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.activity.BaseActivity;

public class DialogView {
    private View view;
    private AlertDialog dialog;

    public DialogView(final BaseActivity activity, @LayoutRes int resource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, null);

        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view, 0, 0, 0, 0);

        view.setClickable(true);
        view.setFocusable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootViewClick(activity);
            }
        });

    }

    private void rootViewClick(@NotNull BaseActivity activity) {
        if (activity.isKeyboardShowing()) {
            if (dialog.getWindow() != null) {
                AcitivityUtil.hideKeyboard(activity, dialog.getWindow().getCurrentFocus());
            }
        } else if (dialog.getWindow() != null && dialog.getWindow().getCurrentFocus() != null) {
            dialog.getWindow().getCurrentFocus().clearFocus();
        }
    }

    public <T extends View> T findViewById(@IdRes int id) {
        T v = view.findViewById(id);

        if (v instanceof Button) {
            AcitivityUtil.addButtonClickEffect(v);
        }

        return v;
    }

    public void cancelDialog() {
        dialog.cancel();
    }

    public void showDialog() {
        dialog.show();
    }

    public boolean dialogShowing() {
        return dialog.isShowing();
    }

    public View getView() {
        return view;
    }

    public AlertDialog getDialog() {
        return dialog;
    }
}
