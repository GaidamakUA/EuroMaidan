package ua.com.studiovision.euromaidan.network.provider.school;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code school} table.
 */
public class SchoolCursor extends AbstractCursor {
    public SchoolCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * School id from server
     */
    public long getSchoolId() {
        return getLongOrNull(SchoolColumns.SCHOOL_ID);
    }

    /**
     * School name
     * Cannot be {@code null}.
     */
    public String getSchoolName() {
        Integer index = getCachedColumnIndexOrThrow(SchoolColumns.SCHOOL_NAME);
        return getString(index);
    }
}
