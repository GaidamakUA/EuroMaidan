package ua.com.studiovision.euromaidan.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.university.UniversityContentValues;

public class GetUniversitiesStrategy extends AbstractProcessResponseStrategy
        <AbstractGetArrayProtocol.AbstractArrayRequest,
                AbstractGetArrayProtocol.AbstractArrayResponse> {

    public GetUniversitiesStrategy(Context context, Message msg) {
        super(context, AbstractGetArrayProtocol.AbstractArrayResponse.class);
        Bundle bundle = msg.getData();
        String universityNamePart = bundle.getString(FirstRunActivity.UNIVERSITY_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        request = AbstractGetArrayProtocol.getUniversities(universityNamePart, cityId);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse response) {
        if (response.array == null)
            return;
        UniversityContentValues contentValues;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response.array) {
            contentValues = new UniversityContentValues();
            contentValues.putUniversityId(item.id);
            contentValues.putUniversityName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}
