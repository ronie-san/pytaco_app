package br.com.enterprise.pytaco.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import br.com.enterprise.pytaco.R;

public class DialogView {
    private View view;
    private AlertDialog dialog;

    public DialogView(Activity activity, @LayoutRes int resource){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, null);

        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view, 0, 0, 0, 0);
    }

    public <T extends View> T findViewById(@IdRes int id){
        return this.view.findViewById(id);
    }

    public void cancelDialog(){
        this.dialog.cancel();
    }

    public void showDialog(){
        this.dialog.show();
    }

    public boolean dialogShowing(){
        return this.dialog.isShowing();
    }

    public View getView() {
        return view;
    }

    public AlertDialog getDialog() {
        return dialog;
    }
}
