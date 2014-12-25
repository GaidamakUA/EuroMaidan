package ua.com.studiovision.euromaidan.network.audio_player;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import org.androidannotations.annotations.EService;

import java.util.ArrayList;

import ua.com.studiovision.euromaidan.network.json_protocol.search.MyAudio;

@EService
public class AudioPlayerService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private static final String TAG = "AudioPlayerService";

    MediaPlayer mMediaPlayer = null;
    private ArrayList<MyAudio> playlist;
    private int currentAudioPosition;

    private final IBinder musicBind = new AudioBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        initMediaPlayer();
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
        MyAudio audio = playlist.get(currentAudioPosition);
        try {
            mMediaPlayer.setDataSource(audio.url);
        } catch (Exception e) {
            Log.e(TAG, "Error setting data source", e);
        }
        mMediaPlayer.prepareAsync();
    }

    public void setList(ArrayList<MyAudio> playlist) {
        this.playlist = playlist;
    }

    public void setAudio(int position) {
        this.currentAudioPosition = position;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (++currentAudioPosition <= playlist.size()) {
            playSong();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    public class AudioBinder extends Binder {
        public AudioPlayerService getService() {
            return AudioPlayerService.this;
        }
    }
}
