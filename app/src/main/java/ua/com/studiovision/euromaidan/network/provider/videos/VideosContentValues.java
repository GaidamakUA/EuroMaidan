package ua.com.studiovision.euromaidan.network.provider.videos;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code videos} table.
 */
public class VideosContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return VideosColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, VideosSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public VideosContentValues putName(String value) {
        if (value == null) throw new IllegalArgumentException("value for name must not be null");
        mContentValues.put(VideosColumns.NAME, value);
        return this;
    }



    /**
     * Seconds
     */
    public VideosContentValues putDuration(int value) {
        mContentValues.put(VideosColumns.DURATION, value);
        return this;
    }



    public VideosContentValues putVideoUrl(String value) {
        if (value == null) throw new IllegalArgumentException("value for videoUrl must not be null");
        mContentValues.put(VideosColumns.VIDEO_URL, value);
        return this;
    }


}
