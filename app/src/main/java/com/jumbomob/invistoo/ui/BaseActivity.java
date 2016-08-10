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
            case R.id.nav_home:
                setFragment(HomeFragment.newInstance(), R.id.nav_home, getString
                        (R.string.title_home));
                break;
            case R.id.nav_my_wallet:
                setFragment(InvestmentsListFragment.newInstance(), R.id.nav_my_wallet,
                        getString(R.string.my_investments));
                break;
            case R.id.nav_indexes:
                setFragment(AssetListFragment.newInstance(), R.id.nav_indexes,
                        getString(R.string.title_indexes));
                break;
            case R.id.nav_brokerage:
                setFragment(BrokerageListFragment.newInstance(), R.id.nav_brokerage,
                        getString(R.string.title_brokerage));
                break;
            case R.id.nav_useful_information:
                setFragment(UsefulInformationListFragment.newInstance(), R.id
                        .nav_useful_information, getString(R.string.title_useful_information));
                break;
            case R.id.nav_settings:
                setFragment(SettingsFragment.newInstance(), R.id.nav_settings,
                        getString(R.string.title_settings));
                break;
            case R.id.nav_about:
                setFragment(AboutFragment.newInstance(), R.id.nav_about,
                        getString(R.string.title_about));
                break;
            case R.id.nav_logout:
                //TODO temporarily
                finish();
        }

        return true;
    }

    protected void setFragment(final Fragment fragment, final int navigationItemId,
                               final String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
        setTitle(title);
        mNavigationView.setCheckedItem(navigationItemId);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setFragment(final Fragment fragment, final String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
        setTitle(title);
    }

    public void goBackFragment() {
        //getFragmentManager().popBackStack();
//        getFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().popBackStack();
    }
}
