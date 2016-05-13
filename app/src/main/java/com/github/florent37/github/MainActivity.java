package com.github.florent37.github;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.github.florent37.github.repo.ListRepoFragment;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindArray(R.array.accounts) String[] accounts;

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

        Menu navMenu = navigationView.getMenu();
        navMenu.clear();
        SubMenu accountsMenu = navMenu.addSubMenu("Accounts");

        for (String account : accounts) {
            accountsMenu.add(account);
        }
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener((actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0)));

        displayStats(accounts[0]);
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
        setTitle("Github " + userName);
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, ListRepoFragment.newInstance(userName), tag)
                .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        String text = menuItem.getTitle().toString();
        displayStats(text);
        return closeDrawer();
    }

    protected boolean closeDrawer() {
        drawerLayout.postDelayed(() -> drawerLayout.closeDrawer(Gravity.LEFT), 500);
        return true;
    }
}
