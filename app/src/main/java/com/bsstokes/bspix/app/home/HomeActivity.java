package com.bsstokes.bspix.app.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.auth.Account;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.sync.SyncService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeController.View {

    @BindView(R.id.profilePictureImageView) ImageView profilePictureImageView;
    @BindView(R.id.nameTextView) TextView nameTextView;
    @BindView(R.id.bioTextView) TextView bioTextView;
    @BindView(R.id.websiteTextView) TextView websiteTextView;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.view_pager_tab_layout) TabLayout viewPagerTabLayout;

    // Counts
    @BindView(R.id.postsCountTextView) TextView postsCountTextView;
    @BindView(R.id.followersCountTextView) TextView followersCountTextView;
    @BindView(R.id.followingCountTextView) TextView followingCountTextView;

    @Inject Account account;
    @Inject BsPixDatabase bsPixDatabase;
    @Inject Picasso picasso;

    private HomeController homeController;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homeViewPagerAdapter);
        viewPagerTabLayout.setupWithViewPager(viewPager);

        homeController = new HomeController(this, bsPixDatabase);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.log_out_menu_item == item.getItemId()) {
            account.logOut();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        homeController.loadSelf();
        SyncService.startActionSyncSelf(this);
    }

    @Override protected void onPause() {
        super.onPause();
        homeController.unload();
    }

    @Override public void loadProfilePicture(String profilePictureUrl) {
        picasso.load(profilePictureUrl).into(profilePictureImageView);
    }

    @Override public void setName(String name) {
        nameTextView.setText(name);
    }

    @Override public void setBio(String bio) {
        bioTextView.setText(bio);
    }

    @Override public void setWebsite(String website) {
        websiteTextView.setText(website);
    }

    @Override public void setCounts(int posts, int followers, int following) {
        postsCountTextView.setText(String.valueOf(posts));
        followersCountTextView.setText(String.valueOf(followers));
        followingCountTextView.setText(String.valueOf(following));
    }
}
