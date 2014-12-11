package ua.com.studiovision.euromaidan.network.json_protocol.friends;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

public final class AddFriendProtocol {
    private AddFriendProtocol() {
    }

    public static class AddFriendRequest implements AbstractRequest<AddFriendRequest> {
        public String key = "add_friend";
        public String token;
        public long idFriend = -1;

        public AddFriendRequest(String token, Long idFriend) {
            this.token = token;
            this.idFriend = idFriend;
        }
    }

    public static class AddFriendResponse extends AbstractResponse<AddFriendResponse> {
    }
}
