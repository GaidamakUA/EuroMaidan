package ua.com.studiovision.euromaidan.network;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.SupposeBackground;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;
import ua.com.studiovision.euromaidan.network.process_strategies.AbstractProcessResponseStrategy;

/**
 * Created by gaidamak on 22.01.15.
 */
public final class StaticRequestUtils {
    private static final String BASE_URL = "http://e-m.com.ua/api";
    private static final String TAG = "StaticRequestUtils";
    private static final Gson gson = new Gson();

    /**
     * Performs requst defined by strategy.
     * You should use this class only in background thread.
     * @param strategy
     */
    public static void doRequest(AbstractProcessResponseStrategy strategy) {
        Log.v(TAG, "MainService.doRequest(strategy=" + strategy + ")");
        try {
            strategy.handleResponse(
                    executeRequest(strategy.getRequest(), strategy.getResponseClass()));
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    private static <T extends AbstractResponse<T>> T executeRequest(AbstractRequest request, Class<T> tClass) throws IOException {
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

    private static String doPost(String parameters) throws IOException {
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

    private static String readIt(InputStream stream) throws IOException {
        java.util.Scanner s = new java.util.Scanner(stream, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
