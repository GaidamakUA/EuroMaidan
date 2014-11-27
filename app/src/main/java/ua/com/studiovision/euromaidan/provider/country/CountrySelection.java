package ua.com.studiovision.euromaidan.provider.country;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.provider.base.AbstractSelection;

/**
 * Selection for the {@code country} table.
 */
public class CountrySelection extends AbstractSelection<CountrySelection> {
    @Override
    public Uri uri() {
        return CountryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CountryCursor} object, which is positioned before the first entry, or null.
     */
    public CountryCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CountryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public CountryCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public CountryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CountrySelection id(long... value) {
        addEquals("country." + CountryColumns._ID, toObjectArray(value));
        return this;
    }


    public CountrySelection id(Integer... value) {
        addEquals(CountryColumns.ID, value);
        return this;
    }

    public CountrySelection idNot(Integer... value) {
        addNotEquals(CountryColumns.ID, value);
        return this;
    }

    public CountrySelection idGt(int value) {
        addGreaterThan(CountryColumns.ID, value);
        return this;
    }

    public CountrySelection idGtEq(int value) {
        addGreaterThanOrEquals(CountryColumns.ID, value);
        return this;
    }

    public CountrySelection idLt(int value) {
        addLessThan(CountryColumns.ID, value);
        return this;
    }

    public CountrySelection idLtEq(int value) {
        addLessThanOrEquals(CountryColumns.ID, value);
        return this;
    }

    public CountrySelection countryName(String... value) {
        addEquals(CountryColumns.COUNTRY_NAME, value);
        return this;
    }

    public CountrySelection countryNameNot(String... value) {
        addNotEquals(CountryColumns.COUNTRY_NAME, value);
        return this;
    }

    public CountrySelection countryNameLike(String... value) {
        addLike(CountryColumns.COUNTRY_NAME, value);
        return this;
    }
}
