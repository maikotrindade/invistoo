package com.jumbomob.invistoo.presenter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.model.persistence.UserDAO;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.util.SecurityUtils;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.view.LoginView;

import org.joda.time.DateTime;

import java.util.Map;

import static com.jumbomob.invistoo.R.string.error_general;

/**
 * @author maiko.trindade
 * @since 09/10/2016
 */
public class LoginPresenter implements BasePresenter<LoginView> {

    private LoginView mView;
    private Context mContext;

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
        mContext = (Context) view;
    }

    public boolean isKnownUser() {
        return SharedPrefsUtil.isKnownUser(mContext);
    }

    public void updateKnownUser() {
        SharedPrefsUtil.setKnownUser(mContext, true);
    }

    public boolean isValidEmailField(final EditText editText) {
        boolean valid = false;
        if (editText != null) {
            final String email = editText.getText().toString();
            if (TextUtils.isEmpty(email)) {
                mView.setErrorMessage(R.string.required_field, editText);
            } else {
                if (InvistooUtil.isValidEmailAddress(email)) {
                    valid = true;
                } else {
                    mView.setErrorMessage(R.string.invalid_email, editText);
                }
            }
        }
        return valid;
    }

    public boolean isValidField(final EditText editText) {
        if (editText != null) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                mView.setErrorMessage(R.string.required_field, editText);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void performLogin(final String email, final String password, final boolean isRememberUser) {
        mView.showProgressDialog(R.string.loading_login);
        final Firebase firebase = new Firebase(ConstantsUtil.FIREBASE_URL);
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                loadUser(email, isRememberUser);
                mView.hideProgressDialog();
                mView.onLoginSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mView.hideProgressDialog();
                mView.showMessage(error_general, Snackbar.LENGTH_LONG);
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
                mView.hideProgressDialog();
                mView.showMessage(error_general, Snackbar.LENGTH_LONG);
            }
        });
    }

    public boolean isUserAlreadyLogged(final Context context) {
        return SharedPrefsUtil.isUserLogged(context);
    }

    public User createUserIfNecessary(final String email) {
        final UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setUpdateDate(DateTime.now().toString());
            user.setEmail(email);
            final String userUid = SecurityUtils.generateId();
            user.setUid(userUid);
            userDAO.insert(user);
        }
        SharedPrefsUtil.setUserLogged(true);

        return user;
    }

    public void loadUser(final String email, boolean isRememberUser) {
        final User user = createUserIfNecessary(email);

        InvistooApplication.getInstance().setLoggedUser(user);
        SharedPrefsUtil.setUserLogged(true);
        SharedPrefsUtil.setRememberUser(isRememberUser);

        if (isRememberUser) {
            SharedPrefsUtil.setLastUserUid(user.getUid());
        }
    }

    public void loadUser(Context context) {
        final UserDAO userDAO = UserDAO.getInstance();
        final String lastUserUid = SharedPrefsUtil.getLastUserUid(context);
        InvistooApplication.getInstance().setLoggedUser(userDAO.findByUid(lastUserUid));
        SharedPrefsUtil.setUserLogged(true);
    }

    public void loadUser(final User user) {
        InvistooApplication.getInstance().setLoggedUser(user);
        SharedPrefsUtil.setUserLogged(true);
    }
}
