package ua.com.studiovision.euromaidan.network.provider.messages;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code messages} table.
 */
public class MessagesSelection extends AbstractSelection<MessagesSelection> {
    @Override
    public Uri uri() {
        return MessagesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code MessagesCursor} object, which is positioned before the first entry, or null.
     */
    public MessagesCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new MessagesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public MessagesCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public MessagesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public MessagesSelection id(long... value) {
        addEquals("messages." + MessagesColumns._ID, toObjectArray(value));
        return this;
    }


    public MessagesSelection userId(int... value) {
        addEquals(MessagesColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public MessagesSelection userIdNot(int... value) {
        addNotEquals(MessagesColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public MessagesSelection userIdGt(int value) {
        addGreaterThan(MessagesColumns.USER_ID, value);
        return this;
    }

    public MessagesSelection userIdGtEq(int value) {
        addGreaterThanOrEquals(MessagesColumns.USER_ID, value);
        return this;
    }

    public MessagesSelection userIdLt(int value) {
        addLessThan(MessagesColumns.USER_ID, value);
        return this;
    }

    public MessagesSelection userIdLtEq(int value) {
        addLessThanOrEquals(MessagesColumns.USER_ID, value);
        return this;
    }

    public MessagesSelection timestamp(Date... value) {
        addEquals(MessagesColumns.TIMESTAMP, value);
        return this;
    }

    public MessagesSelection timestampNot(Date... value) {
        addNotEquals(MessagesColumns.TIMESTAMP, value);
        return this;
    }

    public MessagesSelection timestamp(long... value) {
        addEquals(MessagesColumns.TIMESTAMP, toObjectArray(value));
        return this;
    }

    public MessagesSelection timestampAfter(Date value) {
        addGreaterThan(MessagesColumns.TIMESTAMP, value);
        return this;
    }

    public MessagesSelection timestampAfterEq(Date value) {
        addGreaterThanOrEquals(MessagesColumns.TIMESTAMP, value);
        return this;
    }

    public MessagesSelection timestampBefore(Date value) {
        addLessThan(MessagesColumns.TIMESTAMP, value);
        return this;
    }

    public MessagesSelection timestampBeforeEq(Date value) {
        addLessThanOrEquals(MessagesColumns.TIMESTAMP, value);
        return this;
    }

    public MessagesSelection message(String... value) {
        addEquals(MessagesColumns.MESSAGE, value);
        return this;
    }

    public MessagesSelection messageNot(String... value) {
        addNotEquals(MessagesColumns.MESSAGE, value);
        return this;
    }

    public MessagesSelection messageLike(String... value) {
        addLike(MessagesColumns.MESSAGE, value);
        return this;
    }
}
