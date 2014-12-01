package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.FirstRunActivity;
import ua.com.studiovision.euromaidan.json_protocol.SendEducationPlaceProtocol;

public class SendUnivercityStrategy extends AbstractProcessResponseStrategy<SendEducationPlaceProtocol.SendEducationPlaceResponse> {

    public SendUnivercityStrategy(ContentResolver resolver, Message msg) {
        this.resolver = resolver;
        Bundle bundle = msg.getData();
        String universityNamePart = bundle.getString(FirstRunActivity.UNIVERSITY_NAME);
        long cityId = bundle.getLong(FirstRunActivity.CITY_ID);
        SendEducationPlaceProtocol.EducationPlace educationPlace =
                new SendEducationPlaceProtocol.EducationPlace(bundle.getString(FirstRunActivity.COUNTRY_NAME),
                        bundle.getString(FirstRunActivity.CITY_NAME),
                        bundle.getString(FirstRunActivity.SCHOOL_NAME));
        request = SendEducationPlaceProtocol.
                getSendUniversityRequest(bundle.getString(FirstRunActivity.TOKEN),
                        new SendEducationPlaceProtocol.EducationPlace[]{educationPlace});
        responseClass = SendEducationPlaceProtocol.SendEducationPlaceResponse.class;
    }

    @Override
    public void processResponse(SendEducationPlaceProtocol.SendEducationPlaceResponse response) {
        if(!"success".equals(response.status)) {
            throw new RuntimeException("Not success");
        }
    }
}