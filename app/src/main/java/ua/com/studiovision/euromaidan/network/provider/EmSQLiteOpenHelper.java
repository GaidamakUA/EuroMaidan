package ua.com.studiovision.euromaidan.network.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
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

public class EmSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = EmSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "embase.db";
    private static final int DATABASE_VERSION = 1;
    private static EmSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final EmSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_DIALOGS = "CREATE TABLE IF NOT EXISTS "
            + DialogsColumns.TABLE_NAME + " ( "
            + DialogsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DialogsColumns.MESSAGE + " TEXT NOT NULL, "
            + DialogsColumns.MINE + " INTEGER NOT NULL, "
            + DialogsColumns.NEW_COUNT + " INTEGER NOT NULL, "
            + DialogsColumns.TIME + " INTEGER NOT NULL, "
            + DialogsColumns.USER_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (user_id) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_SCHOOL = "CREATE TABLE IF NOT EXISTS "
            + SchoolColumns.TABLE_NAME + " ( "
            + SchoolColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SchoolColumns.SCHOOL_ID + " INTEGER NOT NULL, "
            + SchoolColumns.SCHOOL_NAME + " TEXT NOT NULL, "
            + SchoolColumns.SCHOOL_NAME_LOWERCASE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (school_id, school_name) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_FRIENDS = "CREATE TABLE IF NOT EXISTS "
            + FriendsColumns.TABLE_NAME + " ( "
            + FriendsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendsColumns.FRIEND_ID + " INTEGER NOT NULL, "
            + FriendsColumns.FRIEND_NAME + " TEXT NOT NULL, "
            + FriendsColumns.FRIEND_SURNAME + " TEXT NOT NULL, "
            + FriendsColumns.FRIEND_AVATAR + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (friend_id) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_UNIVERSITY = "CREATE TABLE IF NOT EXISTS "
            + UniversityColumns.TABLE_NAME + " ( "
            + UniversityColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UniversityColumns.UNIVERSITY_ID + " INTEGER NOT NULL, "
            + UniversityColumns.UNIVERSITY_NAME + " TEXT NOT NULL, "
            + UniversityColumns.UNIVERSITY_NAME_LOWERCASE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (university_id, university_name) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_COUNTRY = "CREATE TABLE IF NOT EXISTS "
            + CountryColumns.TABLE_NAME + " ( "
            + CountryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CountryColumns.COUNTRY_ID + " INTEGER NOT NULL, "
            + CountryColumns.COUNTRY_NAME + " TEXT NOT NULL, "
            + CountryColumns.COUNTRY_NAME_LOWERCASE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (country_id, country_name) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_CITY = "CREATE TABLE IF NOT EXISTS "
            + CityColumns.TABLE_NAME + " ( "
            + CityColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CityColumns.CITY_ID + " INTEGER NOT NULL, "
            + CityColumns.CITY_NAME + " TEXT NOT NULL, "
            + CityColumns.CITY_NAME_LOWERCASE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (city_id, city_name) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_AUDIOS = "CREATE TABLE IF NOT EXISTS "
            + AudiosColumns.TABLE_NAME + " ( "
            + AudiosColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AudiosColumns.NAME + " TEXT NOT NULL, "
            + AudiosColumns.NAME_LOWERCASE + " TEXT NOT NULL, "
            + AudiosColumns.AUTHOR + " TEXT NOT NULL, "
            + AudiosColumns.AUTHOR_LOWERCASE + " TEXT NOT NULL, "
            + AudiosColumns.DURATION + " INTEGER NOT NULL, "
            + AudiosColumns.AUDIO_URL + " TEXT NOT NULL "
            + ", CONSTRAINT unique_url UNIQUE (audio_url) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_FOLLOWERS = "CREATE TABLE IF NOT EXISTS "
            + FollowersColumns.TABLE_NAME + " ( "
            + FollowersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FollowersColumns.FOLLOWER_ID + " INTEGER NOT NULL, "
            + FollowersColumns.FOLLOWER_NAME + " TEXT NOT NULL, "
            + FollowersColumns.FOLLOWER_SURNAME + " TEXT NOT NULL, "
            + FollowersColumns.FOLLOWER_AVATAR + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (follower_id) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS "
            + UsersColumns.TABLE_NAME + " ( "
            + UsersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UsersColumns.USER_ID + " INTEGER NOT NULL, "
            + UsersColumns.USER_NAME + " TEXT NOT NULL, "
            + UsersColumns.USER_SURNAME + " TEXT NOT NULL, "
            + UsersColumns.USER_NAME_LOWERCASE + " TEXT NOT NULL, "
            + UsersColumns.USER_SURNAME_LOWERCASE + " TEXT NOT NULL, "
            + UsersColumns.AVATAR + " TEXT NOT NULL "
            + ", CONSTRAINT unique_id UNIQUE (user_id) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_APPLICANT = "CREATE TABLE IF NOT EXISTS "
            + ApplicantColumns.TABLE_NAME + " ( "
            + ApplicantColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ApplicantColumns.APPLICANT_ID + " INTEGER NOT NULL, "
            + ApplicantColumns.APPLICANT_NAME + " TEXT NOT NULL, "
            + ApplicantColumns.APPLICANT_SURNAME + " TEXT NOT NULL, "
            + ApplicantColumns.APPLICANT_AVATAR + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (applicant_id) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_VIDEOS = "CREATE TABLE IF NOT EXISTS "
            + VideosColumns.TABLE_NAME + " ( "
            + VideosColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VideosColumns.NAME + " TEXT NOT NULL, "
            + VideosColumns.DURATION + " INTEGER NOT NULL, "
            + VideosColumns.VIDEO_URL + " TEXT NOT NULL "
            + " );";

    private static final String SQL_CREATE_TABLE_MESSAGES = "CREATE TABLE IF NOT EXISTS "
            + MessagesColumns.TABLE_NAME + " ( "
            + MessagesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MessagesColumns.USER_ID + " INTEGER NOT NULL, "
            + MessagesColumns.TIMESTAMP + " INTEGER NOT NULL, "
            + MessagesColumns.MESSAGE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (user_id, timestamp) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static EmSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static EmSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static EmSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new EmSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private EmSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new EmSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static EmSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new EmSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private EmSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new EmSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_DIALOGS);
        db.execSQL(SQL_CREATE_TABLE_SCHOOL);
        db.execSQL(SQL_CREATE_TABLE_FRIENDS);
        db.execSQL(SQL_CREATE_TABLE_UNIVERSITY);
        db.execSQL(SQL_CREATE_TABLE_COUNTRY);
        db.execSQL(SQL_CREATE_TABLE_CITY);
        db.execSQL(SQL_CREATE_TABLE_AUDIOS);
        db.execSQL(SQL_CREATE_TABLE_FOLLOWERS);
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_APPLICANT);
        db.execSQL(SQL_CREATE_TABLE_VIDEOS);
        db.execSQL(SQL_CREATE_TABLE_MESSAGES);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
