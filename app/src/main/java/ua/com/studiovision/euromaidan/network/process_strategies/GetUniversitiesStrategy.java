package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.provider.university.UniversityContentValues;

public class GetUniversitiesStrategy extends AbstractGetArrayStrategy {

    public GetUniversitiesStrategy(Context context, Message msg) {
        super(context, msg);
        request = AbstractGetArrayProtocol.getUniversities(namePart, parentItemId);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem[] response) {
        UniversityContentValues contentValues;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response) {
            contentValues = new UniversityContentValues();
            contentValues.putUniversityId(item.id);
            contentValues.putUniversityName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}
