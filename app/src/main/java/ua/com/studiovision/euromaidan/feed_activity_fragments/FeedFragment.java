package ua.com.studiovision.euromaidan.feed_activity_fragments;

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

@EFragment(R.layout.fragment_feed)
public class FeedFragment extends Fragment {
    @ViewById(R.id.feedRecyclerView)
    RecyclerView feedRecyclerView;

    @AfterViews
    void init(){
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        feedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        feedRecyclerView.setAdapter(new FeedAdapter());
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getActionBar().hide();
    }

    private class FeedAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_list_row, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.avatar.setImageBitmap(ImageProcessor.getRoundedCornersImage(BitmapFactory.decodeResource(getResources(), R.drawable.fail_avatar)));
            viewHolder.postTime.setText("11:54");
            viewHolder.postLabel.setText("Deathstar. My pride!");
            viewHolder.content.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
            viewHolder.comment.setImageDrawable(getResources().getDrawable(R.drawable.comment_placeholder));
            viewHolder.share.setImageDrawable(getResources().getDrawable(R.drawable.share_placeholder));
            viewHolder.like.setImageDrawable(getResources().getDrawable(R.drawable.like_placeholder));
        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView postTime;
        TextView postLabel;
        ImageView content;
        ImageView comment;
        ImageView share;
        ImageView like;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            postTime = (TextView) itemView.findViewById(R.id.postTime);
            postLabel = (TextView) itemView.findViewById(R.id.postLabel);
            content = (ImageView) itemView.findViewById(R.id.content);
            comment = (ImageView) itemView.findViewById(R.id.comment);
            share = (ImageView) itemView.findViewById(R.id.share);
            like = (ImageView) itemView.findViewById(R.id.like);
        }
    }
}
