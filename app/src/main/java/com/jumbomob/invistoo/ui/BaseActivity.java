package com.jumbomob.invistoo.ui;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.ui.component.CircleImageView;
import com.jumbomob.invistoo.util.DialogUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.util.StorageUtil;

/**
 * @author maiko.trindade
 * @since 06/02/2016
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;

    public abstract int setContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFragmentManager = getSupportFragmentManager();
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        configureNavigationHeader();

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (mFragmentManager.getBackStackEntryCount() == 1) {
                showQuitDialog();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void showQuitDialog() {
        final BaseActivity baseActivity = this;
        DialogUtil.getInstance(baseActivity).show(baseActivity, R.string.app_name,
                R.string.quit_message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        baseActivity.finishAffinity();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_dashboard:
                setFragmentWithStack(DashboardFragment.newInstance(), getString
                        (R.string.title_dashboard));
                break;
            case R.id.nav_investments:
                setFragmentWithStack(InvestmentsListFragment.newInstance(), getString(R.string.my_investments));
                break;
            case R.id.nav_assets:
                setFragmentWithStack(AssetListFragment.newInstance(), getString(R.string.title_indexes));
                break;
            case R.id.nav_useful_information:
                setFragmentWithStack(QuestionListFragment.newInstance(), getString(R.string.title_useful_information));
                break;
            case R.id.nav_goals:
                setFragmentWithStack(GoalListFragment.newInstance(), getString(R.string.title_goals));
                break;
            case R.id.nav_about:
                setFragmentWithStack(AboutFragment.newInstance(), getString(R.string.title_about));
                break;
            case R.id.nav_account:
                setFragmentWithStack(AccountFragment.newInstance(), getString(R.string.title_account));
                break;
            case R.id.nav_logout:
                logoutAccount();
                finish();
        }

        return true;
    }

    public void setFragmentWithStack(final Fragment fragment, final int titleResourceId) {
        setFragmentWithStack(fragment, getString(titleResourceId != 0 ? titleResourceId : R.string.app_name));
    }

    protected void setFragmentWithStack(final Fragment fragment, final String title) {
        final String fragmentTag = fragment.getClass().getName();
        mFragmentManager.beginTransaction()
                .replace(R.id.content, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out)
                .commit();
        mFragmentManager.executePendingTransactions();
        setTitle(title);
        InvistooUtil.hideKeyboard(this);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void updateNavigationDrawer(int navigationItem) {
        mNavigationView.setCheckedItem(navigationItem);
    }

    public void goBackFragment() {
        getSupportFragmentManager().popBackStack();
    }

    public void setCustomToolbar(final int titleResourceId, final String subtitle) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView titleView = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        titleView.setText(getString(titleResourceId));
        titleView.setVisibility(View.VISIBLE);
        TextView subtitleView = (TextView) mToolbar.findViewById(R.id.toolbar_subtitle);
        subtitleView.setText(subtitle);
        subtitleView.setVisibility(View.VISIBLE);
    }

    public void setDefaultToolbar() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView titleView = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        titleView.setVisibility(View.GONE);
        TextView subtitleView = (TextView) mToolbar.findViewById(R.id.toolbar_subtitle);
        subtitleView.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        boolean isRemember = SharedPrefsUtil.isRememberUser(getBaseContext());
        if (!isRemember) {
            SharedPrefsUtil.setUserLogged(false);
            SharedPrefsUtil.setRememberUser(false);
        }
    }

    private void logoutAccount() {
        SharedPrefsUtil.setUserLogged(false);
    }

    public void configureNavigationHeader() {
        final View headerLayout = mNavigationView.getHeaderView(0);
        final User user = InvistooApplication.getLoggedUser();
        if (user != null && headerLayout != null) {

            final LinearLayout container = (LinearLayout) headerLayout.findViewById(R.id.user_info_container);
            final CircleImageView profileImage = (CircleImageView) headerLayout.findViewById(R.id.profile_image);
            final TextView usernameTxtView = (TextView) headerLayout.findViewById(R.id.username_text_view);
            final TextView emailTxtView = (TextView) headerLayout.findViewById(R.id.email_edit_text);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setFragmentWithStack(AccountFragment.newInstance(), getString(R.string.title_account));
                }
            });

            if (!TextUtils.isEmpty(user.getImagePath())) {
                final Bitmap bitmap = StorageUtil.getBitmap(user.getImagePath(), getBaseContext());
                profileImage.setImageBitmap(bitmap);
            }

            if (!TextUtils.isEmpty(user.getEmail())) {
                emailTxtView.setVisibility(View.VISIBLE);
                emailTxtView.setText(user.getEmail());
            } else {
                emailTxtView.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(user.getUsername())) {
                usernameTxtView.setVisibility(View.VISIBLE);
                usernameTxtView.setText(user.getUsername());
            } else {
                usernameTxtView.setVisibility(View.GONE);
            }
        }
    }
}
