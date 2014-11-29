package ua.com.studiovision.euromaidan.jsonprotocol;

public class AbstractGetProtocol {

    public static class AbstractArrayRequest implements AbstractRequest<AbstractArrayRequest> {
        public String key;
        public String lotName;
        // not in all request, may be dangerous
        // TODO review maybe replace with anonymous or named child class
        public Long id_country;
        public Long id_city;

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

    public static AbstractArrayRequest getCities(String lotName, long countryId) {
        AbstractArrayRequest request = new AbstractArrayRequest("getCities", lotName);
        request.id_country = countryId;
        return request;
    }

    // TODO set city ID
    public static AbstractArrayRequest getUniversities(String lotName) {
        return new AbstractArrayRequest("getUniversities", lotName);
    }

    public static AbstractArrayRequest getSchools(String lotName, long cityId) {
        AbstractArrayRequest request = new AbstractArrayRequest("getSchools", lotName);
        request.id_city = cityId;
        return request;
    }
}
