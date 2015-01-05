package ua.com.studiovision.euromaidan.audio_player;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;
import com.softevol.activity_service_communication.ActivityServiceCommunicationFragmentActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
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

    @ViewById(R.id.audio_name_textview)
    TextView audioNameTextView;
    @ViewById(R.id.playback_seek_bar)
    SeekBar playbackSeekBar;
    @ViewById(R.id.current_duration_textview)
    TextView currentDuration;
    @ViewById(R.id.total_duration_textview)
    TextView totalDurationTextView;

    @Extra
    Parcelable[] audiosTemp = null;
//    MyAudio[] audios = null;
    @Extra
    int initialPosition;
//    int position;
//    int totalDuration;

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

        stopUpdatingTime();

        super.onBackPressed();
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
//        Log.v(TAG, "onVolumeChanged(" + "seekBar=" + seekBar + ", progress=" + progress + ")");
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

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MusicProtocol.ON_SERVICE_CONNECTED:
                // TODO Prevent playback from restarting
                msg = Message.obtain();
                msg.what = MusicProtocol.START_PLAYBACK;
                Bundle bundle = new Bundle();
                bundle.putParcelableArray(AUDIOS_ARRAY, audiosTemp);
                bundle.putInt(INITIAL_POSITION, initialPosition);
                msg.setData(bundle);
                sendMessage(msg);
                break;
            case MusicProtocol.CURRENT_POSITION:
                Log.v(TAG, "CURRENT_POSITION=" + msg.getData().getInt(CURRENT_POSITION));
                playbackSeekBar.setProgress(msg.getData().getInt(CURRENT_POSITION));
                break;
            case MusicProtocol.CURRENT_TRACK_INFO:
                MyAudio audio = msg.getData().getParcelable(CURRENT_TRACK_INFO);
                audioNameTextView.setText(audio.author + " - " + audio.name);
                playbackSeekBar.setMax(audio.duration);
                totalDurationTextView.setText(audio.duration / 60 + ":" + audio.duration % 60);
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
