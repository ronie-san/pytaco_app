package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.AcitivityUtil;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public abstract class BaseActivity extends Activity implements IActivity {

    private boolean keyboardShowing;
    protected static Usuario usuario;
    protected static Clube clube;
    protected static Membro membro;

    protected DialogView dialogLoading;
    protected PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

    protected DialogView createDialog(@LayoutRes int resource) {
        return new DialogView(this, resource);
    }

    protected void makeLongToast(String msg) {
        pMakeToast(msg, Toast.LENGTH_LONG);
    }

    protected void makeShortToast(String msg) {
        pMakeToast(msg, Toast.LENGTH_SHORT);
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

    protected ViewGroup getRootView() {
        return (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    public boolean isKeyboardShowing() {
        return keyboardShowing;
    }

    protected void pFocusEdit(@NotNull EditText edit) {
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
    }

    protected void pHideKeyboard() {
        AcitivityUtil.hideKeyboard(this, getCurrentFocus());
    }

    private void pMakeToast(String msg, int periodo) {
        Toast.makeText(this, msg, periodo).show();
    }

    private void layoutClick() {
        if (keyboardShowing) {
            pHideKeyboard();
        } else if (getCurrentFocus() != null) {
            getCurrentFocus().clearFocus();
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        T v = super.findViewById(id);

        if (v instanceof Button) {
            AcitivityUtil.addButtonClickEffect(v);
        }

        return v;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        final ViewGroup layout = getRootView();
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
        makeShortToast("Erro: " + (error.getMessage() == null ? "Desconhecido" : error.getMessage()));
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
