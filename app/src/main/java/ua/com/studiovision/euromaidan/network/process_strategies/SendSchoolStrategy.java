package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.activities.FirstRunActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.education_places.EducationPlace;
import ua.com.studiovision.euromaidan.network.json_protocol.education_places.SendEducationPlaceProtocol;

public class SendSchoolStrategy extends AbstractProcessResponseStrategy
        <SendEducationPlaceProtocol.SendEducationPlaceRequest,
                SendEducationPlaceProtocol.SendEducationPlaceResponse> {

    public SendSchoolStrategy(Context context, Message msg) {
        super(context, SendEducationPlaceProtocol.SendEducationPlaceResponse.class);
        Bundle bundle = msg.getData();
        EducationPlace educationPlace =
                new EducationPlace(bundle.getString(FirstRunActivity.COUNTRY_NAME),
                        bundle.getString(FirstRunActivity.CITY_NAME),
                        bundle.getString(FirstRunActivity.SCHOOL_NAME));
        request = SendEducationPlaceProtocol.
                getSendSchoolRequest(bundle.getString(FirstRunActivity.TOKEN),
                        new EducationPlace[]{educationPlace});
    }
}
