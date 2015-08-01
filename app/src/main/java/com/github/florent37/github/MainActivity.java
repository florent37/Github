package com.github.florent37.github;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.github.florent37.carpaccio.Carpaccio;
import com.github.florent37.github.event.ListEventFragment;
import com.github.florent37.github.repo.ListRepoFragment;
import com.github.florent37.github.user.User;
import com.github.florent37.github.user.UserManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    GithubAPI githubAPI;

    @Inject
    UserManager userManager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawerCarpaccio)
    Carpaccio drawerCarpaccio;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigationView)
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application.getApplication().getGithubComponent().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.setDrawerListener((actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        userManager.onStart(this);

        final User savedUser = userManager.load();
        if (savedUser != null)
            displayUser(savedUser);
        else
            githubAPI.user("Florent37")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(throwable -> {
                        Log.e("user", throwable.getMessage(), throwable);
                        return null;
                    })
                    .doOnError(throwable -> Log.e("user", throwable.getMessage(), throwable))
                    .map(user -> userManager.setUser(user))
                    .subscribe(user -> {
                        if (user != null) {
                            displayUser(user);
                        }
                    });
    }

    @Override
    protected void onStop() {
        super.onStop();
        userManager.onStop();
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

    protected boolean closeDrawer() {
        drawerLayout.postDelayed(() -> drawerLayout.closeDrawer(Gravity.LEFT), 500);
        return true;
    }

    public void displayUser(User user) {
        drawerCarpaccio.mapObject("user", user);
        displayStats();
    }

    public void displayStats() {
        if (getSupportFragmentManager().findFragmentByTag("ListRepoFragment") == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, ListRepoFragment.newInstance(), "ListRepoFragment")
                    .commit();
    }

    public void displayEvents() {
        if (getSupportFragmentManager().findFragmentByTag("ListEventFragment") == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, ListEventFragment.newInstance(), "ListEventFragment")
                    .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.stats:
                displayStats();
                return closeDrawer();
            case R.id.events:
                displayEvents();
                return closeDrawer();
        }
        return false;
    }
}
