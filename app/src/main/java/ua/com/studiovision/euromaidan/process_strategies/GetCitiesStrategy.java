package ua.com.studiovision.euromaidan.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.city.CityContentValues;

public class GetCitiesStrategy extends AbstractProcessResponseStrategy
        <AbstractGetArrayProtocol.AbstractArrayRequest, AbstractGetArrayProtocol.AbstractArrayResponse> {

    public GetCitiesStrategy(Context context, Message msg) {
        super(context, AbstractGetArrayProtocol.AbstractArrayResponse.class);
        Bundle bundle = msg.getData();
        String cityNamePart = bundle.getString(FirstRunActivity.CITY_NAME);
        long countryId = bundle.getLong(FirstRunActivity.COUNTRY_ID);
        request = AbstractGetArrayProtocol.getCities(cityNamePart, countryId);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse response) {
        if (response.array == null)
            return;
        CityContentValues contentValues;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response.array) {
            contentValues = new CityContentValues();
            contentValues.putCityId(item.id);
            contentValues.putCityName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}