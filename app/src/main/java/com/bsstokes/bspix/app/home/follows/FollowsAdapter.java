package com.bsstokes.bspix.app.home.follows;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.data.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class FollowsAdapter extends RecyclerView.Adapter<FollowsAdapter.ViewHolder> {

    interface OnClickListener {
    }

    @NonNull private final List<User> follows = new ArrayList<>();
    @NonNull private final OnClickListener onClickListener;
    @NonNull private final Picasso picasso;

    FollowsAdapter(@NonNull OnClickListener onClickListener, @NonNull Picasso picasso) {
        this.onClickListener = onClickListener;
        this.picasso = picasso;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follows_item, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = follows.get(position);
        holder.bind(user);
    }

    @Override public int getItemCount() {
        return follows.size();
    }

    void setFollows(@NonNull List<User> follows) {
        this.follows.clear();
        this.follows.addAll(follows);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_picture_image_view) ImageView profilePictureImageView;
        @BindView(R.id.user_name_text_view) TextView userNameTextView;
        @BindView(R.id.full_name_text_view) TextView fullNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    
                }
            });
        }

        void bind(@NonNull User user) {
            picasso.load(user.profilePicture()).into(profilePictureImageView);
            userNameTextView.setText(user.userName());
            fullNameTextView.setText(user.fullName());
        }
    }
}
