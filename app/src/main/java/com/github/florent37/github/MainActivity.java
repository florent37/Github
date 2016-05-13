package com.github.florent37.github;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.github.florent37.github.repo.ListRepoFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USER_FLORENT37 = "florent37";
    public static final String USER_MEETIC = "meetic";

    @Inject
    GithubAPI githubAPI;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigationView)
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application.app().component().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener((actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0)));

        displayStats(USER_FLORENT37);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void displayStats(String userName) {
        String tag = "ListRepoFragment" + userName;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, ListRepoFragment.newInstance(userName), tag)
                .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.florent37:
                displayStats(USER_FLORENT37);
                return closeDrawer();
            case R.id.meetic:
                displayStats(USER_MEETIC);
                return closeDrawer();
        }
        return false;
    }

    protected boolean closeDrawer() {
        drawerLayout.postDelayed(() -> drawerLayout.closeDrawer(Gravity.LEFT), 500);
        return true;
    }
}
