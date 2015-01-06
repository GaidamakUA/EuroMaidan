package ua.com.studiovision.euromaidan.network.json_protocol;

import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;

public class AbstractGetArrayProtocol {

    public static class AbstractArrayRequest implements AbstractRequest<AbstractArrayRequest> {
        public String key;
        public String lotName;
        public Long id_country;
        public Long id_city;
        public Long id_user;
        public FriendsContent content;

        public AbstractArrayRequest(String key, String lotName) {
            this.key = key;
            this.lotName = lotName;
        }

        public AbstractArrayRequest(Long id_user, FriendsContent content) {
            this.id_user = id_user;
            this.content = content;
        }
    }

    public static class AbstractArrayResponse extends AbstractResponse<AbstractArrayResponse> {
        public Content content;

        public class AbstractItem {
            public long id;
            public String name;
        }

        public class Content {
            public AbstractItem[] array;
        }
    }

    public static AbstractArrayRequest getCountries(String lotName) {
        return new AbstractArrayRequest("getCountries", lotName);
    }

    public static AbstractArrayRequest getCities(String lotName, long countryId) {
        AbstractArrayRequest request = new AbstractArrayRequest("getCities", lotName);
        request.id_country = countryId;
        return request;
    }

    public static AbstractArrayRequest getUniversities(String lotName, long cityId) {
        AbstractArrayRequest request = new AbstractArrayRequest("getUniversities", lotName);
        request.id_city = cityId;
        return request;
    }

    public static AbstractArrayRequest getSchools(String lotName, long cityId) {
        AbstractArrayRequest request = new AbstractArrayRequest("getSchools", lotName);
        request.id_city = cityId;
        return request;
    }

    public static AbstractArrayRequest getFriends(long userId, FriendsContent content){
        return new AbstractArrayRequest(userId, content);
    }
}
