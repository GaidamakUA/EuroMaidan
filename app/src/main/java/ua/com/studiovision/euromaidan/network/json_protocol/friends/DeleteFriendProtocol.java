package ua.com.studiovision.euromaidan.network.json_protocol.friends;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

public final class DeleteFriendProtocol {
    private DeleteFriendProtocol() {
    }

    public static class DeleteFriendRequest implements AbstractRequest<DeleteFriendRequest> {
        public String key = "delete_friend";
        public String token;
        public long id_friend = -1;

        public DeleteFriendRequest(String token, Long id_friend) {
            this.token = token;
            this.id_friend = id_friend;
        }
    }

    public static class DeleteFriendResponse extends AbstractResponse<DeleteFriendResponse> {
    }
}
