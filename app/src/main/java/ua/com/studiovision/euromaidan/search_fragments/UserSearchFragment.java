package ua.com.studiovision.euromaidan.search_fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
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

@EFragment(R.layout.fragment_user_search)
public class UserSearchFragment extends Fragment {
    @ViewById(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;

    private final String TAG = "User search fragment";

    @AfterViews
    void init(){
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.setAdapter(new UserSearchAdapter());
    }

    private class UserSearchAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_search_result_list_row, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.avatar.setImageBitmap(ImageProcessor.getRoundedCornersImage(BitmapFactory.decodeResource(getResources(), R.drawable.fail_avatar)));
            viewHolder.userName.setText("Clone User");
        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            userName = (TextView) itemView.findViewById(R.id.user_name_textview);
        }
    }
}
