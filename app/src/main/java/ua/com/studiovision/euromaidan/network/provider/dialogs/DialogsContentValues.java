package ua.com.studiovision.euromaidan.network.provider.dialogs;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code dialogs} table.
 */
public class DialogsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return DialogsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, DialogsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public DialogsContentValues putMessage(String value) {
        if (value == null) throw new IllegalArgumentException("value for message must not be null");
        mContentValues.put(DialogsColumns.MESSAGE, value);
        return this;
    }



    public DialogsContentValues putMine(boolean value) {
        mContentValues.put(DialogsColumns.MINE, value);
        return this;
    }



    public DialogsContentValues putNewCount(int value) {
        mContentValues.put(DialogsColumns.NEW_COUNT, value);
        return this;
    }



    public DialogsContentValues putTime(Date value) {
        if (value == null) throw new IllegalArgumentException("value for time must not be null");
        mContentValues.put(DialogsColumns.TIME, value.getTime());
        return this;
    }


    public DialogsContentValues putTime(long value) {
        mContentValues.put(DialogsColumns.TIME, value);
        return this;
    }


    public DialogsContentValues putUserId(long value) {
        mContentValues.put(DialogsColumns.USER_ID, value);
        return this;
    }


}
