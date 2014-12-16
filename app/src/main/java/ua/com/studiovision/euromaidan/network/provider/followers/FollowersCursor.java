package ua.com.studiovision.euromaidan.network.provider.followers;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code followers} table.
 */
public class FollowersCursor extends AbstractCursor {
    public FollowersCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Follower id from server
     */
    public long getFollowerId() {
        return getLongOrNull(FollowersColumns.FOLLOWER_ID);
    }

    /**
     * Follower name
     * Cannot be {@code null}.
     */
    public String getFollowerName() {
        Integer index = getCachedColumnIndexOrThrow(FollowersColumns.FOLLOWER_NAME);
        return getString(index);
    }

    /**
     * Follower surname
     * Cannot be {@code null}.
     */
    public String getFollowerSurname() {
        Integer index = getCachedColumnIndexOrThrow(FollowersColumns.FOLLOWER_SURNAME);
        return getString(index);
    }

    /**
     * Follower avatar
     * Cannot be {@code null}.
     */
    public String getFollowerAvatar() {
        Integer index = getCachedColumnIndexOrThrow(FollowersColumns.FOLLOWER_AVATAR);
        return getString(index);
    }
}
