package ua.com.studiovision.euromaidan.provider.school;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.studiovision.euromaidan.provider.EmContentProvider;

/**
 * Represents school entity
 */
public class SchoolColumns implements BaseColumns {
    public static final String TABLE_NAME = "school";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * School id from server
     */
    public static final String SCHOOL_ID = "school_id";

    /**
     * School name
     */
    public static final String SCHOOL_NAME = "school_name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SCHOOL_ID,
            SCHOOL_NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == SCHOOL_ID || c.contains("." + SCHOOL_ID)) return true;
            if (c == SCHOOL_NAME || c.contains("." + SCHOOL_NAME)) return true;
        }
        return false;
    }

}
