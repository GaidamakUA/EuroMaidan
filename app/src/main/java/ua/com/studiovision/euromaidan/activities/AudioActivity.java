package ua.com.studiovision.euromaidan.activities;

import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EActivity(R.layout.fragment_audio_player)
public class AudioActivity extends Activity {
    @ViewById(R.id.playback_seek_bar)
    SeekBar playbackSeekBar;
    @ViewById(R.id.current_duration_textview)
    TextView currentDuration;

    @AfterViews
    void init(){
        playbackSeekBar.setProgress(0);
        playbackSeekBar.setMax(209);
        playbackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentDuration.setText(i/60+":"+i%60);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
