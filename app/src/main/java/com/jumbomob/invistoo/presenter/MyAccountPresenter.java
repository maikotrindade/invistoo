package com.jumbomob.invistoo.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.model.persistence.UserDAO;
import com.jumbomob.invistoo.ui.MyAccountFragment;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.util.StorageUtil;
import com.jumbomob.invistoo.view.MyAccountView;

import java.io.IOException;

/**
 * @author maiko.trindade
 * @since 16/10/2016
 */
public class MyAccountPresenter implements BasePresenter<MyAccountView> {

    private MyAccountView mView;
    private MyAccountFragment mFragment;
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

    public MyAccountPresenter(MyAccountFragment fragment, MyAccountView view) {
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

    public boolean validateFields(String name, String email, String password, String passwordConfirmation) {
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            //mView.invalidateEmail();
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password)) {
            int errorMessageId;
            //mView.invalidatePassword(errorMessage);
        }

        //TODO
        mFragment.showMessage(R.string.account_saved_message);

        return valid;
    }

    public void updateUser(User user) {
        final UserDAO userDAO = UserDAO.getInstance();
        userDAO.insertOrUpdate(user);

        //TODO Google here!

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

    public void saveImage(User user, Uri imageUri, ContentResolver resolver, Context context) {
        try {
            final Bitmap bitmap = StorageUtil.getThumbnail(imageUri, resolver, 100);
            String imageToStorage = StorageUtil.saveImageToStorage(bitmap, context);
            //user.setImagePath(imageToStorage);
        } catch (IOException e) {
            //TODO send an error message to the user
            Log.e(TAG, "IOException");
        }
    }

}