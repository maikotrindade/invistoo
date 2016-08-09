package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.view.Menu;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.view.MainView;

public class MainActivity extends BaseActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragment(HomeFragment.newInstance(), R.id.nav_home, getString(R.string.title_home));
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    

}
