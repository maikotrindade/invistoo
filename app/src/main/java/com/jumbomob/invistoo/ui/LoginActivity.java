package com.jumbomob.invistoo.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.presenter.LoginPresenter;
import com.jumbomob.invistoo.ui.component.CircleImageView;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.util.ProgressDialogUtil;
import com.jumbomob.invistoo.view.LoginView;


public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText mEmailEdtView, mPasswordEdtView;
    private CircleImageView mLoginImage;
    private LoginPresenter mPresenter;
    private TextView mNewUserText;
    private CheckBox mRememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);
        if (!mPresenter.isUserAlreadyLogged(getBaseContext())) {
            bindElements();
            configureElements();
        } else {
            mPresenter.loginAuthenticatedUser(getBaseContext());
        }
    }

    private void bindElements() {
        mEmailEdtView = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEdtView = (EditText) findViewById(R.id.new_password_text);
        mNewUserText = (TextView) findViewById(R.id.new_user_button);
        mLoginImage = (CircleImageView) findViewById(R.id.login_button);
        mRememberCheckBox = (CheckBox) findViewById(R.id.remember_me_check_box);
    }

    private void configureElements() {
        mLoginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.isValidEmailField(mEmailEdtView) && mPresenter.isValidField(mPasswordEdtView)) {
                    boolean isRememberUser = mRememberCheckBox.isChecked();
                    mPresenter.performLogin(mEmailEdtView.getText().toString(), mPasswordEdtView.getText().toString(), isRememberUser);
                }
            }
        });

        mNewUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterNewUserDialog();
            }
        });
    }

    public void showProgressDialog(final int resourceId) {
        ProgressDialogUtil.getInstance()
                .showProgressDialog(LoginActivity.this, getString(resourceId));
    }

    public void hideProgressDialog() {
        ProgressDialogUtil.getInstance().hideProgressDialog();
    }

    @Override
    public void onLoginSuccess(String email, boolean isRememberUser) {
        mPresenter.loadUser(email, isRememberUser);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onCreateUserSuccess(String email) {
        mPresenter.createUser(email);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showRegisterNewUserDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_user_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        final EditText emailEdtTxt = (EditText) dialog.findViewById(R.id.register_email_edit_text);
        final EditText passwordEdtTxt = (EditText) dialog.findViewById(R.id.register_password_text);

        (dialog.findViewById(R.id.ok_text_view)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isValidEmailField(emailEdtTxt) && mPresenter.isValidField(passwordEdtTxt)) {
                    mPresenter.performCreateAccount(emailEdtTxt.getText().toString(), passwordEdtTxt.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    @Override
    public void setErrorMessage(final int messageResourceId, final EditText editText) {
        editText.setError(getString(messageResourceId));
    }

    @Override
    public void showMessage(final int messageResourceId, final int length) {
        InvistooUtil.makeSnackBar(this, getString(messageResourceId), length).show();
    }
}
