package ua.com.studiovision.euromaidan.network.provider.friends;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code friends} table.
 */
public class FriendsCursor extends AbstractCursor {
    public FriendsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Friend id from server
     */
    public long getFriendId() {
        return getLongOrNull(FriendsColumns.FRIEND_ID);
    }

    /**
     * Friend name
     * Cannot be {@code null}.
     */
    public String getFriendName() {
        Integer index = getCachedColumnIndexOrThrow(FriendsColumns.FRIEND_NAME);
        return getString(index);
    }

    /**
     * Friend surname
     * Cannot be {@code null}.
     */
    public String getFriendSurname() {
        Integer index = getCachedColumnIndexOrThrow(FriendsColumns.FRIEND_SURNAME);
        return getString(index);
    }

    /**
     * Friend avatar
     * Cannot be {@code null}.
     */
    public String getFriendAvatar() {
        Integer index = getCachedColumnIndexOrThrow(FriendsColumns.FRIEND_AVATAR);
        return getString(index);
    }
}
