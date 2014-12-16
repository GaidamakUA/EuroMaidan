package ua.com.studiovision.euromaidan.network.provider.followers;

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
 * Represents followers entity
 */
public class FollowersColumns implements BaseColumns {
    public static final String TABLE_NAME = "followers";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * Follower id from server
     */
    public static final String FOLLOWER_ID = "follower_id";

    /**
     * Follower name
     */
    public static final String FOLLOWER_NAME = "follower_name";

    /**
     * Follower surname
     */
    public static final String FOLLOWER_SURNAME = "follower_surname";

    /**
     * Follower avatar
     */
    public static final String FOLLOWER_AVATAR = "follower_avatar";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FOLLOWER_ID,
            FOLLOWER_NAME,
            FOLLOWER_SURNAME,
            FOLLOWER_AVATAR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == FOLLOWER_ID || c.contains("." + FOLLOWER_ID)) return true;
            if (c == FOLLOWER_NAME || c.contains("." + FOLLOWER_NAME)) return true;
            if (c == FOLLOWER_SURNAME || c.contains("." + FOLLOWER_SURNAME)) return true;
            if (c == FOLLOWER_AVATAR || c.contains("." + FOLLOWER_AVATAR)) return true;
        }
        return false;
    }

}
