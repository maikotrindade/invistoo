package com.jumbomob.invistoo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.ui.component.StepIndicator;

public class TutorialActivity extends AppCompatActivity {

    private final int STEP_1_FRAGMENT_ID = 0;
    private final int STEP_2_FRAGMENT_ID = 1;
    private final int STEP_3_FRAGMENT_ID = 2;
    private final int STEP_4_FRAGMENT_ID = 3;

    private final int NUMBER_OF_PAGES = 4;

    private LinearLayout mNextContainer;
    private LinearLayout mSkipContainer;
    private TextView mNextTextView;
    private ViewPager mTutorialViewPager;
    private StepIndicator mStepIndicator;
    private TutorialPagerAdapter mTutorialPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        configureElements();
    }

    private void configureElements() {
        mNextContainer = (LinearLayout) findViewById(R.id.next_button_container);
        mSkipContainer = (LinearLayout) findViewById(R.id.skip_container);
        mNextTextView = (TextView) findViewById(R.id.next_text_view);
        mTutorialViewPager = (ViewPager) findViewById(R.id.tutorial_view_pager);
        mStepIndicator = (StepIndicator) findViewById(R.id.step_indicator);

        mTutorialPageAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
        mTutorialViewPager.setAdapter(mTutorialPageAdapter);
        mStepIndicator.setupWithViewPager(mTutorialViewPager);
        mTutorialViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == STEP_4_FRAGMENT_ID) {
                    configureStartButton();
                } else {
                    configureNextButton();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSkipContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
        configureNextButton();
    }

    private void configureNextButton() {
        mNextTextView.setText(R.string.next);
        mNextContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTutorialViewPager.setCurrentItem(mTutorialViewPager.getCurrentItem() + 1, true);
            }
        });
    }

    private void configureStartButton() {
        mNextTextView.setText(R.string.start);
        mNextContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
    }

    public class TutorialPagerAdapter extends FragmentPagerAdapter {

        public TutorialPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case STEP_1_FRAGMENT_ID:
                    return TutorialStep1Fragment.newInstance();
                case STEP_2_FRAGMENT_ID:
                    return TutorialStep2Fragment.newInstance();
                case STEP_3_FRAGMENT_ID:
                    return TutorialStep3Fragment.newInstance();
                case STEP_4_FRAGMENT_ID:
                    return TutorialStep4Fragment.newInstance();
                default:
                    return TutorialStep1Fragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }
    }

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
