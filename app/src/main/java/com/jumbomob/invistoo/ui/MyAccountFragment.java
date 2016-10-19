package com.jumbomob.invistoo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.presenter.MyAccountPresenter;
import com.jumbomob.invistoo.util.PermissionUtil;
import com.jumbomob.invistoo.util.StorageUtil;
import com.jumbomob.invistoo.view.MyAccountView;

/**
 * @author maiko.trindade
 * @since 16/10/2016
 */
public class MyAccountFragment extends BaseFragment implements MyAccountView {

    private View mRootView;
    private MyAccountPresenter mPresenter;
    private ImageView mUserImg;
    private EditText mNameEdtText, mEmailEdtText, mPasswordEdtText, mPasswordConfirmationEdtText;
    private RelativeLayout mUserImgContainer;
    private User mUser;
    private Button mSendButton;

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_my_account, container, false);
        mPresenter = new MyAccountPresenter(this, this);

        mUser = mPresenter.loadUserInfo(getContext());
        configureElements();
        configureListeners();
        requestPermission();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_my_account);
    }

    private void configureElements() {
        mUserImgContainer = (RelativeLayout) mRootView.findViewById(R.id.user_image_container);
        mSendButton = (Button) mRootView.findViewById(R.id.send_button);
        mNameEdtText = (EditText) mRootView.findViewById(R.id.name_edit_text);
        mEmailEdtText = (EditText) mRootView.findViewById(R.id.email_edit_text);
        mPasswordEdtText = (EditText) mRootView.findViewById(R.id.password_edit_text);
        mPasswordConfirmationEdtText = (EditText) mRootView.findViewById(R.id.password_confirmation_edit_text);
        mUserImg = (ImageView) mRootView.findViewById(R.id.profile_image);

        if (mUser != null) {
            if (!TextUtils.isEmpty(mUser.getUsername())) {
                mNameEdtText.setText(mUser.getUsername());
            }
            if (!TextUtils.isEmpty(mUser.getEmail())) {
                mEmailEdtText.setText(mUser.getEmail());
            }
            if (!TextUtils.isEmpty(mUser.getImagePath())) {
                final Bitmap bitmap = StorageUtil.getBitmap(mUser.getImagePath(), getContext());
                mUserImg.setImageBitmap(bitmap);
            }
        } else {

            //TODO

        }
    }

    private void configureListeners() {
        mUserImgContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openImagePicker();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mNameEdtText.getText().toString();
                final String email = mEmailEdtText.getText().toString();
                final String password = mPasswordEdtText.getText().toString();
                final String passwordConfirmation = mPasswordConfirmationEdtText.getText().toString();

                if (mPresenter.validateFields(name, email, password, passwordConfirmation)) {
                    mUser.setUsername(name);
                    mUser.setEmail(email);
                    mPresenter.updateUser(mUser);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyAccountPresenter.SELECT_PICTURE) {
                final Uri selectedImageUri = data.getData();
                if (selectedImageUri != null && mUser != null) {
                    final ContentResolver resolver = getActivity().getContentResolver();
                    mPresenter.saveImage(mUser, selectedImageUri, resolver, getContext());
                    mUserImg.setImageURI(selectedImageUri);
                } else {
                    //TODO add error feedback to the user
                }
            }
        }
    }

    private void requestPermission() {
        final String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!PermissionUtil.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionUtil.request(this, PERMISSIONS_STORAGE, StorageUtil.REQUEST_EXTERNAL_STORAGE);
        }
    }


}
