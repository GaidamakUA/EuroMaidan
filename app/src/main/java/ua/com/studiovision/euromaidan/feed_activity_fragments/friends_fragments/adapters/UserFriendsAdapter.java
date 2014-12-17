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
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsCursor;
import ua.com.studiovision.euromaidan.search_fragments.adapters.CursorRecyclerAdapter;

public class UserFriendsAdapter extends CursorRecyclerAdapter<UserFriendsAdapter.ViewHolder, FriendsCursor> {

    private static final String TAG = "FriendsRequestsAdapter";

    private ImageLoader imageLoader = ImageLoader.getInstance();
    FriendsFragmentCallbacks callbacks;
    Context context;

    public UserFriendsAdapter(FriendsCursor cursor, Context context, FriendsFragmentCallbacks callbacks) {
        super(cursor, context);
        this.callbacks = callbacks;
        this.context = context;
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, FriendsCursor cursor) {
        holder.friendId = cursor.getFriendId();
        holder.friendFullName.setText(cursor.getFriendName() + " " + cursor.getFriendSurname());
        imageLoader.displayImage(cursor.getFriendAvatar(), holder.avatar);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friends_and_followers_list_row, viewGroup, false);
        return new UserFriendsAdapter.ViewHolder(v, callbacks);
    }

    public long getFriendId(int position) {
        if (mDataValid && mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getFriendId();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        long friendId;
        ImageView avatar;
        TextView friendFullName;
        FriendsFragmentCallbacks callbacks;

        public ViewHolder(View itemView, FriendsFragmentCallbacks callbacks) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.user_avatar_imageview);
            friendFullName = (TextView) itemView.findViewById(R.id.user_name_textview);
            this.callbacks = callbacks;
        }
    }
}