package ua.com.studiovision.euromaidan.network.provider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import ua.com.studiovision.euromaidan.BuildConfig;
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

public class EmContentProvider extends ContentProvider {
    private static final String TAG = EmContentProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "ua.com.studiovision.euromaidan.network.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    public static final String QUERY_NOTIFY = "QUERY_NOTIFY";
    public static final String QUERY_GROUP_BY = "QUERY_GROUP_BY";

    private static final int URI_TYPE_DIALOGS = 0;
    private static final int URI_TYPE_DIALOGS_ID = 1;

    private static final int URI_TYPE_SCHOOL = 2;
    private static final int URI_TYPE_SCHOOL_ID = 3;

    private static final int URI_TYPE_FRIENDS = 4;
    private static final int URI_TYPE_FRIENDS_ID = 5;

    private static final int URI_TYPE_UNIVERSITY = 6;
    private static final int URI_TYPE_UNIVERSITY_ID = 7;

    private static final int URI_TYPE_COUNTRY = 8;
    private static final int URI_TYPE_COUNTRY_ID = 9;

    private static final int URI_TYPE_CITY = 10;
    private static final int URI_TYPE_CITY_ID = 11;

    private static final int URI_TYPE_AUDIOS = 12;
    private static final int URI_TYPE_AUDIOS_ID = 13;

    private static final int URI_TYPE_FOLLOWERS = 14;
    private static final int URI_TYPE_FOLLOWERS_ID = 15;

    private static final int URI_TYPE_USERS = 16;
    private static final int URI_TYPE_USERS_ID = 17;

    private static final int URI_TYPE_APPLICANT = 18;
    private static final int URI_TYPE_APPLICANT_ID = 19;

    private static final int URI_TYPE_VIDEOS = 20;
    private static final int URI_TYPE_VIDEOS_ID = 21;

    private static final int URI_TYPE_MESSAGES = 22;
    private static final int URI_TYPE_MESSAGES_ID = 23;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DialogsColumns.TABLE_NAME, URI_TYPE_DIALOGS);
        URI_MATCHER.addURI(AUTHORITY, DialogsColumns.TABLE_NAME + "/#", URI_TYPE_DIALOGS_ID);
        URI_MATCHER.addURI(AUTHORITY, SchoolColumns.TABLE_NAME, URI_TYPE_SCHOOL);
        URI_MATCHER.addURI(AUTHORITY, SchoolColumns.TABLE_NAME + "/#", URI_TYPE_SCHOOL_ID);
        URI_MATCHER.addURI(AUTHORITY, FriendsColumns.TABLE_NAME, URI_TYPE_FRIENDS);
        URI_MATCHER.addURI(AUTHORITY, FriendsColumns.TABLE_NAME + "/#", URI_TYPE_FRIENDS_ID);
        URI_MATCHER.addURI(AUTHORITY, UniversityColumns.TABLE_NAME, URI_TYPE_UNIVERSITY);
        URI_MATCHER.addURI(AUTHORITY, UniversityColumns.TABLE_NAME + "/#", URI_TYPE_UNIVERSITY_ID);
        URI_MATCHER.addURI(AUTHORITY, CountryColumns.TABLE_NAME, URI_TYPE_COUNTRY);
        URI_MATCHER.addURI(AUTHORITY, CountryColumns.TABLE_NAME + "/#", URI_TYPE_COUNTRY_ID);
        URI_MATCHER.addURI(AUTHORITY, CityColumns.TABLE_NAME, URI_TYPE_CITY);
        URI_MATCHER.addURI(AUTHORITY, CityColumns.TABLE_NAME + "/#", URI_TYPE_CITY_ID);
        URI_MATCHER.addURI(AUTHORITY, AudiosColumns.TABLE_NAME, URI_TYPE_AUDIOS);
        URI_MATCHER.addURI(AUTHORITY, AudiosColumns.TABLE_NAME + "/#", URI_TYPE_AUDIOS_ID);
        URI_MATCHER.addURI(AUTHORITY, FollowersColumns.TABLE_NAME, URI_TYPE_FOLLOWERS);
        URI_MATCHER.addURI(AUTHORITY, FollowersColumns.TABLE_NAME + "/#", URI_TYPE_FOLLOWERS_ID);
        URI_MATCHER.addURI(AUTHORITY, UsersColumns.TABLE_NAME, URI_TYPE_USERS);
        URI_MATCHER.addURI(AUTHORITY, UsersColumns.TABLE_NAME + "/#", URI_TYPE_USERS_ID);
        URI_MATCHER.addURI(AUTHORITY, ApplicantColumns.TABLE_NAME, URI_TYPE_APPLICANT);
        URI_MATCHER.addURI(AUTHORITY, ApplicantColumns.TABLE_NAME + "/#", URI_TYPE_APPLICANT_ID);
        URI_MATCHER.addURI(AUTHORITY, VideosColumns.TABLE_NAME, URI_TYPE_VIDEOS);
        URI_MATCHER.addURI(AUTHORITY, VideosColumns.TABLE_NAME + "/#", URI_TYPE_VIDEOS_ID);
        URI_MATCHER.addURI(AUTHORITY, MessagesColumns.TABLE_NAME, URI_TYPE_MESSAGES);
        URI_MATCHER.addURI(AUTHORITY, MessagesColumns.TABLE_NAME + "/#", URI_TYPE_MESSAGES_ID);
    }

    protected EmSQLiteOpenHelper mEmSQLiteOpenHelper;

    @Override
    public boolean onCreate() {
        if (DEBUG) {
            // Enable logging of SQL statements as they are executed.
            try {
                Class<?> sqliteDebugClass = Class.forName("android.database.sqlite.SQLiteDebug");
                Field field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_STATEMENTS");
                field.setAccessible(true);
                field.set(null, true);

                // Uncomment the following block if you also want logging of execution time (more verbose)
                // field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_TIME");
                // field.setAccessible(true);
                // field.set(null, true);
            } catch (Throwable t) {
                if (DEBUG) Log.w(TAG, "Could not enable SQLiteDebug logging", t);
            }
        }

        mEmSQLiteOpenHelper = EmSQLiteOpenHelper.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_DIALOGS:
                return TYPE_CURSOR_DIR + DialogsColumns.TABLE_NAME;
            case URI_TYPE_DIALOGS_ID:
                return TYPE_CURSOR_ITEM + DialogsColumns.TABLE_NAME;

            case URI_TYPE_SCHOOL:
                return TYPE_CURSOR_DIR + SchoolColumns.TABLE_NAME;
            case URI_TYPE_SCHOOL_ID:
                return TYPE_CURSOR_ITEM + SchoolColumns.TABLE_NAME;

            case URI_TYPE_FRIENDS:
                return TYPE_CURSOR_DIR + FriendsColumns.TABLE_NAME;
            case URI_TYPE_FRIENDS_ID:
                return TYPE_CURSOR_ITEM + FriendsColumns.TABLE_NAME;

            case URI_TYPE_UNIVERSITY:
                return TYPE_CURSOR_DIR + UniversityColumns.TABLE_NAME;
            case URI_TYPE_UNIVERSITY_ID:
                return TYPE_CURSOR_ITEM + UniversityColumns.TABLE_NAME;

            case URI_TYPE_COUNTRY:
                return TYPE_CURSOR_DIR + CountryColumns.TABLE_NAME;
            case URI_TYPE_COUNTRY_ID:
                return TYPE_CURSOR_ITEM + CountryColumns.TABLE_NAME;

            case URI_TYPE_CITY:
                return TYPE_CURSOR_DIR + CityColumns.TABLE_NAME;
            case URI_TYPE_CITY_ID:
                return TYPE_CURSOR_ITEM + CityColumns.TABLE_NAME;

            case URI_TYPE_AUDIOS:
                return TYPE_CURSOR_DIR + AudiosColumns.TABLE_NAME;
            case URI_TYPE_AUDIOS_ID:
                return TYPE_CURSOR_ITEM + AudiosColumns.TABLE_NAME;

            case URI_TYPE_FOLLOWERS:
                return TYPE_CURSOR_DIR + FollowersColumns.TABLE_NAME;
            case URI_TYPE_FOLLOWERS_ID:
                return TYPE_CURSOR_ITEM + FollowersColumns.TABLE_NAME;

            case URI_TYPE_USERS:
                return TYPE_CURSOR_DIR + UsersColumns.TABLE_NAME;
            case URI_TYPE_USERS_ID:
                return TYPE_CURSOR_ITEM + UsersColumns.TABLE_NAME;

            case URI_TYPE_APPLICANT:
                return TYPE_CURSOR_DIR + ApplicantColumns.TABLE_NAME;
            case URI_TYPE_APPLICANT_ID:
                return TYPE_CURSOR_ITEM + ApplicantColumns.TABLE_NAME;

            case URI_TYPE_VIDEOS:
                return TYPE_CURSOR_DIR + VideosColumns.TABLE_NAME;
            case URI_TYPE_VIDEOS_ID:
                return TYPE_CURSOR_ITEM + VideosColumns.TABLE_NAME;

            case URI_TYPE_MESSAGES:
                return TYPE_CURSOR_DIR + MessagesColumns.TABLE_NAME;
            case URI_TYPE_MESSAGES_ID:
                return TYPE_CURSOR_ITEM + MessagesColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        String table = uri.getLastPathSegment();
        long rowId = mEmSQLiteOpenHelper.getWritableDatabase().insertOrThrow(table, null, values);
        if (rowId == -1) return null;
        String notify;
        if (rowId != -1 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        String table = uri.getLastPathSegment();
        SQLiteDatabase db = mEmSQLiteOpenHelper.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (ContentValues v : values) {
                long id = db.insert(table, null, v);
                db.yieldIfContendedSafely();
                if (id != -1) {
                    res++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mEmSQLiteOpenHelper.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mEmSQLiteOpenHelper.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + groupBy);
        QueryParams queryParams = getQueryParams(uri, selection, projection);
        ensureIdIsFullyQualified(projection, queryParams.table);
        Cursor res = mEmSQLiteOpenHelper.getReadableDatabase().query(queryParams.tablesWithJoins, projection, queryParams.selection, selectionArgs, groupBy,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    private void ensureIdIsFullyQualified(String[] projection, String tableName) {
        if (projection != null) {
            for (int i = 0; i < projection.length; ++i) {
                if (projection[i].equals(BaseColumns._ID)) {
                    projection[i] = tableName + "." + BaseColumns._ID + " AS " + BaseColumns._ID;
                }
            }
        }
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        HashSet<Uri> urisToNotify = new HashSet<Uri>(operations.size());
        for (ContentProviderOperation operation : operations) {
            urisToNotify.add(operation.getUri());
        }
        SQLiteDatabase db = mEmSQLiteOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            int i = 0;
            for (ContentProviderOperation operation : operations) {
                results[i] = operation.apply(this, results, i);
                if (operation.isYieldAllowed()) {
                    db.yieldIfContendedSafely();
                }
                i++;
            }
            db.setTransactionSuccessful();
            for (Uri uri : urisToNotify) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return results;
        } finally {
            db.endTransaction();
        }
    }

    private static class QueryParams {
        public String table;
        public String tablesWithJoins;
        public String selection;
        public String orderBy;
    }

    private QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_DIALOGS:
            case URI_TYPE_DIALOGS_ID:
                res.table = DialogsColumns.TABLE_NAME;
                res.tablesWithJoins = DialogsColumns.TABLE_NAME;
                res.orderBy = DialogsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_SCHOOL:
            case URI_TYPE_SCHOOL_ID:
                res.table = SchoolColumns.TABLE_NAME;
                res.tablesWithJoins = SchoolColumns.TABLE_NAME;
                res.orderBy = SchoolColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_FRIENDS:
            case URI_TYPE_FRIENDS_ID:
                res.table = FriendsColumns.TABLE_NAME;
                res.tablesWithJoins = FriendsColumns.TABLE_NAME;
                res.orderBy = FriendsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_UNIVERSITY:
            case URI_TYPE_UNIVERSITY_ID:
                res.table = UniversityColumns.TABLE_NAME;
                res.tablesWithJoins = UniversityColumns.TABLE_NAME;
                res.orderBy = UniversityColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COUNTRY:
            case URI_TYPE_COUNTRY_ID:
                res.table = CountryColumns.TABLE_NAME;
                res.tablesWithJoins = CountryColumns.TABLE_NAME;
                res.orderBy = CountryColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_CITY:
            case URI_TYPE_CITY_ID:
                res.table = CityColumns.TABLE_NAME;
                res.tablesWithJoins = CityColumns.TABLE_NAME;
                res.orderBy = CityColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_AUDIOS:
            case URI_TYPE_AUDIOS_ID:
                res.table = AudiosColumns.TABLE_NAME;
                res.tablesWithJoins = AudiosColumns.TABLE_NAME;
                res.orderBy = AudiosColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_FOLLOWERS:
            case URI_TYPE_FOLLOWERS_ID:
                res.table = FollowersColumns.TABLE_NAME;
                res.tablesWithJoins = FollowersColumns.TABLE_NAME;
                res.orderBy = FollowersColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_USERS:
            case URI_TYPE_USERS_ID:
                res.table = UsersColumns.TABLE_NAME;
                res.tablesWithJoins = UsersColumns.TABLE_NAME;
                res.orderBy = UsersColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_APPLICANT:
            case URI_TYPE_APPLICANT_ID:
                res.table = ApplicantColumns.TABLE_NAME;
                res.tablesWithJoins = ApplicantColumns.TABLE_NAME;
                res.orderBy = ApplicantColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_VIDEOS:
            case URI_TYPE_VIDEOS_ID:
                res.table = VideosColumns.TABLE_NAME;
                res.tablesWithJoins = VideosColumns.TABLE_NAME;
                res.orderBy = VideosColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_MESSAGES:
            case URI_TYPE_MESSAGES_ID:
                res.table = MessagesColumns.TABLE_NAME;
                res.tablesWithJoins = MessagesColumns.TABLE_NAME;
                res.orderBy = MessagesColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_DIALOGS_ID:
            case URI_TYPE_SCHOOL_ID:
            case URI_TYPE_FRIENDS_ID:
            case URI_TYPE_UNIVERSITY_ID:
            case URI_TYPE_COUNTRY_ID:
            case URI_TYPE_CITY_ID:
            case URI_TYPE_AUDIOS_ID:
            case URI_TYPE_FOLLOWERS_ID:
            case URI_TYPE_USERS_ID:
            case URI_TYPE_APPLICANT_ID:
            case URI_TYPE_VIDEOS_ID:
            case URI_TYPE_MESSAGES_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + BaseColumns._ID + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + BaseColumns._ID + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }

    public static Uri notify(Uri uri, boolean notify) {
        return uri.buildUpon().appendQueryParameter(QUERY_NOTIFY, String.valueOf(notify)).build();
    }

    public static Uri groupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }
}
