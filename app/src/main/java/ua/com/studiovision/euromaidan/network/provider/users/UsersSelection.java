package ua.com.studiovision.euromaidan.network.provider.users;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code users} table.
 */
public class UsersSelection extends AbstractSelection<UsersSelection> {
    @Override
    public Uri uri() {
        return UsersColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code UsersCursor} object, which is positioned before the first entry, or null.
     */
    public UsersCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new UsersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public UsersCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public UsersCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public UsersSelection id(long... value) {
        addEquals("users." + UsersColumns._ID, toObjectArray(value));
        return this;
    }


    public UsersSelection userId(long... value) {
        addEquals(UsersColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public UsersSelection userIdNot(long... value) {
        addNotEquals(UsersColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public UsersSelection userIdGt(long value) {
        addGreaterThan(UsersColumns.USER_ID, value);
        return this;
    }

    public UsersSelection userIdGtEq(long value) {
        addGreaterThanOrEquals(UsersColumns.USER_ID, value);
        return this;
    }

    public UsersSelection userIdLt(long value) {
        addLessThan(UsersColumns.USER_ID, value);
        return this;
    }

    public UsersSelection userIdLtEq(long value) {
        addLessThanOrEquals(UsersColumns.USER_ID, value);
        return this;
    }

    public UsersSelection userName(String... value) {
        addEquals(UsersColumns.USER_NAME, value);
        return this;
    }

    public UsersSelection userNameNot(String... value) {
        addNotEquals(UsersColumns.USER_NAME, value);
        return this;
    }

    public UsersSelection userNameLike(String... value) {
        addLike(UsersColumns.USER_NAME, value);
        return this;
    }

    public UsersSelection userSurname(String... value) {
        addEquals(UsersColumns.USER_SURNAME, value);
        return this;
    }

    public UsersSelection userSurnameNot(String... value) {
        addNotEquals(UsersColumns.USER_SURNAME, value);
        return this;
    }

    public UsersSelection userSurnameLike(String... value) {
        addLike(UsersColumns.USER_SURNAME, value);
        return this;
    }

    public UsersSelection userNameLowercase(String... value) {
        addEquals(UsersColumns.USER_NAME_LOWERCASE, value);
        return this;
    }

    public UsersSelection userNameLowercaseNot(String... value) {
        addNotEquals(UsersColumns.USER_NAME_LOWERCASE, value);
        return this;
    }

    public UsersSelection userNameLowercaseLike(String... value) {
        addLike(UsersColumns.USER_NAME_LOWERCASE, value);
        return this;
    }

    public UsersSelection userSurnameLowercase(String... value) {
        addEquals(UsersColumns.USER_SURNAME_LOWERCASE, value);
        return this;
    }

    public UsersSelection userSurnameLowercaseNot(String... value) {
        addNotEquals(UsersColumns.USER_SURNAME_LOWERCASE, value);
        return this;
    }

    public UsersSelection userSurnameLowercaseLike(String... value) {
        addLike(UsersColumns.USER_SURNAME_LOWERCASE, value);
        return this;
    }

    public UsersSelection avatar(String... value) {
        addEquals(UsersColumns.AVATAR, value);
        return this;
    }

    public UsersSelection avatarNot(String... value) {
        addNotEquals(UsersColumns.AVATAR, value);
        return this;
    }

    public UsersSelection avatarLike(String... value) {
        addLike(UsersColumns.AVATAR, value);
        return this;
    }
}
