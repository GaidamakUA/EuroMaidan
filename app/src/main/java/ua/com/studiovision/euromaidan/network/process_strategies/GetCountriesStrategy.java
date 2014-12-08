package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.provider.country.CountryContentValues;

public class GetCountriesStrategy extends AbstractGetArrayStrategy {

    private static final String TAG = "GetCountriesStrategy";

    public GetCountriesStrategy(Context context, Message msg) {
        super(context, msg);
        request = AbstractGetArrayProtocol.getCountries(namePart);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem[] response) {
        CountryContentValues contentValues;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response) {
            contentValues = new CountryContentValues();
            contentValues.putCountryId(item.id);
            contentValues.putCountryName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}