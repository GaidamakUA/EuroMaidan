package ua.com.studiovision.euromaidan.network.provider.dialogs;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code dialogs} table.
 */
public class DialogsSelection extends AbstractSelection<DialogsSelection> {
    @Override
    public Uri uri() {
        return DialogsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code DialogsCursor} object, which is positioned before the first entry, or null.
     */
    public DialogsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new DialogsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public DialogsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public DialogsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public DialogsSelection id(long... value) {
        addEquals("dialogs." + DialogsColumns._ID, toObjectArray(value));
        return this;
    }


    public DialogsSelection message(String... value) {
        addEquals(DialogsColumns.MESSAGE, value);
        return this;
    }

    public DialogsSelection messageNot(String... value) {
        addNotEquals(DialogsColumns.MESSAGE, value);
        return this;
    }

    public DialogsSelection messageLike(String... value) {
        addLike(DialogsColumns.MESSAGE, value);
        return this;
    }

    public DialogsSelection mine(boolean value) {
        addEquals(DialogsColumns.MINE, toObjectArray(value));
        return this;
    }

    public DialogsSelection newCount(int... value) {
        addEquals(DialogsColumns.NEW_COUNT, toObjectArray(value));
        return this;
    }

    public DialogsSelection newCountNot(int... value) {
        addNotEquals(DialogsColumns.NEW_COUNT, toObjectArray(value));
        return this;
    }

    public DialogsSelection newCountGt(int value) {
        addGreaterThan(DialogsColumns.NEW_COUNT, value);
        return this;
    }

    public DialogsSelection newCountGtEq(int value) {
        addGreaterThanOrEquals(DialogsColumns.NEW_COUNT, value);
        return this;
    }

    public DialogsSelection newCountLt(int value) {
        addLessThan(DialogsColumns.NEW_COUNT, value);
        return this;
    }

    public DialogsSelection newCountLtEq(int value) {
        addLessThanOrEquals(DialogsColumns.NEW_COUNT, value);
        return this;
    }

    public DialogsSelection time(Date... value) {
        addEquals(DialogsColumns.TIME, value);
        return this;
    }

    public DialogsSelection timeNot(Date... value) {
        addNotEquals(DialogsColumns.TIME, value);
        return this;
    }

    public DialogsSelection time(long... value) {
        addEquals(DialogsColumns.TIME, toObjectArray(value));
        return this;
    }

    public DialogsSelection timeAfter(Date value) {
        addGreaterThan(DialogsColumns.TIME, value);
        return this;
    }

    public DialogsSelection timeAfterEq(Date value) {
        addGreaterThanOrEquals(DialogsColumns.TIME, value);
        return this;
    }

    public DialogsSelection timeBefore(Date value) {
        addLessThan(DialogsColumns.TIME, value);
        return this;
    }

    public DialogsSelection timeBeforeEq(Date value) {
        addLessThanOrEquals(DialogsColumns.TIME, value);
        return this;
    }

    public DialogsSelection userId(long... value) {
        addEquals(DialogsColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public DialogsSelection userIdNot(long... value) {
        addNotEquals(DialogsColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public DialogsSelection userIdGt(long value) {
        addGreaterThan(DialogsColumns.USER_ID, value);
        return this;
    }

    public DialogsSelection userIdGtEq(long value) {
        addGreaterThanOrEquals(DialogsColumns.USER_ID, value);
        return this;
    }

    public DialogsSelection userIdLt(long value) {
        addLessThan(DialogsColumns.USER_ID, value);
        return this;
    }

    public DialogsSelection userIdLtEq(long value) {
        addLessThanOrEquals(DialogsColumns.USER_ID, value);
        return this;
    }
}
