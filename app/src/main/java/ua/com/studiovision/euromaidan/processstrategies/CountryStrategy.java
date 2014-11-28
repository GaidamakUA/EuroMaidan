package ua.com.studiovision.euromaidan.processstrategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.jsonprotocol.AbstractGetProtocol;
import ua.com.studiovision.euromaidan.provider.country.CountryContentValues;

public class CountryStrategy extends AbstractProcessResponseStrategy {

    public CountryStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String countryNamePart = bundle.getString(FirstRunActivity.COUNTRY_NAME);
        request = AbstractGetProtocol.getCountries(countryNamePart);
    }

    @Override
    public void processResponse(AbstractGetProtocol.Response response) {
        CountryContentValues contentValues;
        if(response.array == null)
            return;
        for (AbstractGetProtocol.Response.AbstractItem item : response.array) {
            contentValues = new CountryContentValues();
            contentValues.putCountryId(item.id);
            contentValues.putCountryName(item.name);
            contentValues.insert(resolver);
        }
    }
}