package ua.com.studiovision.euromaidan.network.provider.audios;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code audios} table.
 */
public class AudiosContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return AudiosColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, AudiosSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public AudiosContentValues putName(String value) {
        if (value == null) throw new IllegalArgumentException("value for name must not be null");
        mContentValues.put(AudiosColumns.NAME, value);
        return this;
    }



    public AudiosContentValues putAuthor(String value) {
        if (value == null) throw new IllegalArgumentException("value for author must not be null");
        mContentValues.put(AudiosColumns.AUTHOR, value);
        return this;
    }



    public AudiosContentValues putDuration(int value) {
        mContentValues.put(AudiosColumns.DURATION, value);
        return this;
    }



    public AudiosContentValues putAudioUrl(String value) {
        if (value == null) throw new IllegalArgumentException("value for audioUrl must not be null");
        mContentValues.put(AudiosColumns.AUDIO_URL, value);
        return this;
    }


}
