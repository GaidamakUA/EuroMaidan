package ua.com.studiovision.euromaidan.network.provider.school;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code school} table.
 */
public class SchoolContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return SchoolColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, SchoolSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * School id from server
     */
    public SchoolContentValues putSchoolId(long value) {
        mContentValues.put(SchoolColumns.SCHOOL_ID, value);
        return this;
    }



    /**
     * School name
     */
    public SchoolContentValues putSchoolName(String value) {
        if (value == null) throw new IllegalArgumentException("value for schoolName must not be null");
        mContentValues.put(SchoolColumns.SCHOOL_NAME, value);
        putSchoolNameLowercase(value.toLowerCase());
        return this;
    }



    /**
     * School name lowercase
     */
    public SchoolContentValues putSchoolNameLowercase(String value) {
        if (value == null) throw new IllegalArgumentException("value for schoolNameLowercase must not be null");
        mContentValues.put(SchoolColumns.SCHOOL_NAME_LOWERCASE, value);
        return this;
    }


}
