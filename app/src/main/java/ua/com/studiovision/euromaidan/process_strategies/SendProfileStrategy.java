package ua.com.studiovision.euromaidan.process_strategies;

import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.education_places.EducationPlace;
import ua.com.studiovision.euromaidan.json_protocol.education_places.SendEducationPlaceProtocol;
import ua.com.studiovision.euromaidan.json_protocol.settings.SetSettingProtocol;
import ua.com.studiovision.euromaidan.json_protocol.settings.SettingsParamsBuilder;

public class SendProfileStrategy extends AbstractProcessResponseStrategy<SetSettingProtocol.SetSettingsResponse> {

    public SendProfileStrategy(Message msg) {
        Bundle bundle = msg.getData();

        SettingsParamsBuilder builder = new SettingsParamsBuilder();
//        builder.setName(bundle.getString())


        String universityNamePart = bundle.getString(FirstRunActivity.UNIVERSITY_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        EducationPlace educationPlace =
                new EducationPlace(bundle.getString(FirstRunActivity.COUNTRY_NAME),
                        bundle.getString(FirstRunActivity.CITY_NAME),
                        bundle.getString(FirstRunActivity.SCHOOL_NAME));
        request = SendEducationPlaceProtocol.
                getSendUniversityRequest(bundle.getString(FirstRunActivity.TOKEN),
                        new EducationPlace[]{educationPlace});
        responseClass = SetSettingProtocol.SetSettingsResponse.class;
    }

    @Override
    public void processResponse(SetSettingProtocol.SetSettingsResponse response) {
        if (!"success".equals(response.status)) {
            throw new RuntimeException("Not success");
        }
    }
}
