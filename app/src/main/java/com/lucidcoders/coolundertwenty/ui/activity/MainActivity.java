package com.lucidcoders.coolundertwenty.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.lucidcoders.coolundertwenty.R;
import com.lucidcoders.coolundertwenty.network.ApiManager;
import com.lucidcoders.coolundertwenty.object.Category;
import com.lucidcoders.coolundertwenty.ui.fragment.CategoryFragment;
import com.lucidcoders.coolundertwenty.util.Util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<Category> mCategories = new ArrayList<>();

    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView sideMenuIcon = (ImageView) findViewById(R.id.main_side_menu_icon);
        if (sideMenuIcon != null) {
            sideMenuIcon.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mDrawer.isDrawerOpen(GravityCompat.END)) {
                                mDrawer.closeDrawer(GravityCompat.END);
                            } else {
                                mDrawer.openDrawer(GravityCompat.END);
                            }
                        }
                    }
            );
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        makeCategoriesRequest();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void makeCategoriesRequest() {
        ApiManager.getCategories(
                new ApiManager.RequestListener<List<Category>>() {
                    @Override
                    public void onFinished(List<Category> responseObject) {
                        if (Util.checkList(responseObject)) {
                            mCategories = responseObject;
                            setupTabs();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                }
        );
    }

    private void setupTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        CategoriesPagerAdapter pagerAdapter = new CategoriesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class CategoriesPagerAdapter extends FragmentPagerAdapter {

        public CategoriesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            return CategoryFragment.newInstance(mCategories.get(num));
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getName();
        }
    }
}
