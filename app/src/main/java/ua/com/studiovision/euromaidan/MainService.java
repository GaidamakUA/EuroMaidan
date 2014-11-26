package ua.com.studiovision.euromaidan;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softevol.activity_service_communication.ActivityServiceCommunicationService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.UiThread;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ua.com.studiovision.euromaidan.jsonprotocol.LoginProtocol;

@EService
public class MainService extends ActivityServiceCommunicationService {
    private static final String TAG = "MainService";
    private static final String BASE_URL = "http://e-m.com.ua/api";
    private static final Gson gson = new Gson();

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.DO_LOG_IN:
                Log.v(TAG, "Log in");
                Bundle loginData = msg.getData();
                String login = loginData.getString(LoginActivity.LOGIN);
                String password = loginData.getString(LoginActivity.PASSWORD);
                doLogIn(login, password);
                break;
        }
    }

    @Background
    void doLogIn(String login, String password) {
        Log.v(TAG, "MainService.doLogIn(" + "login=" + login + ", password=" + password + ")");
        URL url;
        try {
            url = new URL(BASE_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            LoginProtocol.Request request = new LoginProtocol.Request(login, password);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            // TODO probably wrap with Buffered writer
            String requestString = "data=" + gson.toJson(request);
            Log.v(TAG, "requestString=\"" + requestString + "\"");
            writer.write(requestString);
            writer.close();
            urlConnection.connect();
            String responseString = readIt(urlConnection.getInputStream());
            Log.v(TAG, "response=" + responseString);
            LoginProtocol.Response response =
                    gson.fromJson(responseString, LoginProtocol.Response.class);

            assert (response.status != null);

            if(response.status.equals("success")) {
                // TODO send success message to activity
                Message msg = Message.obtain();
                msg.what = AppProtocol.LOG_IN_SUCCESSFUL;
                sendMessage(msg);
            } else if (response.status.equals("error")) {
                showErrorMessage(response.message);
                Message msg = Message.obtain();
                msg.what = AppProtocol.LOG_IN_UNSUCCESSFUL;
                sendMessage(msg);
                // TODO send error message to activity
            } else {
                throw new RuntimeException("Illegal answer from server");
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "", e);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    @UiThread
    void showErrorMessage(String errorMessage) {
        Toast toast = Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    private String readIt(InputStream stream) throws IOException {
        java.util.Scanner s = new java.util.Scanner(stream, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
