package ua.com.studiovision.euromaidan.provider.country;

import android.content.ContentResolver;
import android.net.Uri;
import ua.com.studiovision.euromaidan.provider.base.AbstractContentValues;

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
    public CountryContentValues putCountryId(Long value) {
        mContentValues.put(CountryColumns.COUNTRY_ID, value);
        return this;
    }

    public CountryContentValues putCountryIdNull() {
        mContentValues.putNull(CountryColumns.COUNTRY_ID);
        return this;
    }


    /**
     * Country name
     */
    public CountryContentValues putCountryName(String value) {
        mContentValues.put(CountryColumns.COUNTRY_NAME, value);
        return this;
    }

    public CountryContentValues putCountryNameNull() {
        mContentValues.putNull(CountryColumns.COUNTRY_NAME);
        return this;
    }

}
