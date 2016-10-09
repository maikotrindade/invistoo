package com.jumbomob.invistoo.presenter;

import android.text.TextUtils;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.view.LoginView;

import java.util.Map;

/**
 * @author maiko.trindade
 * @since 09/10/2016
 */
public class LoginPresenter implements BasePresenter<LoginView> {

    private LoginView mView;

    @Override
    public void attachView(LoginView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public LoginPresenter(LoginView view) {
        attachView(view);
    }


    public boolean isValidFields(final String email, final String password) {
        if (TextUtils.isEmpty(email)) {
            //TODO Send feedback to the user
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            //TODO Send feedback to the user
            return false;
        }

        return true;
    }

    public void performLogin(final String email, final String password) {
        mView.showProgressDialog(R.string.loading_login);
        final Firebase firebase = new Firebase(ConstantsUtil.FIREBASE_URL);
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mView.onLoginSuccess();
                mView.hideProgressDialog();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //TODO feedback para o usuario
                mView.hideProgressDialog();
            }
        });
    }

    public void performCreateAccount(final String email, final String password) {
        mView.showProgressDialog(R.string.loading_register);
        final Firebase firebase = new Firebase(ConstantsUtil.FIREBASE_URL);
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
//                User user = new User();
//                user.setUpdateDate(DateTime.now().toString());
//                user.setCreationDate(DateTime.now().toString());
//                user.setLastName();
//                user.getToken();
//                user.setExpires();
//                user.setUid();
                mView.onCreateUserSuccess();
                mView.hideProgressDialog();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                //TODO feedback para o usuario
                mView.hideProgressDialog();
            }
        });
    }
}
