package ua.com.studiovision.euromaidan.provider.country;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.studiovision.euromaidan.provider.EmContentProvider;
import ua.com.studiovision.euromaidan.provider.country.CountryColumns;

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
    public static final String ID = "id";

    /**
     * Country name
     */
    public static final String COUNTRY_NAME = "country_name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ID,
            COUNTRY_NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == ID || c.contains("." + ID)) return true;
            if (c == COUNTRY_NAME || c.contains("." + COUNTRY_NAME)) return true;
        }
        return false;
    }

}
