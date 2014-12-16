package ua.com.studiovision.euromaidan.network.provider.friends;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.studiovision.euromaidan.network.provider.EmContentProvider;
import ua.com.studiovision.euromaidan.network.provider.school.SchoolColumns;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsColumns;
import ua.com.studiovision.euromaidan.network.provider.university.UniversityColumns;
import ua.com.studiovision.euromaidan.network.provider.country.CountryColumns;
import ua.com.studiovision.euromaidan.network.provider.city.CityColumns;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosColumns;
import ua.com.studiovision.euromaidan.network.provider.followers.FollowersColumns;
import ua.com.studiovision.euromaidan.network.provider.users.UsersColumns;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantColumns;
import ua.com.studiovision.euromaidan.network.provider.videos.VideosColumns;

/**
 * Represents friends entity
 */
public class FriendsColumns implements BaseColumns {
    public static final String TABLE_NAME = "friends";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * Friend id from server
     */
    public static final String FRIEND_ID = "friend_id";

    /**
     * Friend name
     */
    public static final String FRIEND_NAME = "friend_name";

    /**
     * Friend surname
     */
    public static final String FRIEND_SURNAME = "friend_surname";

    /**
     * Friend avatar
     */
    public static final String FRIEND_AVATAR = "friend_avatar";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FRIEND_ID,
            FRIEND_NAME,
            FRIEND_SURNAME,
            FRIEND_AVATAR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == FRIEND_ID || c.contains("." + FRIEND_ID)) return true;
            if (c == FRIEND_NAME || c.contains("." + FRIEND_NAME)) return true;
            if (c == FRIEND_SURNAME || c.contains("." + FRIEND_SURNAME)) return true;
            if (c == FRIEND_AVATAR || c.contains("." + FRIEND_AVATAR)) return true;
        }
        return false;
    }

}
