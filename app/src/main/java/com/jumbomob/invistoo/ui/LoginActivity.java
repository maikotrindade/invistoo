package com.jumbomob.invistoo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.util.ConstantsUtil;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailTextView, mPasswordTextView;
    private Button mLoginButton, mNewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindElements();
    }

    private void bindElements() {

        mEmailTextView = (EditText) findViewById(R.id.email_edit_text);
        mPasswordTextView = (EditText) findViewById(R.id.password_edit_text);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mNewUserButton = (Button) findViewById(R.id.new_user_button);

        final LoginActivity loginActivity = this;

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailTextView.getText().toString();
                String password = mPasswordTextView.getText().toString();

                Firebase ref = new Firebase(ConstantsUtil.FIREBASE_URL);
                ref.authWithPassword(email, password, new
                        Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                startActivity(new Intent(loginActivity, MainActivity.class));
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                //TODO feedback para o usuario
                            }
                        });
            }
        });

        mNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailTextView.getText().toString();
                String password = mPasswordTextView.getText().toString();

                Firebase ref = new Firebase(ConstantsUtil.FIREBASE_URL);
                ref.createUser(email, password, new Firebase
                        .ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        startActivity(new Intent(loginActivity, MainActivity.class));
                        //TODO feedback para o usuario
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        //TODO feedback para o usuario
                    }
                });
            }
        });
    }

}
