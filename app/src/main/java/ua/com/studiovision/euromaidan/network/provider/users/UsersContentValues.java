package ua.com.studiovision.euromaidan.network.provider.users;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code users} table.
 */
public class UsersContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return UsersColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, UsersSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public UsersContentValues putUserId(long value) {
        mContentValues.put(UsersColumns.USER_ID, value);
        return this;
    }



    public UsersContentValues putUserName(String value) {
        if (value == null) throw new IllegalArgumentException("value for userName must not be null");
        mContentValues.put(UsersColumns.USER_NAME, value);
        return this;
    }



    public UsersContentValues putUserSurname(String value) {
        if (value == null) throw new IllegalArgumentException("value for userSurname must not be null");
        mContentValues.put(UsersColumns.USER_SURNAME, value);
        return this;
    }



    public UsersContentValues putUserNameLowercase(String value) {
        if (value == null) throw new IllegalArgumentException("value for userNameLowercase must not be null");
        mContentValues.put(UsersColumns.USER_NAME_LOWERCASE, value);
        return this;
    }



    public UsersContentValues putUserSurnameLowercase(String value) {
        if (value == null) throw new IllegalArgumentException("value for userSurnameLowercase must not be null");
        mContentValues.put(UsersColumns.USER_SURNAME_LOWERCASE, value);
        return this;
    }



    public UsersContentValues putAvatar(String value) {
        if (value == null) throw new IllegalArgumentException("value for avatar must not be null");
        mContentValues.put(UsersColumns.AVATAR, value);
        return this;
    }


}
