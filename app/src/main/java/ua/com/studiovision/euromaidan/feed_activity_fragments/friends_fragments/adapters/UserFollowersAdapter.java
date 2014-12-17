package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.FriendsFragmentCallbacks;
import ua.com.studiovision.euromaidan.network.provider.followers.FollowersCursor;
import ua.com.studiovision.euromaidan.search_fragments.adapters.CursorRecyclerAdapter;

public class UserFollowersAdapter extends CursorRecyclerAdapter<UserFollowersAdapter.ViewHolder, FollowersCursor> {

    private static final String TAG = "FriendsRequestsAdapter";

    private ImageLoader imageLoader = ImageLoader.getInstance();
    FriendsFragmentCallbacks callbacks;
    Context context;

    public UserFollowersAdapter(FollowersCursor cursor, Context context, FriendsFragmentCallbacks callbacks) {
        super(cursor, context);
        this.callbacks = callbacks;
        this.context = context;
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, FollowersCursor cursor) {
        holder.followerId = cursor.getFollowerId();
        holder.followerFullName.setText(cursor.getFollowerName() + " " + cursor.getFollowerSurname());
        imageLoader.displayImage(cursor.getFollowerAvatar(), holder.avatar);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
        return new UserFollowersAdapter.ViewHolder(v, callbacks);
    }

    public long getFollowerId(int position) {
        if (mDataValid && mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getFollowerId();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        long followerId;
        ImageView avatar;
        TextView followerFullName;
        FriendsFragmentCallbacks callbacks;

        public ViewHolder(View itemView, FriendsFragmentCallbacks callbacks) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            followerFullName = (TextView) itemView.findViewById(R.id.user_name_textview);
            this.callbacks = callbacks;
        }
    }
}