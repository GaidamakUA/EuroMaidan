package ua.com.studiovision.euromaidan.network.provider.friends;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code friends} table.
 */
public class FriendsSelection extends AbstractSelection<FriendsSelection> {
    @Override
    public Uri uri() {
        return FriendsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code FriendsCursor} object, which is positioned before the first entry, or null.
     */
    public FriendsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new FriendsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public FriendsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public FriendsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public FriendsSelection id(long... value) {
        addEquals("friends." + FriendsColumns._ID, toObjectArray(value));
        return this;
    }


    public FriendsSelection friendId(long... value) {
        addEquals(FriendsColumns.FRIEND_ID, toObjectArray(value));
        return this;
    }

    public FriendsSelection friendIdNot(long... value) {
        addNotEquals(FriendsColumns.FRIEND_ID, toObjectArray(value));
        return this;
    }

    public FriendsSelection friendIdGt(long value) {
        addGreaterThan(FriendsColumns.FRIEND_ID, value);
        return this;
    }

    public FriendsSelection friendIdGtEq(long value) {
        addGreaterThanOrEquals(FriendsColumns.FRIEND_ID, value);
        return this;
    }

    public FriendsSelection friendIdLt(long value) {
        addLessThan(FriendsColumns.FRIEND_ID, value);
        return this;
    }

    public FriendsSelection friendIdLtEq(long value) {
        addLessThanOrEquals(FriendsColumns.FRIEND_ID, value);
        return this;
    }

    public FriendsSelection friendName(String... value) {
        addEquals(FriendsColumns.FRIEND_NAME, value);
        return this;
    }

    public FriendsSelection friendNameNot(String... value) {
        addNotEquals(FriendsColumns.FRIEND_NAME, value);
        return this;
    }

    public FriendsSelection friendNameLike(String... value) {
        addLike(FriendsColumns.FRIEND_NAME, value);
        return this;
    }

    public FriendsSelection friendSurname(String... value) {
        addEquals(FriendsColumns.FRIEND_SURNAME, value);
        return this;
    }

    public FriendsSelection friendSurnameNot(String... value) {
        addNotEquals(FriendsColumns.FRIEND_SURNAME, value);
        return this;
    }

    public FriendsSelection friendSurnameLike(String... value) {
        addLike(FriendsColumns.FRIEND_SURNAME, value);
        return this;
    }

    public FriendsSelection friendAvatar(String... value) {
        addEquals(FriendsColumns.FRIEND_AVATAR, value);
        return this;
    }

    public FriendsSelection friendAvatarNot(String... value) {
        addNotEquals(FriendsColumns.FRIEND_AVATAR, value);
        return this;
    }

    public FriendsSelection friendAvatarLike(String... value) {
        addLike(FriendsColumns.FRIEND_AVATAR, value);
        return this;
    }
}
