package com.jumbomob.invistoo.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.presenter.AboutPresenter;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.view.AboutView;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class AboutFragment extends BaseFragment implements AboutView {

    private static final String TAG = AboutFragment.class.getSimpleName();

    private View mRootView;
    private AboutPresenter mPresenter;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_about, container, false);

        mPresenter = new AboutPresenter(this);
        configureElements();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_about);
    }

    private void configureElements() {

        final LinearLayout rateUsContainer = (LinearLayout) mRootView.findViewById(R.id.rate_us_container);
        rateUsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGooglePlay();
            }
        });

        final LinearLayout suggestionContainer = (LinearLayout) mRootView.findViewById(R.id.send_suggestion);
        suggestionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = getString(R.string.email_suggestion);
                String intentType = "text/html";
                String mailToParse = "mailto:" + ConstantsUtil.DEVELOPER_EMAIL;
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType(intentType);
                intent.setData(Uri.parse(mailToParse));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(intent, getString(R.string.send_suggestion)));
            }
        });

        final LinearLayout licensesContainer = (LinearLayout) mRootView.findViewById(R.id.licenses_container);


    }

    private void openGooglePlay() {
        Uri uri = Uri.parse("market://details?id=" + mRootView.getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mRootView.getContext().getPackageName())));
        }
    }
}