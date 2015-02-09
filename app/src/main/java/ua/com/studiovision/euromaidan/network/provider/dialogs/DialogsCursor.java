package ua.com.studiovision.euromaidan.network.provider.dialogs;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code dialogs} table.
 */
public class DialogsCursor extends AbstractCursor {
    public DialogsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code message} value.
     * Cannot be {@code null}.
     */
    public String getMessage() {
        Integer index = getCachedColumnIndexOrThrow(DialogsColumns.MESSAGE);
        return getString(index);
    }

    /**
     * Get the {@code mine} value.
     */
    public boolean getMine() {
        return getBoolean(DialogsColumns.MINE);
    }

    /**
     * Get the {@code new_count} value.
     */
    public int getNewCount() {
        return getIntegerOrNull(DialogsColumns.NEW_COUNT);
    }

    /**
     * Get the {@code time} value.
     * Cannot be {@code null}.
     */
    public Date getTime() {
        return getDate(DialogsColumns.TIME);
    }

    /**
     * Get the {@code user_id} value.
     */
    public long getUserId() {
        return getLongOrNull(DialogsColumns.USER_ID);
    }
}
