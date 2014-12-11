package ua.com.studiovision.euromaidan.network.provider.users;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.studiovision.euromaidan.network.provider.EmContentProvider;
import ua.com.studiovision.euromaidan.network.provider.school.SchoolColumns;
import ua.com.studiovision.euromaidan.network.provider.university.UniversityColumns;
import ua.com.studiovision.euromaidan.network.provider.country.CountryColumns;
import ua.com.studiovision.euromaidan.network.provider.city.CityColumns;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosColumns;
import ua.com.studiovision.euromaidan.network.provider.users.UsersColumns;
import ua.com.studiovision.euromaidan.network.provider.videos.VideosColumns;

/**
 * Columns for the {@code users} table.
 */
public class UsersColumns implements BaseColumns {
    public static final String TABLE_NAME = "users";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String USER_SURNAME = "user_surname";

    public static final String USER_NAME_LOWERCASE = "user_name_lowercase";

    public static final String USER_SURNAME_LOWERCASE = "user_surname_lowercase";

    public static final String AVATAR = "avatar";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            USER_ID,
            USER_NAME,
            USER_SURNAME,
            USER_NAME_LOWERCASE,
            USER_SURNAME_LOWERCASE,
            AVATAR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == USER_ID || c.contains("." + USER_ID)) return true;
            if (c == USER_NAME || c.contains("." + USER_NAME)) return true;
            if (c == USER_SURNAME || c.contains("." + USER_SURNAME)) return true;
            if (c == USER_NAME_LOWERCASE || c.contains("." + USER_NAME_LOWERCASE)) return true;
            if (c == USER_SURNAME_LOWERCASE || c.contains("." + USER_SURNAME_LOWERCASE)) return true;
            if (c == AVATAR || c.contains("." + AVATAR)) return true;
        }
        return false;
    }

}
