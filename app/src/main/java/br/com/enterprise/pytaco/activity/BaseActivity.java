package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class BaseActivity extends Activity implements IActivity {

    protected DialogView dialogLoading;
    protected PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

    protected DialogView createDialog(@LayoutRes int resource) {
        return new DialogView(this, resource);
    }

    protected void makeLongToast(String msg) {
        makeToast(msg, Toast.LENGTH_LONG);
    }

    protected void makeShortToast(String msg) {
        makeToast(msg, Toast.LENGTH_SHORT);
    }

    protected void btnVoltarClick() {
        this.onBackPressed();
    }

    protected void pHideKeyborad() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();

        if (view == null) {
            view = new View(this);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected Point pGetSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    protected void pShowProgress() {
        dialogLoading = createDialog(R.layout.dialog_loading);

        ImageView imgBola = dialogLoading.findViewById(R.id.loading_imgBola);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new LinearInterpolator());
        imgBola.startAnimation(rotation);

        dialogLoading.showDialog();
    }

    protected void pCancelDialog() {
        if (dialogLoading != null && dialogLoading.dialogShowing()) {
            dialogLoading.cancelDialog();
        }
    }

    protected void pDisableScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    protected void pEnableScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    protected void pHideKeboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void makeToast(String msg, int periodo) {
        Toast.makeText(this, msg, periodo).show();
    }

    @Override
    public PytacoRequestEnum getPytacoRequest() {
        return this.pytacoRequestEnum;
    }

    @Override
    public void setPytacoRequest(PytacoRequestEnum value) {
        this.pytacoRequestEnum = value;
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    @Override
    public void onSucess(String response) {
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    @Override
    public void onError(VolleyError error) {
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    @Override
    public void onStartRequest() {
        pDisableScreen();
        pShowProgress();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
