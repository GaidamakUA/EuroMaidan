package ua.com.studiovision.euromaidan.network.provider.dialogs;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.studiovision.euromaidan.network.provider.EmContentProvider;
import ua.com.studiovision.euromaidan.network.provider.dialogs.DialogsColumns;
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
import ua.com.studiovision.euromaidan.network.provider.messages.MessagesColumns;

/**
 * Columns for the {@code dialogs} table.
 */
public class DialogsColumns implements BaseColumns {
    public static final String TABLE_NAME = "dialogs";
    public static final Uri CONTENT_URI = Uri.parse(EmContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String MESSAGE = "message";

    public static final String MINE = "mine";

    public static final String NEW_COUNT = "new_count";

    public static final String TIME = "time";

    public static final String USER_ID = "user_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MESSAGE,
            MINE,
            NEW_COUNT,
            TIME,
            USER_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == MESSAGE || c.contains("." + MESSAGE)) return true;
            if (c == MINE || c.contains("." + MINE)) return true;
            if (c == NEW_COUNT || c.contains("." + NEW_COUNT)) return true;
            if (c == TIME || c.contains("." + TIME)) return true;
            if (c == USER_ID || c.contains("." + USER_ID)) return true;
        }
        return false;
    }

}
