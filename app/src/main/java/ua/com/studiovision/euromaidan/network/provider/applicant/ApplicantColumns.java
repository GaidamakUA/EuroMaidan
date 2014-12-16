package ua.com.studiovision.euromaidan.network.provider.applicant;

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
 * Represents applicants entity
 */
public class ApplicantColumns implements BaseColumns {
    public static final String TABLE_NAME = "applicant";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * Applicant id from server
     */
    public static final String APPLICANT_ID = "applicant_id";

    /**
     * Applicant name
     */
    public static final String APPLICANT_NAME = "applicant_name";

    /**
     * Applicant surname
     */
    public static final String APPLICANT_SURNAME = "applicant_surname";

    /**
     * Applicant avatar
     */
    public static final String APPLICANT_AVATAR = "applicant_avatar";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            APPLICANT_ID,
            APPLICANT_NAME,
            APPLICANT_SURNAME,
            APPLICANT_AVATAR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == APPLICANT_ID || c.contains("." + APPLICANT_ID)) return true;
            if (c == APPLICANT_NAME || c.contains("." + APPLICANT_NAME)) return true;
            if (c == APPLICANT_SURNAME || c.contains("." + APPLICANT_SURNAME)) return true;
            if (c == APPLICANT_AVATAR || c.contains("." + APPLICANT_AVATAR)) return true;
        }
        return false;
    }

}
