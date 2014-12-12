package ua.com.studiovision.euromaidan.network.json_protocol.friends;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

public final class DeleteFriendProtocol {
    private DeleteFriendProtocol() {
    }

    public static class DeleteFriendRequest implements AbstractRequest<DeleteFriendRequest> {
        public String key = "delete_friend";
        public String token;
        public long idFriend = -1;

        public DeleteFriendRequest(String token, Long idFriend) {
            this.token = token;
            this.idFriend = idFriend;
        }
    }

    public static class DeleteFriendResponse extends AbstractResponse<DeleteFriendResponse> {
    }
}
