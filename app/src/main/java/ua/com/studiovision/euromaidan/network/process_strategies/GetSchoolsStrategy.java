package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.provider.school.SchoolContentValues;

public class GetSchoolsStrategy extends AbstractGetArrayStrategy {

    public GetSchoolsStrategy(Context context, Message msg) {
        super(context, msg);
        request = AbstractGetArrayProtocol.getSchools(namePart, parentItemId);
    }

    @Override
    public void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem[] response) {
        SchoolContentValues contentValues;
        for (AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem item : response) {
            contentValues = new SchoolContentValues();
            contentValues.putSchoolId(item.id);
            contentValues.putSchoolName(item.name);
            contentValues.insert(context.getContentResolver());
        }
    }
}
