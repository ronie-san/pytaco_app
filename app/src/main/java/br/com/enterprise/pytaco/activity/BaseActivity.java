package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.util.AcitivityUtil;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public abstract class BaseActivity extends Activity implements IActivity {

    private boolean keyboardShowing;
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

    protected void pAddButtonEffect(@NotNull View button) {
        button.setOnTouchListener(new View.OnTouchListener() {

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

    public boolean isKeyboardShowing() {
        return keyboardShowing;
    }

    private void makeToast(String msg, int periodo) {
        Toast.makeText(this, msg, periodo).show();
    }

    private void layoutClick() {
        if (keyboardShowing) {
            AcitivityUtil.hideKeyboard(this);
        } else if (getCurrentFocus() != null) {
            getCurrentFocus().clearFocus();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        final ViewGroup layout = AcitivityUtil.getRootView(this);
        layout.setClickable(true);
        layout.setFocusable(true);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutClick();
            }
        });

        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                layout.getWindowVisibleDisplayFrame(rect);
                int screenHeight = layout.getRootView().getHeight();
                int keypadHeight = screenHeight - rect.bottom;
                keyboardShowing = keypadHeight > screenHeight * 0.15;
            }
        });
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
