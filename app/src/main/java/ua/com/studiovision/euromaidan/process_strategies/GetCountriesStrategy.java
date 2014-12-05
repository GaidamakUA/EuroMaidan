package ua.com.studiovision.euromaidan.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.country.CountryContentValues;

public class GetCountriesStrategy extends AbstractProcessResponseStrategy
        <AbstractGetArrayProtocol.AbstractArrayRequest, AbstractGetArrayProtocol.AbstractArrayResponse> {

    public GetCountriesStrategy(Context context, Message msg) {
        super(context, AbstractGetArrayProtocol.AbstractArrayResponse.class);
        Bundle bundle = msg.getData();
        String countryNamePart = bundle.getString(FirstRunActivity.COUNTRY_NAME);
        request = AbstractGetArrayProtocol.getCountries(countryNamePart);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse response) {
        CountryContentValues contentValues;
        if (response.array == null)
            return;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response.array) {
            contentValues = new CountryContentValues();
            contentValues.putCountryId(item.id);
            contentValues.putCountryName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}