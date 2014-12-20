package ua.com.studiovision.euromaidan.feed_activity_fragments.inbox_fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_dialogs)
public class DialogsFragment extends Fragment{
    @ViewById(R.id.dialogs_recycler_view)
    RecyclerView dialogsRecyclerView;

    @AfterViews
    void init(){
        dialogsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        dialogsRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    class DialogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friends_and_followers_list_row, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_holder,new MessagesFragment_());
        }
    }

}
