package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.university.UniversityContentValues;

public class GetUniversitiesStrategy extends AbstractProcessResponseStrategy<AbstractGetArrayProtocol.Response> {

    public GetUniversitiesStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String universityNamePart = bundle.getString(FirstRunActivity.UNIVERSITY_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        request = AbstractGetArrayProtocol.getUniversities(universityNamePart, cityId);
        responseClass = AbstractGetArrayProtocol.Response.class;
    }

    @Override
    public void processResponse(AbstractGetArrayProtocol.Response response) {
        if (response.array == null)
            return;
        UniversityContentValues contentValues;
        for (AbstractGetArrayProtocol.Response.AbstractItem item : response.array) {
            contentValues = new UniversityContentValues();
            contentValues.putUniversityId(item.id);
            contentValues.putUniversityName(item.name);
            contentValues.insert(resolver);
        }
    }
}
