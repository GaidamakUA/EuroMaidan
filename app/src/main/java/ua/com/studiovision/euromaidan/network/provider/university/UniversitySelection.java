package ua.com.studiovision.euromaidan.network.provider.university;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code university} table.
 */
public class UniversitySelection extends AbstractSelection<UniversitySelection> {
    @Override
    public Uri uri() {
        return UniversityColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code UniversityCursor} object, which is positioned before the first entry, or null.
     */
    public UniversityCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new UniversityCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public UniversityCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public UniversityCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public UniversitySelection id(long... value) {
        addEquals("university." + UniversityColumns._ID, toObjectArray(value));
        return this;
    }


    public UniversitySelection universityId(long... value) {
        addEquals(UniversityColumns.UNIVERSITY_ID, toObjectArray(value));
        return this;
    }

    public UniversitySelection universityIdNot(long... value) {
        addNotEquals(UniversityColumns.UNIVERSITY_ID, toObjectArray(value));
        return this;
    }

    public UniversitySelection universityIdGt(long value) {
        addGreaterThan(UniversityColumns.UNIVERSITY_ID, value);
        return this;
    }

    public UniversitySelection universityIdGtEq(long value) {
        addGreaterThanOrEquals(UniversityColumns.UNIVERSITY_ID, value);
        return this;
    }

    public UniversitySelection universityIdLt(long value) {
        addLessThan(UniversityColumns.UNIVERSITY_ID, value);
        return this;
    }

    public UniversitySelection universityIdLtEq(long value) {
        addLessThanOrEquals(UniversityColumns.UNIVERSITY_ID, value);
        return this;
    }

    public UniversitySelection universityName(String... value) {
        addEquals(UniversityColumns.UNIVERSITY_NAME, value);
        return this;
    }

    public UniversitySelection universityNameNot(String... value) {
        addNotEquals(UniversityColumns.UNIVERSITY_NAME, value);
        return this;
    }

    public UniversitySelection universityNameLike(String... value) {
        addLike(UniversityColumns.UNIVERSITY_NAME, value);
        return this;
    }

    public UniversitySelection universityNameLowercase(String... value) {
        addEquals(UniversityColumns.UNIVERSITY_NAME_LOWERCASE, value);
        return this;
    }

    public UniversitySelection universityNameLowercaseNot(String... value) {
        addNotEquals(UniversityColumns.UNIVERSITY_NAME_LOWERCASE, value);
        return this;
    }

    public UniversitySelection universityNameLowercaseLike(String... value) {
        addLike(UniversityColumns.UNIVERSITY_NAME_LOWERCASE, value);
        return this;
    }
}
