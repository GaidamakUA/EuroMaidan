package ua.com.studiovision.euromaidan.audio_player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerManager;
import android.util.Log;

import com.softevol.activity_service_communication.ActivityServiceCommunicationService;

import org.androidannotations.annotations.EService;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ua.com.studiovision.euromaidan.network.json_protocol.search.MyAudio;

@EService
public class AudioPlayerService extends ActivityServiceCommunicationService
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private static final String TAG = "AudioPlayerService";

    MediaPlayer mMediaPlayer = null;
    private int currentAudioPosition;
    MyAudio[] playlist;

    ScheduledExecutorService mScheduledExecutorService;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        initMediaPlayer();
        Log.v(TAG, "onCreate(" + ")");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUpdatingTimeInActivity();
        Log.v(TAG, "onDestroy(" + ")");
    }

    public void initMediaPlayer() {
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    public void playSong() {
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(playlist[currentAudioPosition].url);
        } catch (Exception e) {
            Log.e(TAG, "Error setting data source", e);
        }
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(TAG, "onCompletion(" + "mediaPlayer=" + mediaPlayer + ")");
        if (++currentAudioPosition <= playlist.length) {
            sendCurrentTrackInfo();
            playSong();
            startUpdatingTimeInActivity();
        } else {
            stopUpdatingTimeInActivity();
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    @Override
    protected void handleMessage(Message msg) {
        Log.v(TAG, "handleMessage(" + "msg=" + msg.what + ")");
        switch (msg.what) {
            case MusicProtocol.START_PLAYBACK:
                Log.v(TAG, "START_PLAYBACK");
                //audioUrl = msg.getData().getString(AudioActivity.SONG_URL);
                currentAudioPosition = msg.getData().getInt(AudioActivity.INITIAL_POSITION);
                Log.v(TAG, "currentAudioPosition=" + currentAudioPosition);
                Parcelable[] audiosParcelable = msg.getData().getParcelableArray(AudioActivity.AUDIOS_ARRAY);
                playlist = Arrays.copyOf(audiosParcelable,audiosParcelable.length,MyAudio[].class);
                Log.v(TAG, "audioUrl=" + playlist[currentAudioPosition].url);
                sendCurrentTrackInfo();
                playSong();
                startUpdatingTimeInActivity();
                break;
            case MusicProtocol.STOP_PLAYBACK:
                Log.v(TAG, "STOP_PLAYBACK");
                mMediaPlayer.stop();
                mMediaPlayer.release();
                stopSelf();
                break;
            case MusicProtocol.PAUSE_PLAYBACK:
                mMediaPlayer.pause();
                break;
            case MusicProtocol.RESUME_PLAYBACK:
                mMediaPlayer.start();
                break;
            case MusicProtocol.VOLUME_CHANGED:
                float volumeLevel = msg.getData().getFloat(AudioActivity.VOLUME_LEVEL);
                mMediaPlayer.setVolume(volumeLevel, volumeLevel);
                break;
            case MusicProtocol.SEEK_TO:
                mMediaPlayer.seekTo(msg.getData().getInt(AudioActivity.SEEK_TO));
                break;
            case MusicProtocol.ENABLE_REPEAT:
                mMediaPlayer.setLooping(true);
                break;
            case MusicProtocol.DISABLE_REPEAT:
                mMediaPlayer.setLooping(false);
                break;
            case MusicProtocol.START_UPDATING_TIME:
                startUpdatingTimeInActivity();
                break;
            case MusicProtocol.STOP_UPDATING_TIME:
                stopUpdatingTimeInActivity();
                break;
        }
    }

    private void startUpdatingTimeInActivity() {
        Log.v(TAG, "startUpdatingTimeInActivity(" + ")");
        if (mScheduledExecutorService == null) {
            mScheduledExecutorService = Executors.newScheduledThreadPool(1);
        }
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = MusicProtocol.CURRENT_POSITION;
                Bundle bundle = new Bundle();
                bundle.putInt(AudioActivity.CURRENT_POSITION, mMediaPlayer.getCurrentPosition()/1000);
                msg.setData(bundle);
                sendMessage(msg);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
    private void stopUpdatingTimeInActivity() {
        mScheduledExecutorService.shutdown();
        mScheduledExecutorService = null;
    }

    private void sendCurrentTrackInfo(){
        Message msg = Message.obtain();
        msg.what = MusicProtocol.CURRENT_TRACK_INFO;
        Bundle bundle = new Bundle();
        bundle.putParcelable(AudioActivity.CURRENT_TRACK_INFO,playlist[currentAudioPosition]);
        msg.setData(bundle);
        sendMessage(msg);
    }
}
