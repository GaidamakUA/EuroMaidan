package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.country.CountryContentValues;

public class CountryStrategy extends AbstractProcessResponseStrategy<AbstractGetArrayProtocol.Response> {

    public CountryStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String countryNamePart = bundle.getString(FirstRunActivity.COUNTRY_NAME);
        request = AbstractGetArrayProtocol.getCountries(countryNamePart);
        responseClass = AbstractGetArrayProtocol.Response.class;
    }

    @Override
    public void processResponse(AbstractGetArrayProtocol.Response response) {
        CountryContentValues contentValues;
        if(response.array == null)
            return;
        for (AbstractGetArrayProtocol.Response.AbstractItem item : response.array) {
            contentValues = new CountryContentValues();
            contentValues.putCountryId(item.id);
            contentValues.putCountryName(item.name);
            contentValues.insert(resolver);
        }
    }
}