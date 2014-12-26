package ua.com.studiovision.euromaidan.audio_player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import com.softevol.activity_service_communication.ActivityServiceCommunicationService;

import org.androidannotations.annotations.EService;

@EService
public class AudioPlayerService extends ActivityServiceCommunicationService
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private static final String TAG = "AudioPlayerService";

    MediaPlayer mMediaPlayer = null;
    private int currentAudioPosition;

    // TODO remove after final implementation
    String audioUrl;

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate(" + ")");
        mMediaPlayer = new MediaPlayer();
        initMediaPlayer();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy(" + ")");
        mMediaPlayer.stop();
        mMediaPlayer.release();
//        mMediaPlayer = null;
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
//        MyAudio audio = playlist.get(currentAudioPosition);
        try {
//            mMediaPlayer.setDataSource(audio.url);
            mMediaPlayer.setDataSource(audioUrl);
        } catch (Exception e) {
            Log.e(TAG, "Error setting data source", e);
        }
        mMediaPlayer.prepareAsync();
    }

    public void setAudio(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void play() {
        mMediaPlayer.start();
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(TAG, "onCompletion(" + "mediaPlayer=" + mediaPlayer + ")");
//        if (++currentAudioPosition <= playlist.size()) {
//            playSong();
//        }
        // TODO notify activity
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MusicProtocol.START_PLAYBACK:

        }
    }
}
