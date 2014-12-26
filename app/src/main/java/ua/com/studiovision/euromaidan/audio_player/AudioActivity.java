package ua.com.studiovision.euromaidan.audio_player;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.softevol.activity_service_communication.ActivityServiceCommunicationFragmentActivity;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosCursor;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosSelection;

@EActivity(R.layout.activity_audio_player)
public class AudioActivity extends ActivityServiceCommunicationFragmentActivity {
    private static final String TAG = "AudioActivity";

    public static final String SONG_NAME = "song_name";

    @ViewById(R.id.audio_name_textview)
    TextView audioNameTextView;
    @ViewById(R.id.playback_seek_bar)
    SeekBar playbackSeekBar;
    @ViewById(R.id.current_duration_textview)
    TextView currentDuration;

    @Extra
    long[] audioIds = null;
    @Extra
    int initialPosition;
    int position;

    private String mSongName;
    private String mSongUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = AudioPlayerService_.class;
    }

    @AfterViews
    void init() {
        playbackSeekBar.setProgress(0);
        playbackSeekBar.setMax(209);
        playbackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentDuration.setText(i / 60 + ":" + i % 60);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        audioNameTextView.setText(mSongName);
    }

    @AfterExtras
    void startPlaying() {
        position = initialPosition;
        AudiosSelection selection = new AudiosSelection();
        AudiosCursor cursor = selection.id(audioIds[position]).query(getContentResolver());
        cursor.moveToFirst();
        Log.v(TAG, "audio name=" + cursor.getName());

        mSongName = cursor.getAuthor() + " - " + cursor.getName();

        // XXX potentially concurrently unsafe
        mSongUrl = cursor.getAudioUrl();
        cursor.close();
    }

//    @CheckedChange(R.id.play_pause_togglebutton)
//    void playPause(boolean checked) {
//        if (checked) {
//            audioPlayerService.pause();
//        } else {
//            audioPlayerService.play();
//        }
//    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MusicProtocol.ON_SERVICE_CONNECTED:
                msg = Message.obtain();
                msg.what = MusicProtocol.START_PLAYBACK;
                Bundle bundle = new Bundle();
                bundle.putString(SONG_NAME, mSongUrl);
                msg.setData(bundle);
                sendMessage(msg);
        }
    }
}
