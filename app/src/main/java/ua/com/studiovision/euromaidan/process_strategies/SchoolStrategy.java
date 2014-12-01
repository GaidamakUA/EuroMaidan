package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetProtocol;
import ua.com.studiovision.euromaidan.provider.school.SchoolContentValues;

public class SchoolStrategy extends AbstractProcessResponseStrategy {

    public SchoolStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String schoolNamePart = bundle.getString(FirstRunActivity.SCHOOL_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        request = AbstractGetProtocol.getSchools(schoolNamePart, cityId);
    }

    @Override
    public void processResponse(AbstractGetProtocol.Response response) {
        if(response.array == null)
            return;
        SchoolContentValues contentValues;
        for (AbstractGetProtocol.Response.AbstractItem item : response.array) {
            contentValues = new SchoolContentValues();
            contentValues.putSchoolId(item.id);
            contentValues.putSchoolName(item.name);
            contentValues.insert(resolver);
        }
    }
}
