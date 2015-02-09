package ua.com.studiovision.euromaidan.network.provider.messages;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code messages} table.
 */
public class MessagesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MessagesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, MessagesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MessagesContentValues putUserId(int value) {
        mContentValues.put(MessagesColumns.USER_ID, value);
        return this;
    }



    public MessagesContentValues putTimestamp(Date value) {
        if (value == null) throw new IllegalArgumentException("value for timestamp must not be null");
        mContentValues.put(MessagesColumns.TIMESTAMP, value.getTime());
        return this;
    }


    public MessagesContentValues putTimestamp(long value) {
        mContentValues.put(MessagesColumns.TIMESTAMP, value);
        return this;
    }


    public MessagesContentValues putMessage(String value) {
        if (value == null) throw new IllegalArgumentException("value for message must not be null");
        mContentValues.put(MessagesColumns.MESSAGE, value);
        return this;
    }


}
