package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.FriendsFragmentCallbacks;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantCursor;
import ua.com.studiovision.euromaidan.search_fragments.adapters.CursorRecyclerAdapter;

public class FriendsRequestsAdapter extends CursorRecyclerAdapter<FriendsRequestsAdapter.ViewHolder, ApplicantCursor> {

    private static final String TAG = "FriendsRequestsAdapter";

    private ImageLoader imageLoader = ImageLoader.getInstance();
    FriendsFragmentCallbacks callbacks;
    Context context;
    long currentUserId;

    public FriendsRequestsAdapter(ApplicantCursor cursor, Context context, FriendsFragmentCallbacks callbacks, long currentUserId) {
        super(cursor, context);
        this.callbacks = callbacks;
        this.context = context;
        this.currentUserId = currentUserId;
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, ApplicantCursor cursor) {
        holder.applicantId = cursor.getApplicantId();
        holder.applicantFullName.setText(cursor.getApplicantName() + " " + cursor.getApplicantSurname());
        imageLoader.displayImage(cursor.getApplicantAvatar(), holder.avatar);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friends_requests_list_row, viewGroup, false);
        return new FriendsRequestsAdapter.ViewHolder(v, callbacks);
    }

    public long getApplicantId(int position) {
        if (mDataValid && mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getApplicantId();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        long applicantId;
        ImageView avatar;
        TextView applicantFullName;
        Button addToFriends;
        Button addToSubscribers;
        FriendsFragmentCallbacks callbacks;

        public ViewHolder(View itemView, FriendsFragmentCallbacks callbacks) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.user_avatar_imageview);
            applicantFullName = (TextView) itemView.findViewById(R.id.user_name_textview);
            addToFriends = (Button) itemView.findViewById(R.id.add_to_friends_btn);
            addToSubscribers = (Button) itemView.findViewById(R.id.add_to_subscribers_btn);
            addToFriends.setOnClickListener(this);
            addToSubscribers.setOnClickListener(this);
            this.callbacks = callbacks;
        }

        @Override
        public void onClick(View v) {
            long friendId = FriendsRequestsAdapter.this.getApplicantId(getPosition());
            switch (v.getId()) {
                case R.id.add_to_friends_btn:
                    Log.v(TAG,"Add to friends clicked");
                    callbacks.addFriend(friendId);
                    callbacks.loadFriends(currentUserId, FriendsContent.FRIENDS_REQUESTS);
                    break;
                case R.id.add_to_subscribers_btn:
                    Log.v(TAG,"Add to subscribers clicked");
                    break;
            }
        }
    }
}
