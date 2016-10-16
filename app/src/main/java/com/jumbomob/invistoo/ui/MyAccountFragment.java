package com.jumbomob.invistoo.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.presenter.MyAccountPresenter;
import com.jumbomob.invistoo.view.MyAccountView;

/**
 * @author maiko.trindade
 * @since 16/10/2016
 */
public class MyAccountFragment extends BaseFragment implements MyAccountView {

    private View mRootView;
    private MyAccountPresenter mPresenter;
    private ImageView userImg;
    private EditText nameEdtText, emailEdtText, passwordEdtText, passwordConfirmationEdtText;
    private RelativeLayout userImgContainer;
    private Button sendButton;

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_my_account, container, false);
        mPresenter = new MyAccountPresenter(this, this);

        bindElements();
        configureListeners();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_my_account);
    }

    private void bindElements() {
        userImgContainer = (RelativeLayout) mRootView.findViewById(R.id.user_image_container);
        sendButton = (Button) mRootView.findViewById(R.id.send_button);
        nameEdtText = (EditText) mRootView.findViewById(R.id.name_edit_text);
        emailEdtText = (EditText) mRootView.findViewById(R.id.email_edit_text);
        passwordEdtText = (EditText) mRootView.findViewById(R.id.password_edit_text);
        passwordConfirmationEdtText = (EditText) mRootView.findViewById(R.id.password_confirmation_edit_text);
        userImg = (ImageView) mRootView.findViewById(R.id.profile_image);
    }

    private void configureListeners() {
        userImgContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openImagePicker();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameEdtText.getText().toString();
                final String email = emailEdtText.getText().toString();
                final String password = passwordEdtText.getText().toString();
                final String passwordConfirmation = passwordConfirmationEdtText.getText().toString();

                mPresenter.validateFields(name, email, password, passwordConfirmation);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyAccountPresenter.SELECT_PICTURE) {
                final Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    userImg.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
