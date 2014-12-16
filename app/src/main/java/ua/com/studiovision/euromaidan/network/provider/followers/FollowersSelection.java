package ua.com.studiovision.euromaidan.network.provider.followers;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code followers} table.
 */
public class FollowersSelection extends AbstractSelection<FollowersSelection> {
    @Override
    public Uri uri() {
        return FollowersColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code FollowersCursor} object, which is positioned before the first entry, or null.
     */
    public FollowersCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new FollowersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public FollowersCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public FollowersCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public FollowersSelection id(long... value) {
        addEquals("followers." + FollowersColumns._ID, toObjectArray(value));
        return this;
    }


    public FollowersSelection followerId(long... value) {
        addEquals(FollowersColumns.FOLLOWER_ID, toObjectArray(value));
        return this;
    }

    public FollowersSelection followerIdNot(long... value) {
        addNotEquals(FollowersColumns.FOLLOWER_ID, toObjectArray(value));
        return this;
    }

    public FollowersSelection followerIdGt(long value) {
        addGreaterThan(FollowersColumns.FOLLOWER_ID, value);
        return this;
    }

    public FollowersSelection followerIdGtEq(long value) {
        addGreaterThanOrEquals(FollowersColumns.FOLLOWER_ID, value);
        return this;
    }

    public FollowersSelection followerIdLt(long value) {
        addLessThan(FollowersColumns.FOLLOWER_ID, value);
        return this;
    }

    public FollowersSelection followerIdLtEq(long value) {
        addLessThanOrEquals(FollowersColumns.FOLLOWER_ID, value);
        return this;
    }

    public FollowersSelection followerName(String... value) {
        addEquals(FollowersColumns.FOLLOWER_NAME, value);
        return this;
    }

    public FollowersSelection followerNameNot(String... value) {
        addNotEquals(FollowersColumns.FOLLOWER_NAME, value);
        return this;
    }

    public FollowersSelection followerNameLike(String... value) {
        addLike(FollowersColumns.FOLLOWER_NAME, value);
        return this;
    }

    public FollowersSelection followerSurname(String... value) {
        addEquals(FollowersColumns.FOLLOWER_SURNAME, value);
        return this;
    }

    public FollowersSelection followerSurnameNot(String... value) {
        addNotEquals(FollowersColumns.FOLLOWER_SURNAME, value);
        return this;
    }

    public FollowersSelection followerSurnameLike(String... value) {
        addLike(FollowersColumns.FOLLOWER_SURNAME, value);
        return this;
    }

    public FollowersSelection followerAvatar(String... value) {
        addEquals(FollowersColumns.FOLLOWER_AVATAR, value);
        return this;
    }

    public FollowersSelection followerAvatarNot(String... value) {
        addNotEquals(FollowersColumns.FOLLOWER_AVATAR, value);
        return this;
    }

    public FollowersSelection followerAvatarLike(String... value) {
        addLike(FollowersColumns.FOLLOWER_AVATAR, value);
        return this;
    }
}
