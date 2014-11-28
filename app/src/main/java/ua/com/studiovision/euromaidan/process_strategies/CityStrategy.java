package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetProtocol;
import ua.com.studiovision.euromaidan.provider.city.CityContentValues;

public class CityStrategy extends AbstractProcessResponseStrategy {

    public CityStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String cityNamePart = bundle.getString(FirstRunActivity.CITY_NAME);
        long countryId = bundle.getLong(FirstRunActivity.COUNTRY_ID);
        request = AbstractGetProtocol.getCities(cityNamePart, countryId);
    }

    @Override
    public void processResponse(AbstractGetProtocol.Response response) {
        // TODO implement insertion to DB
        if(response.array == null)
            return;
        CityContentValues contentValues;
        for (AbstractGetProtocol.Response.AbstractItem item : response.array) {
            contentValues = new CityContentValues();
            contentValues.putCityId(item.id);
            contentValues.putCityName(item.name);
            contentValues.insert(resolver);
        }
    }
}