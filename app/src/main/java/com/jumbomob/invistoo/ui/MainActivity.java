package com.jumbomob.invistoo.ui;

import android.os.Bundle;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.view.MainView;

public class MainActivity extends BaseActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragmentWithStack(DashboardFragment.newInstance(), getString(R.string.title_dashboard));
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}