package com.jumbomob.invistoo.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.model.persistence.UserDAO;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.SecurityUtils;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.view.LoginView;

import org.joda.time.DateTime;

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

    public void performLogin(final String email, final String password, final boolean isRememberUser) {
        mView.showProgressDialog(R.string.loading_login);
        final Firebase firebase = new Firebase(ConstantsUtil.FIREBASE_URL);
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mView.onLoginSuccess(email, isRememberUser);
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
                mView.onCreateUserSuccess(email);
                mView.hideProgressDialog();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                //TODO feedback para o usuario
                mView.hideProgressDialog();
            }
        });
    }

    public boolean isUserAlreadyLogged(final Context context) {
        return SharedPrefsUtil.isUserLogged(context);
    }

    public void createUser(final String email) {
        SharedPrefsUtil.setUserLogged(true);
        User user = new User();
        user.setUpdateDate(DateTime.now().toString());
        user.setEmail(email);
        final String userUid = SecurityUtils.generateId();
        user.setUid(userUid);
        SharedPrefsUtil.setLastUserUid(userUid);

        final UserDAO userDAO = UserDAO.getInstance();
        userDAO.insert(user);
    }

    public void loadUser(final String email, boolean isRememberUser) {
        final UserDAO userDAO = UserDAO.getInstance();
        final User userByEmail = userDAO.findByEmail(email);

        if (userByEmail != null) {
            SharedPrefsUtil.setLastUserUid(userByEmail.getUid());
        } else {
            createUser(email);
        }

        SharedPrefsUtil.setUserLogged(true);
        SharedPrefsUtil.setRememberUser(isRememberUser);
    }

    public void loginAuthenticatedUser(final Context context) {
        final String lastUserUid = SharedPrefsUtil.getLastUserUid(context);
        final UserDAO userDAO = UserDAO.getInstance();
        final User userByUid = userDAO.findByUid(lastUserUid);
        if (userByUid != null) {
            SharedPrefsUtil.setUserLogged(true);
            mView.onLoginSuccess();
        } else {
            //TODO display error auth to the user
        }
    }

}
