package ua.com.studiovision.euromaidan.audio_player;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
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
import org.androidannotations.annotations.OptionsItem;
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
    public static final String IS_PLAYING = "is_playing";

    boolean mIsOldInstance = false;

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

    @Extra
    boolean mIsStartedFromNotification = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mServiceClass = AudioPlayerService_.class;
        if (savedInstanceState != null) {
            mIsOldInstance = savedInstanceState.getBoolean(IS_OLD_INSTANCE, mIsOldInstance);
            audioState = savedInstanceState.getParcelable(AUDIO_STATE);
        }
    }

    @Override
    public void onBackPressed() {
        if (!playPauseToggleButton.isChecked()) {
            sendMessageWithWhat(MusicProtocol.STOP_PLAYBACK);
        }
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(TAG, "onNewIntent(" + "intent=" + intent + ")");
        super.onNewIntent(intent);
        setIntent(intent);
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

    @OptionsItem(android.R.id.home)
    void upNavigation() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        startActivity(upIntent);
        finish();
    }

    @CheckedChange(R.id.play_pause_togglebutton)
    void playPause(boolean checked) {
        Log.v(TAG, "playPause(" + "checked=" + checked + ")");
        if (checked) {
            sendMessageWithWhat(MusicProtocol.RESUME_PLAYBACK);
        } else {
            sendMessageWithWhat(MusicProtocol.PAUSE_PLAYBACK);
        }
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
        if (repeat) {
            sendMessageWithWhat(MusicProtocol.ENABLE_REPEAT);
        } else {
            sendMessageWithWhat(MusicProtocol.DISABLE_REPEAT);
        }
    }

    @CheckedChange(R.id.shuffle_toggle_button)
    void onShuffleChanged(boolean repeat) {
        if (repeat) {
            sendMessageWithWhat(MusicProtocol.ENABLE_SHUFFLE);
        } else {
            sendMessageWithWhat(MusicProtocol.DISABLE_SHUFFLE);
        }
    }

    @Click(R.id.next_audio_image_view)
    void nextTrack() {
        sendMessageWithWhat(MusicProtocol.NEXT_TRACK);
    }

    @Click(R.id.previous_audio_image_view)
    void previoustTrack() {
        sendMessageWithWhat(MusicProtocol.PREVIOUS_TRACK);
    }

    private void sendMessageWithWhat (int what) {
        Message msg = Message.obtain();
        msg.what = what;
        sendMessage(msg);
    }

    @Override
    protected void handleMessage(Message msg) {
        Log.v(TAG, "handleMessage(" + "msg=" + msg.what + "; data=" + msg.getData() + ")");
        switch (msg.what) {
            case MusicProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "ON_SERVICE_CONNECTED: mIsOldInstance=" + mIsOldInstance
                        + "; mIsStartedFromNotification=" + mIsStartedFromNotification
                        + "; command=");
                msg = Message.obtain();
                if (!mIsStartedFromNotification) {
                    msg.what = MusicProtocol.START_PLAYBACK;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray(AUDIOS_ARRAY, audiosTemp);
                    bundle.putInt(INITIAL_POSITION, initialPosition);
                    msg.setData(bundle);
                } else {
                    msg.what = MusicProtocol.REQUEST_SYNC;
                }
                sendMessage(msg);
                mIsOldInstance = true;
                break;
            case MusicProtocol.CURRENT_POSITION:
                Log.v(TAG, "CURRENT_POSITION=" + msg.getData().getInt(CURRENT_POSITION));
                playbackSeekBar.setProgress(msg.getData().getInt(CURRENT_POSITION));
                break;
            case MusicProtocol.CURRENT_TRACK_INFO:
                Log.v(TAG, "CURRENT_TRACK_INFO=" + msg.getData());
                Bundle data = msg.getData();
                audioState = data.getParcelable(CURRENT_TRACK_INFO);
                audioNameTextView.setText(audioState.author + " - " + audioState.name);
                playbackSeekBar.setMax(audioState.duration);
                totalDurationTextView.setText(audioState.duration / 60 + ":" + audioState.duration % 60);
                boolean isPlaying = data.getBoolean(IS_PLAYING, false);
                playPauseToggleButton.setChecked(isPlaying);
                break;
            case MusicProtocol.ON_PLAYBACK_FINISHED:
                playPauseToggleButton.setChecked(false);
                break;
            case MusicProtocol.DIE:
                finish();
            default:
                Log.v(TAG, "Unhandled Message=" + msg.what);
        }
    }

    private void startUpdatingTime() {
        sendMessageWithWhat(MusicProtocol.START_UPDATING_TIME);
    }

    private void stopUpdatingTime() {
        sendMessageWithWhat(MusicProtocol.STOP_UPDATING_TIME);
    }
}
