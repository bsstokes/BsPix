package com.bsstokes.bspix.app.media_item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaItemActivity extends AppCompatActivity implements MediaItemController.View {

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull String mediaItemId) {
        final Intent intent = new Intent(context, MediaItemActivity.class);
        intent.putExtra(EXTRA_MEDIA_ITEM_ID, mediaItemId);
        return intent;
    }

    private static final String TAG = "MediaItemActivity";
    private static final String EXTRA_MEDIA_ITEM_ID = "EXTRA_MEDIA_ITEM_ID";

    @Inject BsPixDatabase bsPixDatabase;
    @Inject InstagramApi instagramApi;
    @Inject Picasso picasso;

    @BindView(R.id.media_item_image_view) ImageView mediaItemImageView;
    @BindView(R.id.user_name_text_view) TextView userNameTextView;
    @BindView(R.id.media_item_caption_text_view) TextView mediaItemCaptionTextView;
    @BindView(R.id.location_text_view) TextView locationTextView;
    @BindView(R.id.like_button) ImageButton likeButton;

    @Nullable private String mediaItemId;
    private MediaItemController mediaItemController;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_item_activity);
        ButterKnife.bind(this);

        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        mediaItemId = getIntent().getStringExtra(EXTRA_MEDIA_ITEM_ID);
        mediaItemController = new MediaItemController(this, bsPixDatabase, new LikeAction(bsPixDatabase, instagramApi));
    }

    @Override protected void onResume() {
        super.onResume();
        mediaItemController.load(mediaItemId);
    }

    @Override protected void onPause() {
        super.onPause();
        mediaItemController.unload();
    }

    @Override public void loadPhoto(@NonNull String photoUrl) {
        picasso.load(photoUrl).into(mediaItemImageView);
    }

    @Override public void setUserName(@Nullable String userName) {
        userNameTextView.setText(userName);
    }

    @Override public void setCaption(@Nullable String caption) {
        mediaItemCaptionTextView.setText(caption);
    }

    @Override public void setLocation(@Nullable String location) {
        locationTextView.setText(location);
    }

    @Override public void logErrorAndFinish(@NonNull String message) {
        Log.e(TAG, message);
        finish();
    }

    @Override public void setIsLiked(boolean isLiked) {
        final VectorDrawableCompat icon = VectorDrawableCompat.create(getResources(), iconId(isLiked), null);
        likeButton.setImageDrawable(icon);
    }

    @DrawableRes private int iconId(boolean isLiked) {
        return isLiked ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp;
    }

    @OnClick(R.id.like_button)
    void onClickLikeButton() {
        mediaItemController.onClickLikeButton(mediaItemId);
    }
}
