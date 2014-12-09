package ua.com.studiovision.euromaidan.network.provider.country;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code country} table.
 */
public class CountryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CountryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, CountrySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Country id from server
     */
    public CountryContentValues putCountryId(long value) {
        mContentValues.put(CountryColumns.COUNTRY_ID, value);
        return this;
    }



    /**
     * Country name
     */
    public CountryContentValues putCountryName(String value) {
        if (value == null) throw new IllegalArgumentException("value for countryName must not be null");
        mContentValues.put(CountryColumns.COUNTRY_NAME, value);
        return this;
    }


}
