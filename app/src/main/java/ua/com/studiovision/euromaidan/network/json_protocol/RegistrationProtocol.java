package ua.com.studiovision.euromaidan.network.json_protocol;

public final class RegistrationProtocol {

    // Do not create class
    private RegistrationProtocol() {
    }

    public static class RegistrationRequest implements AbstractRequest<RegistrationRequest> {
        public final String key = "user_registration";
        public String name; // min 2
        public String surname; // min 2
        public String password; // min 6
        public String password_check; // repeat password
        public String email; // valid email

        public RegistrationRequest(String name, String surname, String password,
                                   String password_check, String email) {
            this.name = name;
            this.surname = surname;
            this.password = password;
            this.password_check = password_check;
            this.email = email;
        }
    }

    public static class RegistrationResponse extends AbstractResponse<RegistrationResponse> {
    }
}
