package ua.com.studiovision.euromaidan.audio_player;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.json_protocol.search.MyAudio;

@EActivity(R.layout.activity_audio_player)
public class AudioActivity extends ActivityServiceCommunicationActivity {
    private static final String TAG = "AudioActivity";

    public static final String INITIAL_POSITION = "initial_position";
    public static final String AUDIOS_ARRAY = "audios_array";

    public static final String VOLUME_LEVEL = "volume_level";
    public static final String CURRENT_POSITION = "current_position";
    public static final String SEEK_TO = "seek_to";

    public static final String CURRENT_TRACK_INFO = "current_track_info";

    public static final String IS_OLD_INSTANCE = "is_old_instance";
    public static final String AUDIO_STATE = "audio_state";

    private boolean mIsOldInstance = false;

    private MyAudio audioState;

    @ViewById(R.id.audio_name_textview)
    TextView audioNameTextView;
    @ViewById(R.id.playback_seek_bar)
    SeekBar playbackSeekBar;
    @ViewById(R.id.current_duration_textview)
    TextView currentDuration;
    @ViewById(R.id.total_duration_textview)
    TextView totalDurationTextView;
    @ViewById(R.id.play_pause_togglebutton)
    ToggleButton playPauseToggleButton;

    @Extra
    Parcelable[] audiosTemp = null;

    @Extra
    int initialPosition;
    private boolean playbackFinished;
//    int position;
//    int totalDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = AudioPlayerService_.class;
        if (savedInstanceState != null) {
            mIsOldInstance = savedInstanceState.getBoolean(IS_OLD_INSTANCE, mIsOldInstance);
            audioState = savedInstanceState.getParcelable(AUDIO_STATE);
        }
    }

    @Override
    public void onBackPressed() {
        if (!playPauseToggleButton.isChecked()) {
            Message msg = Message.obtain();
            msg.what = MusicProtocol.STOP_PLAYBACK;
            sendMessage(msg);
        }

        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_OLD_INSTANCE, mIsOldInstance);
        outState.putParcelable(AUDIO_STATE, audioState);
        super.onSaveInstanceState(outState);
    }

    @AfterViews
    void init() {
        playbackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentDuration.setText(i / 60 + ":" + i % 60);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdatingTime();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.v(TAG, "onStopTrackingTouch(" + "seekBar=" + seekBar + ")");

                Message msg = Message.obtain();
                Bundle data = new Bundle();
                data.putInt(SEEK_TO, seekBar.getProgress() * 1000);
                msg.setData(data);
                msg.what = MusicProtocol.SEEK_TO;
                sendMessage(msg);

                startUpdatingTime();
            }
        });
        if (audioState != null) {
            audioNameTextView.setText(audioState.author + " - " + audioState.name);
            playbackSeekBar.setMax(audioState.duration);
            totalDurationTextView.setText(audioState.duration / 60 + ":" + audioState.duration % 60);
        }
    }

    @CheckedChange(R.id.play_pause_togglebutton)
    void playPause(boolean checked) {
        Log.v(TAG, "playPause(" + "checked=" + checked + ")");
        Message msg = Message.obtain();
        if (checked) {
            startUpdatingTime();
            msg.what = MusicProtocol.RESUME_PLAYBACK;
        } else {
            stopUpdatingTime();
            msg.what = MusicProtocol.PAUSE_PLAYBACK;
        }
        sendMessage(msg);
    }

    @SeekBarProgressChange(R.id.volume_seek_bar)
    void onVolumeChanged(SeekBar seekBar, int progress) {
        Message msg = Message.obtain();
        Bundle data = new Bundle();
        data.putFloat(VOLUME_LEVEL, progress / 100.0f);
        msg.setData(data);
        msg.what = MusicProtocol.VOLUME_CHANGED;
        sendMessage(msg);
    }

    @CheckedChange(R.id.repeat_toggle_button)
    void onRepeatChanged(boolean repeat) {
        Log.v(TAG, "onRepeatChanged(" + "repeat=" + repeat + ")");
        Message msg = Message.obtain();
        if (repeat) {
            msg.what = MusicProtocol.ENABLE_REPEAT;
        } else {
            msg.what = MusicProtocol.DISABLE_REPEAT;
        }
        sendMessage(msg);
    }

    @CheckedChange(R.id.shuffle_toggle_button)
    void onShuffleChanged(boolean repeat) {
        Message msg = Message.obtain();
        if (repeat) {
            msg.what = MusicProtocol.ENABLE_SHUFFLE;
        } else {
            msg.what = MusicProtocol.DISABLE_SHUFFLE;
        }
        sendMessage(msg);
    }

    @Click(R.id.next_audio_image_view)
    void nextTrack() {
        Message msg = Message.obtain();
        msg.what = MusicProtocol.NEXT_TRACK;
        sendMessage(msg);
    }

    @Click(R.id.previous_audio_image_view)
    void previoustTrack() {
        Message msg = Message.obtain();
        msg.what = MusicProtocol.PREVIOUS_TRACK;
        sendMessage(msg);
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MusicProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "ON_SERVICE_CONNECTED: mIsOldInstance=" + mIsOldInstance);
                if (mIsOldInstance) break;
                msg = Message.obtain();
                msg.what = MusicProtocol.START_PLAYBACK;
                Bundle bundle = new Bundle();
                bundle.putParcelableArray(AUDIOS_ARRAY, audiosTemp);
                bundle.putInt(INITIAL_POSITION, initialPosition);
                msg.setData(bundle);
                sendMessage(msg);
                mIsOldInstance = true;
                break;
            case MusicProtocol.CURRENT_POSITION:
                Log.v(TAG, "CURRENT_POSITION=" + msg.getData().getInt(CURRENT_POSITION));
                playbackSeekBar.setProgress(msg.getData().getInt(CURRENT_POSITION));
                break;
            case MusicProtocol.CURRENT_TRACK_INFO:
                audioState = msg.getData().getParcelable(CURRENT_TRACK_INFO);
                audioNameTextView.setText(audioState.author + " - " + audioState.name);
                playbackSeekBar.setMax(audioState.duration);
                totalDurationTextView.setText(audioState.duration / 60 + ":" + audioState.duration % 60);
                break;
            case MusicProtocol.ON_PLAYBACK_FINISHED:
                playPauseToggleButton.setChecked(false);
                playbackFinished = true;
                break;
        }
    }

    private void startUpdatingTime() {
        Message msg;
        msg = Message.obtain();
        msg.what = MusicProtocol.START_UPDATING_TIME;
        sendMessage(msg);
    }


    private void stopUpdatingTime() {
        Message msg;
        msg = Message.obtain();
        msg.what = MusicProtocol.STOP_UPDATING_TIME;
        sendMessage(msg);
    }
}
