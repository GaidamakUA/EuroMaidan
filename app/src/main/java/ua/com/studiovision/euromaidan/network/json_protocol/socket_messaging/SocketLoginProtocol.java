package ua.com.studiovision.euromaidan.network.json_protocol.socket_messaging;

/**
 * Created by gaidamak on 23.01.15.
 */
public class SocketLoginProtocol {
    final String type = "auth";
    String token;

    public SocketLoginProtocol(String token) {
        this.token = token;
    }
}
