package ua.com.studiovision.euromaidan.network.provider.university;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code university} table.
 */
public class UniversityContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return UniversityColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, UniversitySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * University id from server
     */
    public UniversityContentValues putUniversityId(long value) {
        mContentValues.put(UniversityColumns.UNIVERSITY_ID, value);
        return this;
    }



    /**
     * University name
     */
    public UniversityContentValues putUniversityName(String value) {
        if (value == null) throw new IllegalArgumentException("value for universityName must not be null");
        mContentValues.put(UniversityColumns.UNIVERSITY_NAME, value);
        return this;
    }



    /**
     * University name lowercase
     */
    public UniversityContentValues putUniversityNameLowercase(String value) {
        if (value == null) throw new IllegalArgumentException("value for universityNameLowercase must not be null");
        mContentValues.put(UniversityColumns.UNIVERSITY_NAME_LOWERCASE, value);
        return this;
    }


}
