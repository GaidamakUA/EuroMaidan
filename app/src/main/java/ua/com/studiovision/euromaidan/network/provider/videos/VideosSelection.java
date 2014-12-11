package ua.com.studiovision.euromaidan.network.provider.videos;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code videos} table.
 */
public class VideosSelection extends AbstractSelection<VideosSelection> {
    @Override
    public Uri uri() {
        return VideosColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code VideosCursor} object, which is positioned before the first entry, or null.
     */
    public VideosCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new VideosCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public VideosCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public VideosCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public VideosSelection id(long... value) {
        addEquals("videos." + VideosColumns._ID, toObjectArray(value));
        return this;
    }


    public VideosSelection name(String... value) {
        addEquals(VideosColumns.NAME, value);
        return this;
    }

    public VideosSelection nameNot(String... value) {
        addNotEquals(VideosColumns.NAME, value);
        return this;
    }

    public VideosSelection nameLike(String... value) {
        addLike(VideosColumns.NAME, value);
        return this;
    }

    public VideosSelection duration(int... value) {
        addEquals(VideosColumns.DURATION, toObjectArray(value));
        return this;
    }

    public VideosSelection durationNot(int... value) {
        addNotEquals(VideosColumns.DURATION, toObjectArray(value));
        return this;
    }

    public VideosSelection durationGt(int value) {
        addGreaterThan(VideosColumns.DURATION, value);
        return this;
    }

    public VideosSelection durationGtEq(int value) {
        addGreaterThanOrEquals(VideosColumns.DURATION, value);
        return this;
    }

    public VideosSelection durationLt(int value) {
        addLessThan(VideosColumns.DURATION, value);
        return this;
    }

    public VideosSelection durationLtEq(int value) {
        addLessThanOrEquals(VideosColumns.DURATION, value);
        return this;
    }

    public VideosSelection videoUrl(String... value) {
        addEquals(VideosColumns.VIDEO_URL, value);
        return this;
    }

    public VideosSelection videoUrlNot(String... value) {
        addNotEquals(VideosColumns.VIDEO_URL, value);
        return this;
    }

    public VideosSelection videoUrlLike(String... value) {
        addLike(VideosColumns.VIDEO_URL, value);
        return this;
    }
}
