package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;
import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;

public class UserSearchAdapter extends CursorRecyclerAdapter<UserSearchAdapter.ViewHolder, UsersCursor> {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    SearchActivityCallbacks callbacks;
    Context context;

    public UserSearchAdapter(UsersCursor cursor, Context context, SearchActivityCallbacks callbacks) {
        super(cursor, context);
        this.callbacks = callbacks;
        this.context = context;
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
        return new UserSearchAdapter.ViewHolder(v, callbacks);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, UsersCursor cursor) {
        holder.userId = cursor.getUserId();
        holder.userFullName.setText(cursor.getUserName() + " " + cursor.getUserSurname());
        imageLoader.displayImage(cursor.getAvatar(), holder.avatar);
    }

    public long getUserId(int position) {
        if (mDataValid && mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getUserId();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ViewHolder";
        long userId;
        ImageView avatar;
        TextView userFullName;
        SearchActivityCallbacks  callbacks;

        public ViewHolder(View itemView, SearchActivityCallbacks callbacks) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            userFullName = (TextView) itemView.findViewById(R.id.user_name_textview);
            itemView.setOnClickListener(this);
            this.callbacks = callbacks;
        }


        @Override
        public void onClick(View v) {
            long userId = UserSearchAdapter.this.getUserId(getPosition());
            callbacks.addFriend(userId);
        }

//        @Override
//        public boolean onLongClick(View view) {
//            callbacks.deleteFriend(UserSearchAdapter.this.getUserId(getPosition()));
//            return true;
//        }
    }
}
