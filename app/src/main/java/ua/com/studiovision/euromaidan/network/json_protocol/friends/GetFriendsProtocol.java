package ua.com.studiovision.euromaidan.network.json_protocol.friends;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;
import ua.com.studiovision.euromaidan.network.json_protocol.search.User;

public final class GetFriendsProtocol {
    private GetFriendsProtocol() {
    }

    public static class GetFriendsRequest implements AbstractRequest<GetFriendsRequest> {
        public String key = "get_friends_list";
        public long id_user = -1;
        public FriendsContent content;

        public GetFriendsRequest(Long id_user, FriendsContent content) {
            this.id_user = id_user;
            this.content = content;
        }
    }

    public static class GetFriendsResponse extends AbstractResponse<GetFriendsResponse> {
        public User[] content;
    }
}
