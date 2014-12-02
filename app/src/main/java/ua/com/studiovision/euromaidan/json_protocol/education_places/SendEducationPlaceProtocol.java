package ua.com.studiovision.euromaidan.json_protocol.education_places;

import ua.com.studiovision.euromaidan.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.json_protocol.AbstractResponse;

/**
 * Created by gaidamak on 01.12.14.
 */
public class SendEducationPlaceProtocol {
    private SendEducationPlaceProtocol(){}
    public static class SendEducationPlaceRequest implements AbstractRequest<SendEducationPlaceRequest> {
        public String key;
        public EducationPlace[] schools;
        public EducationPlace[] universities;
        public String token;

        public SendEducationPlaceRequest(String key, String token,
                                         EducationPlace[] schools,
                                         EducationPlace[] universities) {
            this.key = key;
            this.schools = schools;
            this.universities = universities;
            if(token == null) {
                throw new IllegalArgumentException("User token is null");
            }
            this.token = token;
        }
    }

    public static class SendEducationPlaceResponse implements AbstractResponse<SendEducationPlaceResponse> {
        public SendEducationPlaceResponse(String status) {
            this.status = status;
        }

        public String status; // success
        public String message;
    }

    public static SendEducationPlaceRequest getSendSchoolRequest(String token, EducationPlace[] schools) {
        return new SendEducationPlaceRequest("addSchool", token, schools, null);
    }

    public static SendEducationPlaceRequest getSendUniversityRequest(String token, EducationPlace[] universities) {
        return new SendEducationPlaceRequest("addUniversity", token, null, universities);
    }

    public static class EducationPlace {
        public String country;
        public String city;
        public String name;

        public EducationPlace(String country, String city, String name) {
            this.country = country;
            this.city = city;
            this.name = name;
        }
    }
}
