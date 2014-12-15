package ua.com.studiovision.euromaidan.network.provider.friends;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code friends} table.
 */
public class FriendsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FriendsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, FriendsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Friend id from server
     */
    public FriendsContentValues putFriendId(long value) {
        mContentValues.put(FriendsColumns.FRIEND_ID, value);
        return this;
    }



    /**
     * Friend name
     */
    public FriendsContentValues putFriendName(String value) {
        if (value == null) throw new IllegalArgumentException("value for friendName must not be null");
        mContentValues.put(FriendsColumns.FRIEND_NAME, value);
        return this;
    }



    /**
     * Friend surname
     */
    public FriendsContentValues putFriendSurname(String value) {
        if (value == null) throw new IllegalArgumentException("value for friendSurname must not be null");
        mContentValues.put(FriendsColumns.FRIEND_SURNAME, value);
        return this;
    }



    /**
     * Friend avatar
     */
    public FriendsContentValues putFriendAvatar(String value) {
        if (value == null) throw new IllegalArgumentException("value for friendAvatar must not be null");
        mContentValues.put(FriendsColumns.FRIEND_AVATAR, value);
        return this;
    }


}
