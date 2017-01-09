package com.jumbomob.invistoo.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.model.persistence.UserDAO;
import com.jumbomob.invistoo.ui.AccountFragment;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.util.StorageUtil;
import com.jumbomob.invistoo.view.MyAccountView;

import java.io.IOException;

import io.realm.Realm;

/**
 * @author maiko.trindade
 * @since 16/10/2016
 */
public class MyAccountPresenter implements BasePresenter<MyAccountView> {

    private MyAccountView mView;
    private AccountFragment mFragment;
    private static final String TAG = MyAccountPresenter.class.getSimpleName();
    public static final int SELECT_PICTURE = 1;

    @Override
    public void attachView(MyAccountView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public MyAccountPresenter(AccountFragment fragment, MyAccountView view) {
        mFragment = fragment;
        attachView(view);
    }

    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mFragment.startActivityForResult(Intent.createChooser(intent,
                mFragment.getActivity().getString(R.string.select_image)), SELECT_PICTURE);
    }

    public void validateFields(User updateUser, String username, String email, String oldPassword, String newPassword) {

        //validate email
        if (TextUtils.isEmpty(email)) {
            mView.invalidateEmail();
            return;
        }

        if (!TextUtils.isEmpty(oldPassword) && !TextUtils.isEmpty(oldPassword)) {
            performChangePassword(email, oldPassword, newPassword);
        } else if (TextUtils.isEmpty(oldPassword) && !TextUtils.isEmpty(oldPassword) ||
                !TextUtils.isEmpty(oldPassword) && TextUtils.isEmpty(oldPassword)) {
            mView.invalidatePasswords();
            return;
        }

        if (!TextUtils.isEmpty(oldPassword) && !TextUtils.isEmpty(email)) {
            performChangeEmail(updateUser, oldPassword, email);
        }

        if (!TextUtils.isEmpty(username)) {
            updateUser(updateUser, username, null);
        }

        //TODO
        //mFragment.showMessage(R.string.account_saved_message);

    }

    public User loadUserInfo(final Context context) {
        User user = null;
        final String lastUserUid = SharedPrefsUtil.getLastUserUid(context);
        if (!TextUtils.isEmpty(lastUserUid)) {
            final UserDAO userDAO = UserDAO.getInstance();
            user = userDAO.findByUid(lastUserUid);
        }
        return user;
    }

    public void performChangePassword(final String email, final String oldPassword, final String newPassword) {
        //mView.showProgressDialog(R.string.loading_register);
        final Firebase firebase = new Firebase(ConstantsUtil.FIREBASE_URL);
        firebase.changePassword(email, oldPassword, newPassword, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Log.i("Sucesso", "Sucesso ao trocar a senha");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                //TODO feedback para o usuario
                //mView.hideProgressDialog();

                Log.e("ERRO:", firebaseError.getDetails() + "\n\n" + firebaseError.getMessage());
            }
        });
    }

    public void performChangeEmail(final User user, final String password, final String newEmail) {
        //mView.showProgressDialog(R.string.loading_register);
        final Firebase firebase = new Firebase(ConstantsUtil.FIREBASE_URL);
        firebase.changeEmail(user.getEmail(), password, newEmail, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                updateUser(user, null, newEmail);
                Log.i("Sucesso", "Sucesso ao trocar o email");
            }

            @Override
            public void onError(FirebaseError firebaseError) {

                Log.e("ERRO:", firebaseError.getDetails() + "\n\n" + firebaseError.getMessage());

                //TODO feedback para o usuario
                //mView.hideProgressDialog();
            }
        });
    }

    public void saveImage(User user, Uri imageUri, ContentResolver resolver, Context context) {
        try {
            final Bitmap bitmap = StorageUtil.getThumbnail(imageUri, resolver, 100);
            String imageToStorage = StorageUtil.saveImageToStorage(bitmap, context);
            updateUserImageProfile(user, imageToStorage);
        } catch (IOException e) {
            //TODO send an error message to the user
            Log.e(TAG, "IOException");
        }
    }

    private void updateUser(User user, String username, String email) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();

        if (!TextUtils.isEmpty(username))
            user.setUsername(username);

        if (!TextUtils.isEmpty(email))
            user.setEmail(email);

        realm.commitTransaction();
        mView.reloadNavigationHeader();
    }

    private void updateUserImageProfile(final User user, final String imagePath) {
        final Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();

        if (!TextUtils.isEmpty(imagePath))
            user.setImagePath(imagePath);

        realm.commitTransaction();
        mView.reloadNavigationHeader();
    }

}