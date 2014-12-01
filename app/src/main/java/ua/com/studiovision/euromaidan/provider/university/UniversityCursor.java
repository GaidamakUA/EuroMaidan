package ua.com.studiovision.euromaidan.provider.university;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code university} table.
 */
public class UniversityCursor extends AbstractCursor {
    public UniversityCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * University id from server
     */
    public long getUniversityId() {
        return getLongOrNull(UniversityColumns.UNIVERSITY_ID);
    }

    /**
     * University name
     * Cannot be {@code null}.
     */
    public String getUniversityName() {
        Integer index = getCachedColumnIndexOrThrow(UniversityColumns.UNIVERSITY_NAME);
        return getString(index);
    }

    /**
     * University name lowercase
     * Cannot be {@code null}.
     */
    public String getUniversityNameLowercase() {
        Integer index = getCachedColumnIndexOrThrow(UniversityColumns.UNIVERSITY_NAME_LOWERCASE);
        return getString(index);
    }
}
