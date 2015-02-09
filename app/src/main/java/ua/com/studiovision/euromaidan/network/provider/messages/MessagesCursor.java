package ua.com.studiovision.euromaidan.network.provider.messages;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code messages} table.
 */
public class MessagesCursor extends AbstractCursor {
    public MessagesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code user_id} value.
     */
    public int getUserId() {
        return getIntegerOrNull(MessagesColumns.USER_ID);
    }

    /**
     * Get the {@code timestamp} value.
     * Cannot be {@code null}.
     */
    public Date getTimestamp() {
        return getDate(MessagesColumns.TIMESTAMP);
    }

    /**
     * Get the {@code message} value.
     * Cannot be {@code null}.
     */
    public String getMessage() {
        Integer index = getCachedColumnIndexOrThrow(MessagesColumns.MESSAGE);
        return getString(index);
    }
}
