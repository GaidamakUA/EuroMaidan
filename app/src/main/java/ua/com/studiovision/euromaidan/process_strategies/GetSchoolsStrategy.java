package ua.com.studiovision.euromaidan.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.provider.school.SchoolContentValues;

public class GetSchoolsStrategy extends AbstractProcessResponseStrategy
        <AbstractGetArrayProtocol.AbstractArrayRequest,
                AbstractGetArrayProtocol.AbstractArrayResponse> {

    public GetSchoolsStrategy(Context context, Message msg) {
        super(context, AbstractGetArrayProtocol.AbstractArrayResponse.class);
        Bundle bundle = msg.getData();
        String schoolNamePart = bundle.getString(FirstRunActivity.SCHOOL_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        request = AbstractGetArrayProtocol.getSchools(schoolNamePart, cityId);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse response) {
        if (response.array == null)
            return;
        SchoolContentValues contentValues;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response.array) {
            contentValues = new SchoolContentValues();
            contentValues.putSchoolId(item.id);
            contentValues.putSchoolName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}
