package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public abstract class BaseActivity extends Activity implements IActivity {

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

        dialogLoading.getDialog().setCancelable(false);
        dialogLoading.getDialog().setCanceledOnTouchOutside(false);
        dialogLoading.showDialog();
    }

    protected void pCancelDialog() {
        if (dialogLoading != null && dialogLoading.dialogShowing()) {
            dialogLoading.cancelDialog();
            dialogLoading = null;
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
    public void setPytacoRequest(PytacoRequestEnum value) {
        this.pytacoRequestEnum = value;
    }

    @Override
    public void onSucess(String response) {
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        pCancelDialog();
        pEnableScreen();
        makeLongToast("Erro: " + (error.getMessage() == null ? "Desconhecido" : error.getMessage()));
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    @Override
    public void onStartRequest() {
        if (dialogLoading == null) {
            pDisableScreen();
            pShowProgress();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
