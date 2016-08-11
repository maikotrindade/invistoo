package com.jumbomob.invistoo.ui;

import android.support.v4.app.Fragment;

import com.jumbomob.invistoo.util.ProgressDialogUtil;

/**
 * @author maiko.trindade
 * @since 10/08/2016
 */
public class BaseFragment extends Fragment {

    public void showProgressDialog(final int resourceId) {
        showProgressDialog(getString(resourceId));
    }

    public void showProgressDialog(final String message) {
        ProgressDialogUtil.getInstance().showProgressDialog(getContext(), message);
    }

    public void hideProgressDialog() {
        ProgressDialogUtil.getInstance().hideProgressDialog();
    }

}
