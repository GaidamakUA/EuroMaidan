package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosCursor;
import ua.com.studiovision.euromaidan.search_fragments.AudioSearchFragment;

public class AudioSearchAdapter extends CursorRecyclerAdapter<AudioSearchAdapter.ViewHolder, AudiosCursor> {

    AudioSearchFragment.OnAudioClickListener callbacks;

    public AudioSearchAdapter(AudiosCursor cursor, Context context, AudioSearchFragment.OnAudioClickListener callbacks) {
        super(cursor, context);
        this.callbacks = callbacks;
    }

//    public AudiosCursor getCursor() {
//        return mCursor;
//    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, AudiosCursor cursor) {
        holder.audioName.setText(cursor.getName());
        holder.audioAuthor.setText(cursor.getAuthor());
        int duration = cursor.getDuration();
        holder.duration.setText(String.valueOf(duration / 60) + ":" + duration % 60);
    }

    @Override
    public AudioSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_search_result_list_row, parent, false);
        return new AudioSearchAdapter.ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView audioName;
        TextView audioAuthor;
        TextView duration;


        public ViewHolder(View itemView) {
            super(itemView);
            audioName = (TextView) itemView.findViewById(R.id.audio_name_textview);
            audioAuthor = (TextView) itemView.findViewById(R.id.audio_author_textview);
            duration = (TextView) itemView.findViewById(R.id.duration_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            callbacks.onAudioClicked(AudioSearchAdapter.this.getItemId(getPosition()));
            callbacks.onAudioClicked(getPosition());
        }
    }

}
