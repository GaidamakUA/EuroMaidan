package ua.com.studiovision.euromaidan.network.provider.school;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code school} table.
 */
public class SchoolSelection extends AbstractSelection<SchoolSelection> {
    @Override
    public Uri uri() {
        return SchoolColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *                        order, which may be unordered.
     * @return A {@code SchoolCursor} object, which is positioned before the first entry, or null.
     */
    public SchoolCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new SchoolCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public SchoolCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public SchoolCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public SchoolSelection id(long... value) {
        addEquals("school." + SchoolColumns._ID, toObjectArray(value));
        return this;
    }


    public SchoolSelection schoolId(long... value) {
        addEquals(SchoolColumns.SCHOOL_ID, toObjectArray(value));
        return this;
    }

    public SchoolSelection schoolIdNot(long... value) {
        addNotEquals(SchoolColumns.SCHOOL_ID, toObjectArray(value));
        return this;
    }

    public SchoolSelection schoolIdGt(long value) {
        addGreaterThan(SchoolColumns.SCHOOL_ID, value);
        return this;
    }

    public SchoolSelection schoolIdGtEq(long value) {
        addGreaterThanOrEquals(SchoolColumns.SCHOOL_ID, value);
        return this;
    }

    public SchoolSelection schoolIdLt(long value) {
        addLessThan(SchoolColumns.SCHOOL_ID, value);
        return this;
    }

    public SchoolSelection schoolIdLtEq(long value) {
        addLessThanOrEquals(SchoolColumns.SCHOOL_ID, value);
        return this;
    }

    public SchoolSelection schoolName(String... value) {
        addEquals(SchoolColumns.SCHOOL_NAME, value);
        return this;
    }

    public SchoolSelection schoolNameNot(String... value) {
        addNotEquals(SchoolColumns.SCHOOL_NAME, value);
        return this;
    }

    public SchoolSelection schoolNameLike(String... value) {
        addLike(SchoolColumns.SCHOOL_NAME, value);
        return this;
    }
}
