package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments;

import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.ImageProcessor;
import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_friends_requests)
public class FriendsRequestsFragment extends Fragment{
    @ViewById(R.id.friends_requests_recycler_view)
    RecyclerView friendsRequestsRecyclerView;

    @AfterViews
    void init(){
        friendsRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        friendsRequestsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friendsRequestsRecyclerView.setAdapter(new FriendsRequestsAdapter());
    }


    private class FriendsRequestsAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_requests_list_row, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.avatar.setImageBitmap(ImageProcessor.getRoundedCornersImage(BitmapFactory.decodeResource(getResources(), R.drawable.fail_avatar)));
            holder.userName.setText("Darth Vader Second");
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView)itemView.findViewById(R.id.user_avatar_imageview);
            userName = (TextView)itemView.findViewById(R.id.user_name_textview);
        }
    }
}
