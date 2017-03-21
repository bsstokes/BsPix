package com.bsstokes.bspix.app.media_item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bsstokes.bspix.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaItemActivity extends AppCompatActivity {

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull String mediaItemId) {
        final Intent intent = new Intent(context, MediaItemActivity.class);
        intent.putExtra(EXTRA_MEDIA_ITEM_ID, mediaItemId);
        return intent;
    }

    private static final String EXTRA_MEDIA_ITEM_ID = "EXTRA_MEDIA_ITEM_ID";

    @BindView(R.id.media_item_id_text_view) TextView mediaItemIdTextView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_item_activity);
        ButterKnife.bind(this);

        final String mediaItemId = getIntent().getStringExtra(EXTRA_MEDIA_ITEM_ID);
        mediaItemIdTextView.setText(mediaItemId);
    }
}
