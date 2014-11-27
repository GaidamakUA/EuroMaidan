package ua.com.studiovision.euromaidan.jsonprotocol;

public class AbstractGetProtocol {

    public static class AbstractArrayRequest {
        public String key;
        public String lotName;

        public AbstractArrayRequest(String key, String lotName) {
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

    public static AbstractArrayRequest getCountries(String lotName) {
        return new AbstractArrayRequest("getCountries", lotName);
    }

    public static AbstractArrayRequest getCities(String lotName) {
        return new AbstractArrayRequest("getCities", lotName);
    }

    public static AbstractArrayRequest getUniversities(String lotName) {
        return new AbstractArrayRequest("getUniversities", lotName);
    }

    public static AbstractArrayRequest getSchools(String lotName) {
        return new AbstractArrayRequest("getSchools", lotName);
    }
}
