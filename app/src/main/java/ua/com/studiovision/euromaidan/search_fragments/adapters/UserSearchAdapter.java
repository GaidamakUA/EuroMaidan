package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;
import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;

public class UserSearchAdapter extends CursorRecyclerAdapter<UserSearchAdapter.ViewHolder, UsersCursor> {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    SearchActivityCallbacks callbacks;

    public UserSearchAdapter(UsersCursor cursor, Context context, SearchActivityCallbacks callbacks) {
        super(cursor, context);
        this.callbacks = callbacks;
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
        return new UserSearchAdapter.ViewHolder(v, new ViewHolder.IViewHolderClicks() {
            @Override
            public void onUserClicked(View caller) {

            }
        });
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, UsersCursor cursor) {
        holder.userId = cursor.getUserId();
        holder.userName.setText(cursor.getUserName());
        imageLoader.displayImage(cursor.getAvatar(), holder.avatar);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        long userId;
        ImageView avatar;
        TextView userName;
        IViewHolderClicks mListener;

        public ViewHolder(View itemView, IViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            userName = (TextView) itemView.findViewById(R.id.user_name_textview);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mListener.onUserClicked(v);
        }

        public static interface IViewHolderClicks {
            public void onUserClicked(View caller);
        }
    }
}
