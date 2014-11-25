package ua.com.studiovision.euromaidan.jsonprotocol;

public class RegistrationProtocol {

    // Do not create class
    private RegistrationProtocol(){};

    public static class Request {
        // TODO make final
        public String key = "user_registration";
        public String name; // min 2
        public String password; // min 6
        public String password_check; // repeat password
        public String email; // valid email

        public Request(String name, String password, String password_check, String email) {
            this.name = name;
            this.password = password;
            this.password_check = password_check;
            this.email = email;
        }
    }

    public static class Response {
        public Response(String status) {
            this.status = status;
        }

        public String status; // success
    }
}
