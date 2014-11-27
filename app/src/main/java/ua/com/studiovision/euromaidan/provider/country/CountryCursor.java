package ua.com.studiovision.euromaidan.provider.country;

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
     * Can be {@code null}.
     */
    public Long getCountryId() {
        return getLongOrNull(CountryColumns.COUNTRY_ID);
    }

    /**
     * Country name
     * Can be {@code null}.
     */
    public String getCountryName() {
        Integer index = getCachedColumnIndexOrThrow(CountryColumns.COUNTRY_NAME);
        return getString(index);
    }
}
