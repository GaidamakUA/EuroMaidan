package ua.com.studiovision.euromaidan.network.provider.country;

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
 * Represents country entity
 */
public class CountryColumns implements BaseColumns {
    public static final String TABLE_NAME = "country";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * Country id from server
     */
    public static final String COUNTRY_ID = "country_id";

    /**
     * Country name
     */
    public static final String COUNTRY_NAME = "country_name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            COUNTRY_ID,
            COUNTRY_NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == COUNTRY_ID || c.contains("." + COUNTRY_ID)) return true;
            if (c == COUNTRY_NAME || c.contains("." + COUNTRY_NAME)) return true;
        }
        return false;
    }

}
