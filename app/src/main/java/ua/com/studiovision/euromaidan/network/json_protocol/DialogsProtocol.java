package ua.com.studiovision.euromaidan.network.json_protocol;

/**
 * Created by gaidamak on 23.01.15.
 */
public class DialogsProtocol {
    public static class GetDialogsRequest implements AbstractRequest<GetDialogsRequest> {
        public final String key = "get_dialogs";
        public String token;

        public GetDialogsRequest(String token) {
            this.token = token;
        }
    }
    public static class GetDialogsResponse extends AbstractResponse<GetDialogsResponse> {
        DialogsEntity[] dialogues;
    }

    private static class DialogsEntity {
        public String message;
        public int mine;
        public String name;
        public String surname;
        public String avatar;
        public int new_count;
        public long time;
        public long user;
    }
}
