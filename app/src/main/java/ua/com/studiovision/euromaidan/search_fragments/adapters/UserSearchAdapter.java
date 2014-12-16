package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;
import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;

public class UserSearchAdapter extends CursorRecyclerAdapter<UserSearchAdapter.ViewHolder, UsersCursor> {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    SearchActivityCallbacks callbacks;
    Context context;
    private ProgressBar spinner;

    public UserSearchAdapter(UsersCursor cursor, Context context, SearchActivityCallbacks callbacks) {
        super(cursor, context);
        this.callbacks = callbacks;
        this.context = context;
        imageLoader.init(imageLoaderConfiguration);
        spinner = new ProgressBar(context);
        spinner.setIndeterminate(true);
    }

    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
        return new UserSearchAdapter.ViewHolder(v, callbacks);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, UsersCursor cursor) {
        holder.userId = cursor.getUserId();
        holder.userName.setText(cursor.getUserName());
        imageLoader.displayImage(cursor.getAvatar(), holder.avatar, new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private static final String TAG = "ViewHolder";
        long userId;
        ImageView avatar;
        TextView userName;
        SearchActivityCallbacks  callbacks;

        public ViewHolder(View itemView, SearchActivityCallbacks callbacks) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            userName = (TextView) itemView.findViewById(R.id.user_name_textview);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.callbacks = callbacks;
        }


        @Override
        public void onClick(View v) {
            long userId = UserSearchAdapter.this.getUserId(getPosition());
            callbacks.addFriend(userId);
        }

        @Override
        public boolean onLongClick(View view) {
            callbacks.deleteFriend(UserSearchAdapter.this.getUserId(getPosition()));
            return true;
        }
    }
}
