package ua.com.studiovision.euromaidan.provider.country;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code country} table.
 */
public class CountryCursor extends AbstractCursor {
    public CountryCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Country id from server
     */
    public long getCountryId() {
        return getLongOrNull(CountryColumns.COUNTRY_ID);
    }

    /**
     * Country name
     * Cannot be {@code null}.
     */
    public String getCountryName() {
        Integer index = getCachedColumnIndexOrThrow(CountryColumns.COUNTRY_NAME);
        return getString(index);
    }
}
