package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;

public class UserSearchAdapter extends CursorRecyclerAdapter<UserSearchAdapter.ViewHolder, UsersCursor> {

    private ImageLoader imageLoader = ImageLoader.getInstance();

    public UserSearchAdapter(UsersCursor cursor, Context context) {
        super(cursor, context);
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
        return new UserSearchAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, UsersCursor cursor) {
        holder.userName.setText(cursor.getUserName());
        imageLoader.displayImage(cursor.getAvatar(),holder.avatar);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            userName = (TextView) itemView.findViewById(R.id.user_name_textview);
        }
    }
}
