package com.jumbomob.invistoo.presenter;

import android.content.Intent;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.ui.MyAccountFragment;
import com.jumbomob.invistoo.view.MyAccountView;

/**
 * @author maiko.trindade
 * @since 16/10/2016
 */
public class MyAccountPresenter implements BasePresenter<MyAccountView> {

    private MyAccountView mView;
    private MyAccountFragment mFragment;
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

    public void validateFields(String name, String email, String password, String passwordConfirmation) {
        //TODO
        mFragment.showMessage(R.string.account_saved_message);
    }

}