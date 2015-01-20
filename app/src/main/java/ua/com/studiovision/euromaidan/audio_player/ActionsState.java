package ua.com.studiovision.euromaidan.audio_player;

public enum ActionsState {
    // PAUSE and PLAY for forground
    // BPAUSE and BPLAY for background
    PLAY_TO_PAUSE,
    PLAY_TO_BPLAY,
    PAUSE_TO_PLAY,
    BPAUSE_TO_PAUSE,
    BPAUSE_TO_BPLAY,
    BPLAY_TO_PLAY,
    BPLAY_TO_BPAUSE;
    private ActionsState nextPlaybackChangeState;
    private ActionsState nextBackgroundChangeState;

    static {
        PLAY_TO_PAUSE.nextPlaybackChangeState = PAUSE_TO_PLAY;
        PLAY_TO_BPLAY.nextPlaybackChangeState = BPLAY_TO_BPAUSE;
        PLAY_TO_BPLAY.nextBackgroundChangeState = BPLAY_TO_PLAY;
        PAUSE_TO_PLAY.nextPlaybackChangeState = PLAY_TO_PAUSE;
        PAUSE_TO_PLAY.nextBackgroundChangeState = PLAY_TO_BPLAY;
        BPAUSE_TO_PAUSE.nextPlaybackChangeState = PAUSE_TO_PLAY;
        BPAUSE_TO_BPLAY.nextPlaybackChangeState = BPLAY_TO_BPAUSE;
        BPAUSE_TO_BPLAY.nextBackgroundChangeState = BPLAY_TO_PLAY;
        BPLAY_TO_PLAY.nextPlaybackChangeState = PLAY_TO_PAUSE;
        BPLAY_TO_PLAY.nextBackgroundChangeState = PLAY_TO_BPLAY;
        BPLAY_TO_BPAUSE.nextPlaybackChangeState = BPAUSE_TO_BPLAY;
        BPLAY_TO_BPAUSE.nextBackgroundChangeState = BPAUSE_TO_PAUSE;
    }

    public ActionsState playbackChanged() {
        return nextPlaybackChangeState;
    }
    public ActionsState backgroundChanged() {
        return nextPlaybackChangeState;
    }
}
