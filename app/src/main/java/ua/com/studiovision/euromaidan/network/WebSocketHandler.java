package ua.com.studiovision.euromaidan.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.network.json_protocol.socket_messaging.SocketLoginProtocol;

/**
 * Created by gaidamak on 23.01.15.
 */
public class WebSocketHandler {
    private static final String TAG = "WebSocketHandler";
    private final Gson gson = new Gson();

    public static final URI uri;

    static {
        try {
            uri = new URI("ws://89.184.66.191:8080");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final Context context;

    private WebSocketClient webSocketClient;

    public WebSocketHandler(final String token, Context context) {
        this.context = context;
        webSocketClient = new MyWebSocketClient(uri, token);
        webSocketClient.connect();
    }

    public void closeWebSocket() {
        webSocketClient.close();
    }

    @Override
    protected void finalize() throws Throwable {
        webSocketClient.close();
    }

    private class MyWebSocketClient extends WebSocketClient {
        private final String token;

        private MyWebSocketClient(URI serverURI, String token) {
            super(serverURI);
            this.token = token;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.v(TAG, "onOpen(" + "handshakedata=" + handshakedata + ")");

            String message = gson.toJson(new SocketLoginProtocol(token));
            Log.v(TAG, "message:" + message);
            this.send(message);
        }

        @Override
        public void onMessage(String message) {
            Log.v(TAG, "onMessage(" + "message=" + message + ")");
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.v(TAG, "onClose(" + "code=" + code + ", reason=" + reason + ", remote=" + remote + ")");
        }

        @Override
        public void onError(Exception ex) {
            Log.v(TAG, "onError(" + "ex=" + ex + ")");
        }
    }
}
