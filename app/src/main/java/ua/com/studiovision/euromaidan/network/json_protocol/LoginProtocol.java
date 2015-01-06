package ua.com.studiovision.euromaidan.network.json_protocol;

public class LoginProtocol {
    private LoginProtocol() {
    }

    public static class LogInRequest implements AbstractRequest<LogInRequest> {
        public final String key = "user_authorization";
        public String email;
        public String password;

        public LogInRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static class LogInResponse extends AbstractResponse<LogInResponse> {
        public String token;
        public long id_user;
    }
}