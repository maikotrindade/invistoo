package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.NewInvestmentView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewInvestmentPresenter implements BasePresenter<NewInvestmentView> {

    private NewInvestmentView mView;

    @Override
    public void attachView(NewInvestmentView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewInvestmentPresenter(NewInvestmentView view) {
        attachView(view);
    }


    public boolean isValidFields(String price, String quantity, String assetTitle) {

        boolean isValid = true;
        if (price.isEmpty()) {
            mView.setPriceError();
            isValid = false;
        }

        if (quantity.isEmpty()) {
            mView.setQuantityError();
            isValid = false;
        }

        if (assetTitle.isEmpty()) {
            isValid = false;
        }

        return isValid;
    }
}