package ua.com.studiovision.euromaidan.audio_player;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.softevol.activity_service_communication.ActivityServiceCommunicationFragmentActivity;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosCursor;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosSelection;

@EActivity(R.layout.activity_audio_player)
public class AudioActivity extends ActivityServiceCommunicationFragmentActivity {
    private static final String TAG = "AudioActivity";

    public static final String SONG_URL = "song_url";
    public static final String VOLUME_LEVEL = "volume_level";
    public static final String CURRENT_POSITION = "current_position";
    public static final String SEEK_TO = "seek_to";

    @ViewById(R.id.audio_name_textview)
    TextView audioNameTextView;
    @ViewById(R.id.playback_seek_bar)
    SeekBar playbackSeekBar;
    @ViewById(R.id.current_duration_textview)
    TextView currentDuration;
    @ViewById(R.id.total_duration_textview)
    TextView totalDurationTextView;

    @Extra
    long[] audioIds = null;
    @Extra
    int initialPosition;
    int position;
    int totalDuration;

    private String mSongName;
    private String mSongUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = AudioPlayerService_.class;
    }

    @Override
    public void onBackPressed() {
        // Because activity is disconnecting from service onPause
        Message msg = Message.obtain();
        msg.what = MusicProtocol.STOP_PLAYBACK;
        sendMessage(msg);
        super.onBackPressed();
    }

    @AfterViews
    void init() {
        totalDurationTextView.setText(totalDuration / 60 + ":" + totalDuration % 60);
        playbackSeekBar.setMax(totalDuration);
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
                Message msg = Message.obtain();
                Bundle data = new Bundle();
                data.putInt(SEEK_TO, seekBar.getProgress() * 1000);
                msg.setData(data);
                msg.what = MusicProtocol.SEEK_TO;
                sendMessage(msg);
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

        totalDuration = cursor.getDuration();
        cursor.close();
    }

    @CheckedChange(R.id.play_pause_togglebutton)
    void playPause(boolean checked) {
        Message msg = Message.obtain();
        if (checked) {
            msg.what = MusicProtocol.RESUME_PLAYBACK;
        } else {
            msg.what = MusicProtocol.PAUSE_PLAYBACK;
        }
        sendMessage(msg);
    }

    @SeekBarProgressChange(R.id.volume_seek_bar)
    void onVolumeChanged(SeekBar seekBar, int progress) {
//        Log.v(TAG, "onVolumeChanged(" + "seekBar=" + seekBar + ", progress=" + progress + ")");
        Message msg = Message.obtain();
        Bundle data = new Bundle();
        data.putFloat(VOLUME_LEVEL, progress / 100.0f);
        msg.setData(data);
        msg.what = MusicProtocol.VOLUME_CHANGED;
        sendMessage(msg);
    }

//    @CheckedChange(R.id.repeat_toggle_button)
//    void onSeekFinished(SeekBar seekBar) {
//
//    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MusicProtocol.ON_SERVICE_CONNECTED:
                // TODO Prevent playback from restarting
                msg = Message.obtain();
                msg.what = MusicProtocol.START_PLAYBACK;
                Bundle bundle = new Bundle();
                bundle.putString(SONG_URL, mSongUrl);
                msg.setData(bundle);
                sendMessage(msg);
                break;
            case MusicProtocol.CURRENT_POSITION:
//                Log.v(TAG, "CURRENT_POSITION=" + msg.getData().getInt(CURRENT_POSITION));
                playbackSeekBar.setProgress(msg.getData().getInt(CURRENT_POSITION));
                break;
        }
    }
}
