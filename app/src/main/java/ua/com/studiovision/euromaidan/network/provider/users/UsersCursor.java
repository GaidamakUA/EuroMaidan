package ua.com.studiovision.euromaidan.network.provider.users;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code users} table.
 */
public class UsersCursor extends AbstractCursor {
    public UsersCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code user_id} value.
     */
    public long getUserId() {
        return getLongOrNull(UsersColumns.USER_ID);
    }

    /**
     * Get the {@code user_name} value.
     * Cannot be {@code null}.
     */
    public String getUserName() {
        Integer index = getCachedColumnIndexOrThrow(UsersColumns.USER_NAME);
        return getString(index);
    }

    /**
     * Get the {@code user_surname} value.
     * Cannot be {@code null}.
     */
    public String getUserSurname() {
        Integer index = getCachedColumnIndexOrThrow(UsersColumns.USER_SURNAME);
        return getString(index);
    }

    /**
     * Get the {@code user_name_lowercase} value.
     * Cannot be {@code null}.
     */
    public String getUserNameLowercase() {
        Integer index = getCachedColumnIndexOrThrow(UsersColumns.USER_NAME_LOWERCASE);
        return getString(index);
    }

    /**
     * Get the {@code user_surname_lowercase} value.
     * Cannot be {@code null}.
     */
    public String getUserSurnameLowercase() {
        Integer index = getCachedColumnIndexOrThrow(UsersColumns.USER_SURNAME_LOWERCASE);
        return getString(index);
    }

    /**
     * Get the {@code avatar} value.
     * Cannot be {@code null}.
     */
    public String getAvatar() {
        Integer index = getCachedColumnIndexOrThrow(UsersColumns.AVATAR);
        return getString(index);
    }
}
