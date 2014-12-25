package ua.com.studiovision.euromaidan.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.audio_player.AudioPlayerService;

@EActivity(R.layout.activity_audio_player)
public class AudioActivity extends Activity {
    @ViewById(R.id.playback_seek_bar)
    SeekBar playbackSeekBar;
    @ViewById(R.id.current_duration_textview)
    TextView currentDuration;

    private AudioPlayerService audioPlayerService;
    private Intent playIntent;
    private boolean musicBound = false;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, AudioPlayerService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioPlayerService.AudioBinder binder = (AudioPlayerService.AudioBinder)service;
            //get service
            audioPlayerService = binder.getService();
            //pass list
            audioPlayerService.setList(null);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };
}
