package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
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

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Bolao;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Jogo;
import br.com.enterprise.pytaco.pojo.Liga;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.pojo.Pais;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.AcitivityUtil;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public abstract class BaseActivity extends Activity {

    private boolean keyboardShowing;
    protected static Usuario usuario;
    protected static Clube clube;
    protected static Membro membro;
    protected static Pais pais;
    protected static Liga liga;
    protected static Bolao bolao;
    protected static List<Jogo> lstJogo = new ArrayList<>();
    protected static List<Jogo> lstJogoSelecionado = new ArrayList<>();

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

    protected void pShowOkDialog(String title, String msg) {
        pShowOkDialog(title, msg, null);
    }

    protected void pShowOkDialog(String title, String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle(title);
        alert.setPositiveButton("OK", listener);
        alert.setMessage(msg);
        alert.show();
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

    protected void pCancelLoading() {
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

    protected void pFocusEdit(@NotNull EditText edit) {
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
    }

    protected int pGetVisible(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

    protected void pHideKeyboard() {
        AcitivityUtil.hideKeyboard(this, getCurrentFocus());
    }

    //este flag faz voltar à Activity que já existe, limpando todas que existem acima dela
    protected void pStartActivityClearTop(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void pStartActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
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

    public boolean isKeyboardShowing() {
        return keyboardShowing;
    }

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
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

    public void setPytacoRequest(PytacoRequestEnum value) {
        this.pytacoRequestEnum = value;
    }

    public void onSucess(String response) {
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    public void onError(@NotNull VolleyError error) {
        pCancelLoading();
        pEnableScreen();
        makeShortToast("Erro: " + (error.getMessage() == null ? "Desconhecido" : error.getMessage()));
        this.pytacoRequestEnum = PytacoRequestEnum.NONE;
    }

    public void onStartRequest() {
        if (dialogLoading == null) {
            pDisableScreen();
            pShowProgress();
        }
    }
}
