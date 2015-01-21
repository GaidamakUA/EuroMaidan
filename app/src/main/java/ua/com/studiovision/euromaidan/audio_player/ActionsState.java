package ua.com.studiovision.euromaidan.audio_player;

public enum ActionsState {
    PAUSE(false),
    PLAY(true);
    private ActionsState nextPlaybackChangeState;
    private boolean isPlaying;
    static {
        PAUSE.nextPlaybackChangeState = PLAY;
        PLAY.nextPlaybackChangeState = PAUSE;
    }

    ActionsState(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public ActionsState playbackChanged() {
        return nextPlaybackChangeState;
    }
    public boolean isPlaying() {
        return isPlaying;
    }
}
