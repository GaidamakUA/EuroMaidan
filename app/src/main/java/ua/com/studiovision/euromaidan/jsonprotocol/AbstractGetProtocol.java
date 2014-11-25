package ua.com.studiovision.euromaidan.jsonprotocol;

public class AbstractGetProtocol {

    public static class AbstractRequest {
        public String key;
        public String lotName;

        public AbstractRequest(String key, String lotName) {
            this.key = key;
            this.lotName = lotName;
        }
    }

    public static class Response {
        AbstractItem[] array;

        public class AbstractItem {
            public long id;
            public String name;
        }
    }

    public static AbstractRequest getCountries(String lotName) {
        return new AbstractRequest("getCountries", lotName);
    }

    public static AbstractRequest getCities(String lotName) {
        return new AbstractRequest("getCities", lotName);
    }

    public static AbstractRequest getUniversities(String lotName) {
        return new AbstractRequest("getUniversities", lotName);
    }

    public static AbstractRequest getSchools(String lotName) {
        return new AbstractRequest("getSchools", lotName);
    }
}
