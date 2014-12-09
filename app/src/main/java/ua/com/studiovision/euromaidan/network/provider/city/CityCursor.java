package ua.com.studiovision.euromaidan.network.provider.city;

import java.util.Date;

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
}
