package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.view.NewInvestmentListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewInvestmentListPresenter implements BasePresenter<NewInvestmentListView> {

    private final static String TAG = NewInvestmentListPresenter.class.getSimpleName();

    private NewInvestmentListView mView;

    @Override
    public void attachView(NewInvestmentListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewInvestmentListPresenter(NewInvestmentListView view) {
        attachView(view);
    }

    public void saveInvestments(List<InvestmentSuggestionDTO> suggestions) {
        List<Investment> investments = new ArrayList<>();
        for (InvestmentSuggestionDTO suggestion : suggestions) {
            Investment investment = new Investment();
            investment.setCreationDate(new Date());
            investment.setPrice(suggestion.getSuggestion().toString());
            investment.setUpdateDate(new Date());

            final AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(suggestion.getAssetType());
            investment.setName(assetTypeEnum.getTitle());
            investment.setAssetType(assetTypeEnum);
            investment.setAssetStatus(AssetStatusEnum.BUY);
            investment.setActive(true);
            investment.setUserId(InvistooApplication.getLoggedUser().getUid());
            investments.add(investment);
        }
        InvestmentDAO.getInstance().insert(investments, InvistooApplication.getLoggedUser().getUid());

        mView.showMessage(R.string.msg_save_investment);
        mView.navigateToInvestmentList();
    }

}