package ua.com.studiovision.euromaidan.network.provider.followers;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code followers} table.
 */
public class FollowersContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FollowersColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, FollowersSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Follower id from server
     */
    public FollowersContentValues putFollowerId(long value) {
        mContentValues.put(FollowersColumns.FOLLOWER_ID, value);
        return this;
    }



    /**
     * Follower name
     */
    public FollowersContentValues putFollowerName(String value) {
        if (value == null) throw new IllegalArgumentException("value for followerName must not be null");
        mContentValues.put(FollowersColumns.FOLLOWER_NAME, value);
        return this;
    }



    /**
     * Follower surname
     */
    public FollowersContentValues putFollowerSurname(String value) {
        if (value == null) throw new IllegalArgumentException("value for followerSurname must not be null");
        mContentValues.put(FollowersColumns.FOLLOWER_SURNAME, value);
        return this;
    }



    /**
     * Follower avatar
     */
    public FollowersContentValues putFollowerAvatar(String value) {
        if (value == null) throw new IllegalArgumentException("value for followerAvatar must not be null");
        mContentValues.put(FollowersColumns.FOLLOWER_AVATAR, value);
        return this;
    }


}
