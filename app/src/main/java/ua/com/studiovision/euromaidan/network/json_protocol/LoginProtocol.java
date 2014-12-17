package ua.com.studiovision.euromaidan.network.json_protocol;

public class LoginProtocol {
    private LoginProtocol() {
    }

    public static class Request implements AbstractRequest<Request> {
        // TODO make final
        public String key = "user_authorization";
        public String email;
        public String password;

        public Request(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static class Response extends AbstractResponse<Response> {
        public String token;
        public long id_user;
    }
}