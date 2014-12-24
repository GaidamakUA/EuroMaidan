package ua.com.studiovision.euromaidan.network.provider.audios;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.studiovision.euromaidan.network.provider.EmContentProvider;

/**
 * Columns for the {@code audios} table.
 */
public class AudiosColumns implements BaseColumns {
    public static final String TABLE_NAME = "audios";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String NAME = "name";

    public static final String NAME_LOWERCASE = "name_lowercase";

    public static final String AUTHOR = "author";

    public static final String AUTHOR_LOWERCASE = "author_lowercase";

    public static final String DURATION = "duration";

    public static final String AUDIO_URL = "audio_url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            NAME_LOWERCASE,
            AUTHOR,
            AUTHOR_LOWERCASE,
            DURATION,
            AUDIO_URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == NAME || c.contains("." + NAME)) return true;
            if (c == NAME_LOWERCASE || c.contains("." + NAME_LOWERCASE)) return true;
            if (c == AUTHOR || c.contains("." + AUTHOR)) return true;
            if (c == AUTHOR_LOWERCASE || c.contains("." + AUTHOR_LOWERCASE)) return true;
            if (c == DURATION || c.contains("." + DURATION)) return true;
            if (c == AUDIO_URL || c.contains("." + AUDIO_URL)) return true;
        }
        return false;
    }

}
