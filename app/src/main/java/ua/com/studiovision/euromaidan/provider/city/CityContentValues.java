package ua.com.studiovision.euromaidan.provider.city;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code city} table.
 */
public class CityContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CityColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, CitySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CityContentValues putCityId(long value) {
        mContentValues.put(CityColumns.CITY_ID, value);
        return this;
    }



    /**
     * City name
     */
    public CityContentValues putCityName(String value) {
        if (value == null) throw new IllegalArgumentException("value for cityName must not be null");
        mContentValues.put(CityColumns.CITY_NAME, value);
        return this;
    }


}
