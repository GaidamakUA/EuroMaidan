package ua.com.studiovision.euromaidan.network.provider.university;

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
 * Represents university entity
 */
public class UniversityColumns implements BaseColumns {
    public static final String TABLE_NAME = "university";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * University id from server
     */
    public static final String UNIVERSITY_ID = "university_id";

    /**
     * University name
     */
    public static final String UNIVERSITY_NAME = "university_name";

    /**
     * University name lowercase
     */
    public static final String UNIVERSITY_NAME_LOWERCASE = "university_name_lowercase";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            UNIVERSITY_ID,
            UNIVERSITY_NAME,
            UNIVERSITY_NAME_LOWERCASE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == UNIVERSITY_ID || c.contains("." + UNIVERSITY_ID)) return true;
            if (c == UNIVERSITY_NAME || c.contains("." + UNIVERSITY_NAME)) return true;
            if (c == UNIVERSITY_NAME_LOWERCASE || c.contains("." + UNIVERSITY_NAME_LOWERCASE)) return true;
        }
        return false;
    }

}
