package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetProtocol;
import ua.com.studiovision.euromaidan.provider.university.UniversityContentValues;

public class UniversityStrategy extends AbstractProcessResponseStrategy{

    public UniversityStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String universityNamePart = bundle.getString(FirstRunActivity.UNIVERSITY_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        request = AbstractGetProtocol.getUniversities(universityNamePart, cityId);
    }

    @Override
    public void processResponse(AbstractGetProtocol.Response response) {
        if(response.array == null)
            return;
        UniversityContentValues contentValues;
        for (AbstractGetProtocol.Response.AbstractItem item : response.array) {
            contentValues = new UniversityContentValues();
            contentValues.putUniversityId(item.id);
            contentValues.putUniversityName(item.name);
            contentValues.insert(resolver);
        }
    }
}
