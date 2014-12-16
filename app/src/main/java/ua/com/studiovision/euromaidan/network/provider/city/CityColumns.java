package ua.com.studiovision.euromaidan.network.provider.city;

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
 * Columns for the {@code city} table.
 */
public class CityColumns implements BaseColumns {
    public static final String TABLE_NAME = "city";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String CITY_ID = "city_id";

    /**
     * City name
     */
    public static final String CITY_NAME = "city_name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CITY_ID,
            CITY_NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == CITY_ID || c.contains("." + CITY_ID)) return true;
            if (c == CITY_NAME || c.contains("." + CITY_NAME)) return true;
        }
        return false;
    }

}
