package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 06/02/2016
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    public abstract int setContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_dashboard:
                break;
            case R.id.nav_my_wallet:
                setFragment(InvestmentsListFragment.newInstance(), R.id.nav_my_wallet, getString
                        (R.string.title_my_wallet));
                break;
            case R.id.nav_indexes:
                setFragment(AssetListFragment.newInstance(), R.id.nav_indexes, getString(R.string
                        .title_list_assets));
                break;
            case R.id.nav_brokerage:
                break;
            case R.id.nav_useful_information:
                setFragment(QuestionsListFragment.newInstance(), R.id.nav_useful_information,
                        getString(R.string.title_useful_information));
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_logout:
        }

        return true;
    }

    protected void setFragment(final Fragment fragment, final int id, final String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
        setTitle(title);
        mNavigationView.setCheckedItem(id);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    protected void setFragment(final Fragment fragment, final String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
        setTitle(title);
    }

}
