package ua.com.studiovision.euromaidan.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.softevol.activity_service_communication.ActivityServiceCommunicationService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.network.json_protocol.socket_messaging.SocketLoginProtocol;
import ua.com.studiovision.euromaidan.network.process_strategies.AbstractProcessResponseStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.AddFriendStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.DeleteFriendStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetCitiesStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetCountriesStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetDialogsStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetFriendsStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetSchoolsStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetSettingsStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetUniversitiesStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.LogInStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.RegisterStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.SearchStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.SendSchoolStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.SendSettingsStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.SendUniversityStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.StrategyCallbacks;

@EService
public class MainService extends ActivityServiceCommunicationService implements StrategyCallbacks, UserInterfaceCallbacks {
    private static final String TAG = "MainService";

    private Handler UiThreadHandler;
    @Pref
    SharedPrefs_ sharedPrefs;
    WebSocketHandler webSocketHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        webSocketHandler = new WebSocketHandler(sharedPrefs.getToken().get(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webSocketHandler.closeWebSocket();
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.DO_LOG_IN:
                Log.v(TAG, "Log in");
                doRequest(new LogInStrategy(getApplicationContext(), msg, this));
                break;
            case AppProtocol.DO_REGISTER:
                Log.v(TAG, "Registration");
                doRequest(new RegisterStrategy(getApplicationContext(), msg, this));
                break;
            case AppProtocol.REQUEST_COUNTRIES:
                Log.v(TAG, "Countries");
                doRequest(new GetCountriesStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.REQUEST_CITIES:
                Log.v(TAG, "Cities");
                doRequest(new GetCitiesStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.REQUEST_SCHOOLS:
                Log.v(TAG, "Request Schools");
                doRequest(new GetSchoolsStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.REQUEST_UNIVERSITIES:
                Log.v(TAG, "Request Universities");
                doRequest(new GetUniversitiesStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.SEND_SCHOOL:
                Log.v(TAG, "Send schools");
                doRequest(new SendSchoolStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.SEND_UNIVERSITY:
                Log.v(TAG, "Send universities");
                doRequest(new SendUniversityStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.SEND_PROFILE:
                Log.v(TAG, "Send profile");
                doRequest(new SendSettingsStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.REQUEST_USER_SETTINGS:
                Log.v(TAG, "Request user settings");
                doRequest(new GetSettingsStrategy(getApplicationContext(), msg, this));
                break;
            case AppProtocol.SEARCH:
                Log.v(TAG, "Search");
                doRequest(new SearchStrategy(getApplicationContext(), msg, this));
                break;
            case AppProtocol.ADD_FRIEND:
                Log.v(TAG, "Add friend");
                doRequest(new AddFriendStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.DELETE_FRIEND:
                Log.v(TAG, "Delete friend");
                doRequest(new DeleteFriendStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.GET_FRIENDS:
                Log.v(TAG, "Get friends");
                doRequest(new GetFriendsStrategy(getApplicationContext(), msg));
                break;
            case AppProtocol.GET_DIALOGS:
                Log.v(TAG, "Get dialogs");
                doRequest(new GetDialogsStrategy(getApplicationContext(), sharedPrefs.getToken().get()));
                break;
        }
    }

    @Background
    void doRequest(AbstractProcessResponseStrategy strategy) {
        StaticRequestUtils.doRequest(strategy);
    }

    @Override
    public void sendMessageToActivity(Message message) {
        sendMessage(message);
    }

    @Override
    public void executeOnUiThread(Runnable runnable) {
        if (UiThreadHandler == null) {
            UiThreadHandler = new Handler();
        }
        UiThreadHandler.post(runnable);
    }
}
