package ua.com.studiovision.euromaidan.network;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.softevol.activity_service_communication.ActivityServiceCommunicationService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SupposeBackground;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;
import ua.com.studiovision.euromaidan.network.process_strategies.AbstractProcessResponseStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.AddFriendStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.DeleteFriendStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetCitiesStrategy;
import ua.com.studiovision.euromaidan.network.process_strategies.GetCountriesStrategy;
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
    private static final String BASE_URL = "http://e-m.com.ua/api";
    private static final Gson gson = new Gson();

    private Handler UiThreadHandler;

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
        }
    }

    @Background
    void doRequest(AbstractProcessResponseStrategy strategy) {
        Log.v(TAG, "MainService.doRequest(strategy=" + strategy + ")");
        try {
            strategy.handleResponse(
                    executeRequest(strategy.getRequest(), strategy.getResponseClass()));
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    @SupposeBackground
    <T extends AbstractResponse<T>> T executeRequest(AbstractRequest request, Class<T> tClass) throws IOException {
        String requestString = gson.toJson(request);
        Log.v(TAG, "beforeEncode=" + requestString);
        requestString = "data=" + URLEncoder.encode(
                Base64.encodeToString(requestString.getBytes("UTF-8"), Base64.DEFAULT), "UTF-8")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%7E", "~");
//        requestString = URLEncoder.encode(requestString, "UTF-8");
        return gson.fromJson(doPost(requestString), tClass);
    }

    @SupposeBackground
    String doPost(String parameters) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
        Log.v(TAG, "parameters=\"" + parameters + "\"");
        writer.write(parameters);
        writer.close();
        urlConnection.connect();
        String responseString = readIt(urlConnection.getInputStream());
        Log.v(TAG, "response=" + responseString);
        return responseString;
    }

    private String readIt(InputStream stream) throws IOException {
        java.util.Scanner s = new java.util.Scanner(stream, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
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
