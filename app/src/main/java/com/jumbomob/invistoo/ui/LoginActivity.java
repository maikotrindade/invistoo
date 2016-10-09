package com.jumbomob.invistoo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.presenter.LoginPresenter;
import com.jumbomob.invistoo.ui.component.CircleImageView;
import com.jumbomob.invistoo.util.ProgressDialogUtil;
import com.jumbomob.invistoo.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText mEmailTextView, mPasswordTextView;
    private CircleImageView mNewProfileImage, mLoginImage;
    private LoginPresenter mPresenter;
    private TextView mNewUserText;
    private CheckBox mRememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindElements();
        configureElements();
        mPresenter = new LoginPresenter(this);
    }

    private void bindElements() {
        mEmailTextView = (EditText) findViewById(R.id.email_edit_text);
        mPasswordTextView = (EditText) findViewById(R.id.password_edit_text);
        mNewUserText = (TextView) findViewById(R.id.new_user_button);
        mLoginImage = (CircleImageView) findViewById(R.id.login_button);
        mRememberCheckBox = (CheckBox) findViewById(R.id.remember_me_check_box);
    }

    private void configureElements() {
        mLoginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmailTextView.getText().toString();
                final String password = mPasswordTextView.getText().toString();
                if (mPresenter.isValidFields(email, password)) {
                    mPresenter.performLogin(email, password);
                }
            }
        });

        mNewUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmailTextView.getText().toString();
                final String password = mPasswordTextView.getText().toString();
                if (mPresenter.isValidFields(email, password)) {
                    mPresenter.performCreateAccount(email, password);
                }
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
    public void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onCreateUserSuccess() {
        //TODO adicionar feedback para o usuario
        startActivity(new Intent(this, MainActivity.class));
    }
}
