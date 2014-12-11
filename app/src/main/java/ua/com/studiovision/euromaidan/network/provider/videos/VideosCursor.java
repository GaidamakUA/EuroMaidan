package ua.com.studiovision.euromaidan.network.provider.videos;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code videos} table.
 */
public class VideosCursor extends AbstractCursor {
    public VideosCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(VideosColumns.NAME);
        return getString(index);
    }

    /**
     * Seconds
     */
    public int getDuration() {
        return getIntegerOrNull(VideosColumns.DURATION);
    }

    /**
     * Get the {@code video_url} value.
     * Cannot be {@code null}.
     */
    public String getVideoUrl() {
        Integer index = getCachedColumnIndexOrThrow(VideosColumns.VIDEO_URL);
        return getString(index);
    }
}
