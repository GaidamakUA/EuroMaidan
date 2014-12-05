package ua.com.studiovision.euromaidan.json_protocol;

public class RegistrationProtocol {

    // Do not create class
    private RegistrationProtocol() {
    }

    ;

    public static class Request implements AbstractRequest<Request> {
        // TODO make final
        public String key = "user_registration";
        public String name; // min 2
        public String surname; // min 2
        public String password; // min 6
        public String password_check; // repeat password
        public String email; // valid email

        public Request(String name, String surname, String password, String password_check,
                       String email) {
            this.name = name;
            this.surname = surname;
            this.password = password;
            this.password_check = password_check;
            this.email = email;
        }
    }

    public static class Response extends AbstractResponse<Response> {
    }
}
