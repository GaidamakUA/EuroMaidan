package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.User;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;

public class UserSearchAdapter extends CursorRecyclerAdapter<UserSearchAdapter.ViewHolder,UsersCursor>{

    public UserSearchAdapter(UsersCursor cursor) {
        super(cursor);
    }

    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
        return new UserSearchAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, UsersCursor cursor) {
        holder.userName.setText(cursor.getUserName());
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
