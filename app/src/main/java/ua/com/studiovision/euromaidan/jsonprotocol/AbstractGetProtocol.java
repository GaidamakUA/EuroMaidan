package ua.com.studiovision.euromaidan.jsonprotocol;

public class AbstractGetProtocol {

    public static class AbstractArrayRequest implements AbstractRequest<AbstractArrayRequest> {
        public String key;
        public String lotName;

        public AbstractArrayRequest(String key, String lotName) {
            this.key = key;
            this.lotName = lotName;
        }
    }

    public static class Response implements AbstractResponse<Response> {
        public AbstractItem[] array;

        public class AbstractItem {
            public long id;
            public String name;
        }
    }

    public static AbstractArrayRequest getCountries(String lotName) {
        return new AbstractArrayRequest("getCountries", lotName);
    }

    // TODO set country ID
    public static AbstractArrayRequest getCities(String lotName) {
        return new AbstractArrayRequest("getCities", lotName);
    }

    // TODO set city ID
    public static AbstractArrayRequest getUniversities(String lotName) {
        return new AbstractArrayRequest("getUniversities", lotName);
    }

    // TODO set city ID
    public static AbstractArrayRequest getSchools(String lotName) {
        return new AbstractArrayRequest("getSchools", lotName);
    }
}
