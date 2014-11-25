package ua.com.studiovision.euromaidan.jsonprotocol;

public class LoginProtocol {
    private LoginProtocol() {}
    public static class Request {
        // TODO make final
        public String key = "user_authorization";
        public String email;
        public String password;
    }
    public static class Response {
        public String status; // success
        public String token;
    }
}
