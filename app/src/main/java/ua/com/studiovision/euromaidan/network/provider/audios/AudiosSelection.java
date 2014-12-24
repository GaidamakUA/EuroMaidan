package ua.com.studiovision.euromaidan.network.provider.audios;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code audios} table.
 */
public class AudiosSelection extends AbstractSelection<AudiosSelection> {
    @Override
    public Uri uri() {
        return AudiosColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code AudiosCursor} object, which is positioned before the first entry, or null.
     */
    public AudiosCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new AudiosCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public AudiosCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public AudiosCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public AudiosSelection id(long... value) {
        addEquals("audios." + AudiosColumns._ID, toObjectArray(value));
        return this;
    }


    public AudiosSelection name(String... value) {
        addEquals(AudiosColumns.NAME, value);
        return this;
    }

    public AudiosSelection nameNot(String... value) {
        addNotEquals(AudiosColumns.NAME, value);
        return this;
    }

    public AudiosSelection nameLike(String... value) {
        addLike(AudiosColumns.NAME, value);
        return this;
    }

    public AudiosSelection nameLowercase(String... value) {
        addEquals(AudiosColumns.NAME_LOWERCASE, value);
        return this;
    }

    public AudiosSelection nameLowercaseNot(String... value) {
        addNotEquals(AudiosColumns.NAME_LOWERCASE, value);
        return this;
    }

    public AudiosSelection nameLowercaseLike(String... value) {
        addLike(AudiosColumns.NAME_LOWERCASE, value);
        return this;
    }

    public AudiosSelection author(String... value) {
        addEquals(AudiosColumns.AUTHOR, value);
        return this;
    }

    public AudiosSelection authorNot(String... value) {
        addNotEquals(AudiosColumns.AUTHOR, value);
        return this;
    }

    public AudiosSelection authorLike(String... value) {
        addLike(AudiosColumns.AUTHOR, value);
        return this;
    }

    public AudiosSelection authorLowercase(String... value) {
        addEquals(AudiosColumns.AUTHOR_LOWERCASE, value);
        return this;
    }

    public AudiosSelection authorLowercaseNot(String... value) {
        addNotEquals(AudiosColumns.AUTHOR_LOWERCASE, value);
        return this;
    }

    public AudiosSelection authorLowercaseLike(String... value) {
        addLike(AudiosColumns.AUTHOR_LOWERCASE, value);
        return this;
    }

    public AudiosSelection duration(int... value) {
        addEquals(AudiosColumns.DURATION, toObjectArray(value));
        return this;
    }

    public AudiosSelection durationNot(int... value) {
        addNotEquals(AudiosColumns.DURATION, toObjectArray(value));
        return this;
    }

    public AudiosSelection durationGt(int value) {
        addGreaterThan(AudiosColumns.DURATION, value);
        return this;
    }

    public AudiosSelection durationGtEq(int value) {
        addGreaterThanOrEquals(AudiosColumns.DURATION, value);
        return this;
    }

    public AudiosSelection durationLt(int value) {
        addLessThan(AudiosColumns.DURATION, value);
        return this;
    }

    public AudiosSelection durationLtEq(int value) {
        addLessThanOrEquals(AudiosColumns.DURATION, value);
        return this;
    }

    public AudiosSelection audioUrl(String... value) {
        addEquals(AudiosColumns.AUDIO_URL, value);
        return this;
    }

    public AudiosSelection audioUrlNot(String... value) {
        addNotEquals(AudiosColumns.AUDIO_URL, value);
        return this;
    }

    public AudiosSelection audioUrlLike(String... value) {
        addLike(AudiosColumns.AUDIO_URL, value);
        return this;
    }
}
