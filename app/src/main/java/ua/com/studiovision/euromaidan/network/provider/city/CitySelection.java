package ua.com.studiovision.euromaidan.network.provider.city;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code city} table.
 */
public class CitySelection extends AbstractSelection<CitySelection> {
    @Override
    public Uri uri() {
        return CityColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CityCursor} object, which is positioned before the first entry, or null.
     */
    public CityCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CityCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public CityCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public CityCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CitySelection id(long... value) {
        addEquals("city." + CityColumns._ID, toObjectArray(value));
        return this;
    }


    public CitySelection cityId(long... value) {
        addEquals(CityColumns.CITY_ID, toObjectArray(value));
        return this;
    }

    public CitySelection cityIdNot(long... value) {
        addNotEquals(CityColumns.CITY_ID, toObjectArray(value));
        return this;
    }

    public CitySelection cityIdGt(long value) {
        addGreaterThan(CityColumns.CITY_ID, value);
        return this;
    }

    public CitySelection cityIdGtEq(long value) {
        addGreaterThanOrEquals(CityColumns.CITY_ID, value);
        return this;
    }

    public CitySelection cityIdLt(long value) {
        addLessThan(CityColumns.CITY_ID, value);
        return this;
    }

    public CitySelection cityIdLtEq(long value) {
        addLessThanOrEquals(CityColumns.CITY_ID, value);
        return this;
    }

    public CitySelection cityName(String... value) {
        addEquals(CityColumns.CITY_NAME, value);
        return this;
    }

    public CitySelection cityNameNot(String... value) {
        addNotEquals(CityColumns.CITY_NAME, value);
        return this;
    }

    public CitySelection cityNameLike(String... value) {
        addLike(CityColumns.CITY_NAME, value);
        return this;
    }
}
