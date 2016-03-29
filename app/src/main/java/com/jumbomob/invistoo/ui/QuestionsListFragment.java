package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.component.DividerItemDecorator;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class QuestionsListFragment extends Fragment {

    private View mRootView;

    public static QuestionsListFragment newInstance() {
        QuestionsListFragment fragment = new QuestionsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_questions_list, container, false);

        configureRecyclerView();

        return mRootView;
    }

    private void configureRecyclerView() {

        //final InvestmentDAO dao = InvestmentDAO.getInstance();
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .questions_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

//        InvestmentListAdapter mAdapter = new InvestmentListAdapter(dao.findAll());
//        recyclerView.setAdapter(mAdapter);
    }


}
