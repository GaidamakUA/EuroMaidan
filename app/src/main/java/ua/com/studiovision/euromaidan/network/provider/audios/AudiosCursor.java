package ua.com.studiovision.euromaidan.network.provider.audios;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code audios} table.
 */
public class AudiosCursor extends AbstractCursor {
    public AudiosCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(AudiosColumns.NAME);
        return getString(index);
    }

    /**
     * Get the {@code name_lowercase} value.
     * Cannot be {@code null}.
     */
    public String getNameLowercase() {
        Integer index = getCachedColumnIndexOrThrow(AudiosColumns.NAME_LOWERCASE);
        return getString(index);
    }

    /**
     * Get the {@code author} value.
     * Cannot be {@code null}.
     */
    public String getAuthor() {
        Integer index = getCachedColumnIndexOrThrow(AudiosColumns.AUTHOR);
        return getString(index);
    }

    /**
     * Get the {@code author_lowercase} value.
     * Cannot be {@code null}.
     */
    public String getAuthorLowercase() {
        Integer index = getCachedColumnIndexOrThrow(AudiosColumns.AUTHOR_LOWERCASE);
        return getString(index);
    }

    /**
     * Get the {@code duration} value.
     */
    public int getDuration() {
        return getIntegerOrNull(AudiosColumns.DURATION);
    }

    /**
     * Get the {@code audio_url} value.
     * Cannot be {@code null}.
     */
    public String getAudioUrl() {
        Integer index = getCachedColumnIndexOrThrow(AudiosColumns.AUDIO_URL);
        return getString(index);
    }
}
