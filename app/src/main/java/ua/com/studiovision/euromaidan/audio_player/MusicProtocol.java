package ua.com.studiovision.euromaidan.audio_player;

import com.softevol.activity_service_communication.Protocol;

/**
 * Created by gaidamak on 26.12.14.
 */
public class MusicProtocol extends Protocol {
    public static final int START_PLAYBACK = 0;
    public static final int PAUSE_PLAYBACK = 1;
    public static final int RESUME_PLAYBACK = 2;
    public static final int STOP_PLAYBACK = 4;
    public static final int VOLUME_CHANGED = 5;
    public static final int CURRENT_POSITION = 6;
    public static final int SEEK_TO = 7;
    public static final int ENABLE_REPEAT = 8;
    public static final int DISABLE_REPEAT = 9;
    public static final int START_UPDATING_TIME = 10;
    public static final int STOP_UPDATING_TIME = 11;
    public static final int CURRENT_TRACK_INFO = 12;
    public static final int ENABLE_SHUFFLE = 13;
    public static final int DISABLE_SHUFFLE = 14;
    public static final int ON_PLAYBACK_FINISHED = 15;
    public static final int NEXT_TRACK = 16;
    public static final int PREVIOUS_TRACK = 17;
    public static final int REQUEST_SYNC = 18;
}
