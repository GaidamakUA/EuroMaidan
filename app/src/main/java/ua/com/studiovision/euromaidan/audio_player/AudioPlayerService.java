package ua.com.studiovision.euromaidan.audio_player;

import android.content.Intent;
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
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ua.com.studiovision.euromaidan.network.json_protocol.search.MyAudio;

@EService
public class AudioPlayerService extends ActivityServiceCommunicationService
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private static final String TAG = "AudioPlayerService";

    public static final String ACTION_PREVIOUS = "ua.com.studiovision.euromaidan.audio_player.action_previous";
    public static final String ACTION_PLAY = "ua.com.studiovision.euromaidan.audio_player.action_play";
    public static final String ACTION_PAUSE = "ua.com.studiovision.euromaidan.audio_player.action_pause";
    public static final String ACTION_NEXT = "ua.com.studiovision.euromaidan.audio_player.action_next";
    public static final String ACTION_DIE = "ua.com.studiovision.euromaidan.audio_player.action_die";

    MediaPlayer mMediaPlayer = null;
    private int currentAudioPosition;
    MyAudio[] playlist;

    ScheduledExecutorService mScheduledExecutorService;
    private boolean shouldShuffle;

    private Random mRandom = new Random();

    private ActionsState state = ActionsState.PLAY;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null)
            switch (action) {
                case ACTION_PLAY:
                    if (!state.isPlaying())
                        onPlaybackStateChanged(state.playbackChanged());
                    return START_NOT_STICKY;
                case ACTION_PAUSE:
                    if (state.isPlaying())
                        onPlaybackStateChanged(state.playbackChanged());
                    return START_NOT_STICKY;
                case ACTION_NEXT:
                    if (++currentAudioPosition < playlist.length) {
                        stopUpdatingTimeInActivity();
                        playSong();
                        startUpdatingTimeInActivity();
                        sendCurrentTrackInfo();
                    } else {
                        --currentAudioPosition;
                    }
                    return START_NOT_STICKY;
                case ACTION_PREVIOUS:
                    if (--currentAudioPosition >= 0) {
                        stopUpdatingTimeInActivity();
                        playSong();
                        startUpdatingTimeInActivity();
                        sendCurrentTrackInfo();
                    } else {
                        ++currentAudioPosition;
                    }
                    return START_NOT_STICKY;
                case ACTION_DIE:
                    sendMessageWithWhat(MusicProtocol.DIE);
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                    stopSelf();
            }

        return super.onStartCommand(intent, flags, startId);
    }

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
        PlayerNotification.cancel(this);
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

        PlayerNotification.notify(this, playlist[currentAudioPosition].author,
                playlist[currentAudioPosition].name, PlayerNotification.PlayerActionState.PAUSE);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(TAG, "onCompletion(" + "mediaPlayer=" + mediaPlayer + ") currentAudioPosition="
                + currentAudioPosition + "; playlist.length=" + playlist.length);
        if (shouldShuffle) {
            currentAudioPosition = mRandom.nextInt(playlist.length) - 1;
        }
        if (++currentAudioPosition < playlist.length) {
            playSong();
            startUpdatingTimeInActivity();
            resetPlaybackState();
            sendCurrentTrackInfo();
        } else {
            resetPlaybackState();
            currentAudioPosition--;
            stopUpdatingTimeInActivity();

            Message msg = Message.obtain();
            msg.what = MusicProtocol.ON_PLAYBACK_FINISHED;
            sendMessage(msg);

            PlayerNotification.notify(this, playlist[currentAudioPosition].author,
                    playlist[currentAudioPosition].name, PlayerNotification.PlayerActionState.PLAY);
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
                Parcelable[] audiosParcelable = msg.getData().getParcelableArray(AudioActivity.AUDIOS_ARRAY);
                MyAudio[] tempPlaylist = Arrays.copyOf(audiosParcelable, audiosParcelable.length, MyAudio[].class);

                boolean isPlaylistChanged = false;
                if (playlist == null) {
                    isPlaylistChanged = true;
                } else {
                    for (int i = 0; i < tempPlaylist.length; i++) {
                        if (!playlist[i].url.equals(tempPlaylist[i].url)) {
                            isPlaylistChanged = true;
                            break;
                        }
                    }
                }
                if (isPlaylistChanged) {
                    playlist = tempPlaylist;
                    currentAudioPosition = msg.getData().getInt(AudioActivity.INITIAL_POSITION);
                    resetPlaybackState();
                    playSong();
                } else {
                    if (currentAudioPosition != msg.getData().getInt(AudioActivity.INITIAL_POSITION)) {
                        currentAudioPosition = msg.getData().getInt(AudioActivity.INITIAL_POSITION);
                        resetPlaybackState();
                        playSong();
                    }
                }

                startUpdatingTimeInActivity();
                sendCurrentTrackInfo();
                break;
            case MusicProtocol.STOP_PLAYBACK:
                Log.v(TAG, "STOP_PLAYBACK");
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                stopSelf();
                break;
            case MusicProtocol.PAUSE_PLAYBACK:
                if (state.isPlaying())
                    onPlaybackStateChanged(state.playbackChanged());
                break;
            case MusicProtocol.RESUME_PLAYBACK:
                if (!state.isPlaying())
                    onPlaybackStateChanged(state.playbackChanged());
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
            case MusicProtocol.ENABLE_SHUFFLE:
                shouldShuffle = true;
                break;
            case MusicProtocol.DISABLE_SHUFFLE:
                shouldShuffle = false;
                break;
            case MusicProtocol.NEXT_TRACK:
                if (++currentAudioPosition < playlist.length) {
                    stopUpdatingTimeInActivity();
                    playSong();
                    startUpdatingTimeInActivity();
                    sendCurrentTrackInfo();
                } else {
                    --currentAudioPosition;
                }
                break;
            case MusicProtocol.PREVIOUS_TRACK:
                if (--currentAudioPosition >= 0) {
                    stopUpdatingTimeInActivity();
                    playSong();
                    startUpdatingTimeInActivity();
                    sendCurrentTrackInfo();
                } else {
                    ++currentAudioPosition;
                }
                break;
            case MusicProtocol.ON_PAUSE:
                stopUpdatingTimeInActivity();
                break;
            case MusicProtocol.REQUEST_SYNC:
                if (state.isPlaying()) {
                    startUpdatingTimeInActivity();
                }
                sendCurrentTrackInfo();
                break;
            default:
                Log.v(TAG, "Unhandled Message=" + msg.what);
        }
    }

    private void onPlaybackStateChanged(ActionsState newState) {
        Log.v(TAG, "onPlaybackStateChanged(oldState=" + state + "; newState=" + newState + ")");
        state = newState;
        switch (newState) {
            case PAUSE:
                mMediaPlayer.pause();
                PlayerNotification.notify(this, playlist[currentAudioPosition].author,
                        playlist[currentAudioPosition].name, PlayerNotification.PlayerActionState.PLAY);
                stopUpdatingTimeInActivity();
                sendCurrentTrackInfo();
                break;
            case PLAY:
                mMediaPlayer.start();
                PlayerNotification.notify(this, playlist[currentAudioPosition].author,
                        playlist[currentAudioPosition].name, PlayerNotification.PlayerActionState.PAUSE);
                startUpdatingTimeInActivity();
                sendCurrentTrackInfo();
                break;
            default:
                throw new IllegalStateException("State=" + newState);
        }
    }

    void resetPlaybackState() {
        state = ActionsState.PLAY;
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
                bundle.putInt(AudioActivity.CURRENT_POSITION, mMediaPlayer.getCurrentPosition() / 1000);
                msg.setData(bundle);
                sendMessage(msg);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void stopUpdatingTimeInActivity() {
        if (mScheduledExecutorService == null) return;
        mScheduledExecutorService.shutdown();
        mScheduledExecutorService = null;
    }

    private void sendCurrentTrackInfo() {
        Log.v(TAG, "sendCurrentTrackInfo(Playing=" + state.isPlaying() + ")");
        Message msg = Message.obtain();
        msg.what = MusicProtocol.CURRENT_TRACK_INFO;
        Bundle bundle = new Bundle();
        bundle.putParcelable(AudioActivity.CURRENT_TRACK_INFO, playlist[currentAudioPosition]);
        // For convenience.
        if (mMediaPlayer != null) {
            bundle.putBoolean(AudioActivity.IS_PLAYING, state.isPlaying());
        }
        msg.setData(bundle);
        sendMessage(msg);
    }

    private void sendMessageWithWhat(int what) {
        Message msg = Message.obtain();
        msg.what = what;
        sendMessage(msg);
    }
}
