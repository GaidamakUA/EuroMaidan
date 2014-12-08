package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.provider.city.CityContentValues;

public class GetCitiesStrategy extends AbstractGetArrayStrategy {

    public GetCitiesStrategy(Context context, Message msg) {
        super(context, msg);
        request = AbstractGetArrayProtocol.getCities(namePart, parentItemId);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem[] response) {
        CityContentValues contentValues = new CityContentValues();
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response) {
            contentValues = new CityContentValues();
            contentValues.putCityId(item.id);
            contentValues.putCityName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}