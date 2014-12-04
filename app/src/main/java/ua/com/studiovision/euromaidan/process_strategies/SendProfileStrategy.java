package ua.com.studiovision.euromaidan.process_strategies;

import android.os.Bundle;
import android.os.Message;
import ua.com.studiovision.euromaidan.json_protocol.settings.SetSettingProtocol;

public class SendProfileStrategy extends AbstractProcessResponseStrategy<SetSettingProtocol.SetSettingsResponse> {

    public SendProfileStrategy(Message msg) {
        Bundle bundle = msg.getData();

        /*String universityNamePart = bundle.getString(FirstRunActivity.UNIVERSITY_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        EducationPlace educationPlace =
                new EducationPlace(bundle.getString(FirstRunActivity.COUNTRY_NAME),
                        bundle.getString(FirstRunActivity.CITY_NAME),
                        bundle.getString(FirstRunActivity.SCHOOL_NAME));
        request = SendEducationPlaceProtocol.
                getSendUniversityRequest(bundle.getString(FirstRunActivity.TOKEN),
                        new EducationPlace[]{educationPlace});*/
    }

    @Override
    public void processResponse(SetSettingProtocol.SetSettingsResponse response) {
        if (!"success".equals(response.status)) {
            throw new RuntimeException("Not success");
        }
    }
}
