package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.city.CityContentValues;

public class CityStrategy extends AbstractProcessResponseStrategy<AbstractGetArrayProtocol.Response> {

    public CityStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String cityNamePart = bundle.getString(FirstRunActivity.CITY_NAME);
        long countryId = bundle.getLong(FirstRunActivity.COUNTRY_ID);
        request = AbstractGetArrayProtocol.getCities(cityNamePart, countryId);
        responseClass = AbstractGetArrayProtocol.Response.class;
    }

    @Override
    public void processResponse(AbstractGetArrayProtocol.Response response) {
        // TODO implement insertion to DB
        if(response.array == null)
            return;
        CityContentValues contentValues;
        for (AbstractGetArrayProtocol.Response.AbstractItem item : response.array) {
            contentValues = new CityContentValues();
            contentValues.putCityId(item.id);
            contentValues.putCityName(item.name);
            contentValues.insert(resolver);
        }
    }
}