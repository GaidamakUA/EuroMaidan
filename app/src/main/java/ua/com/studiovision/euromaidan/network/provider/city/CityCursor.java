package ua.com.studiovision.euromaidan.network.provider.city;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code city} table.
 */
public class CityCursor extends AbstractCursor {
    public CityCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code city_id} value.
     */
    public long getCityId() {
        return getLongOrNull(CityColumns.CITY_ID);
    }

    /**
     * City name
     * Cannot be {@code null}.
     */
    public String getCityName() {
        Integer index = getCachedColumnIndexOrThrow(CityColumns.CITY_NAME);
        return getString(index);
    }

    /**
     * City name lowercase
     * Cannot be {@code null}.
     */
    public String getCityNameLowercase() {
        Integer index = getCachedColumnIndexOrThrow(CityColumns.CITY_NAME_LOWERCASE);
        return getString(index);
    }
}
