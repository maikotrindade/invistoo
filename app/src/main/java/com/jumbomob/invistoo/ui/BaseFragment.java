package com.jumbomob.invistoo.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.jumbomob.invistoo.util.DialogUtil;
import com.jumbomob.invistoo.util.InvistooUtil;
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

    public void showMessage(final String message, final int length) {
        InvistooUtil.makeSnackBar(getActivity(), message, length).show();
    }

    public void showMessage(final String message) {
        showMessage(message, Snackbar.LENGTH_LONG);
    }

    public void showMessage(final int resourceId) {
        showMessage(getString(resourceId), Snackbar.LENGTH_LONG);
    }

    public void showDialog(final int titleResourceId, final int messageResourceId) {
        DialogUtil.getInstance(getActivity()).show(getActivity(), getString(titleResourceId),
                getText(messageResourceId).toString());
    }

}
