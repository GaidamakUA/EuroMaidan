package ua.com.studiovision.euromaidan;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softevol.activity_service_communication.ActivityServiceCommunicationService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SupposeBackground;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.BackgroundExecutor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ua.com.studiovision.euromaidan.jsonprotocol.AbstractGetProtocol;
import ua.com.studiovision.euromaidan.jsonprotocol.AbstractRequest;
import ua.com.studiovision.euromaidan.jsonprotocol.AbstractResponse;
import ua.com.studiovision.euromaidan.jsonprotocol.LoginProtocol;
import ua.com.studiovision.euromaidan.jsonprotocol.RegistrationProtocol;
import ua.com.studiovision.euromaidan.provider.country.CountryContentValues;

@EService
public class MainService extends ActivityServiceCommunicationService {
    private static final String TAG = "MainService";
    private static final String BASE_URL = "http://e-m.com.ua/api";
    private static final Gson gson = new Gson();

    // Not following YAGNI principle
    @Pref
    SharedPrefs_ mSharedPrefs;

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.DO_LOG_IN:
                Log.v(TAG, "Log in");
                Bundle loginData = msg.getData();
                String login = loginData.getString(LoginActivity.LOGIN);
                String password = loginData.getString(LoginActivity.PASSWORD);
                // true is for may interrupt if running
                BackgroundExecutor.cancelAll(LoginActivity.LOGIN, true);
                doLogIn(login, password);
                break;
            case AppProtocol.DO_REGISTER:
                Log.v(TAG, "Registration");
                Bundle registrationData = msg.getData();
                String name = registrationData.getString(RegisterActivity.NAME);
                String surname = registrationData.getString(RegisterActivity.SURNAME);
                password = registrationData.getString(RegisterActivity.PASSWORD);
                String confirmPassword = registrationData.getString(RegisterActivity.CONFIRM_PASSWORD);
                String email = registrationData.getString(RegisterActivity.EMAIL);
                doRegister(name, surname, password, confirmPassword, email);
                break;
            case AppProtocol.REQUEST_COUNTRIES:
                Log.v(TAG, "Countries");
                Bundle bundle = msg.getData();
                String countryNamePart = bundle.getString(FirstRunActivity.COUNTRY_NAME);
                doRequestCountries(countryNamePart);
        }
    }

    @Background
    void doRequestCountries(String countryName) {
        Log.v(TAG, "MainService.doRequestCountries(" + "countryName=" + countryName + ")");
        try {
            AbstractGetProtocol.AbstractArrayRequest request =
                    AbstractGetProtocol.getCountries(countryName);
            AbstractGetProtocol.Response response =
                    executeRequest(request, AbstractGetProtocol.Response.class);
            CountryContentValues contentValues;
            for (AbstractGetProtocol.Response.AbstractItem item : response.array) {
                contentValues = new CountryContentValues();
                contentValues.putCountryId(item.id);
                contentValues.putCountryName(item.name);
                contentValues.insert(getContentResolver());
            }

        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    @Background
    void doRegister(String name, String surname, String password, String confirmPassword,
                            String email) {
        Log.v(TAG, "MainService.doRegister(" + "name=" + name + ", surname=" + surname
                + ", password=" + password + ", confirmPassword=" + confirmPassword + ", email="
                + email + ")");
        try {
            RegistrationProtocol.Request request = new RegistrationProtocol.Request(name, surname,
                    password, confirmPassword, email);
            RegistrationProtocol.Response response =
                    executeRequest(request, RegistrationProtocol.Response.class);
            if(response.status.equals("success")) {
                Message msg = Message.obtain();
                msg.what = AppProtocol.REGISTRATION_SUCCESSFUL;
                sendMessage(msg);
                // XXX hardcode
            } else if (response.status.equals("error")) {
                showErrorMessage(response.message);
                Message msg = Message.obtain();
                msg.what = AppProtocol.REGISTRATION_UNSUCCESSFUL;
                sendMessage(msg);
            } else {
                throw new RuntimeException("Illegal answer from server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background(id=LoginActivity.LOGIN)
    void doLogIn(String login, String password) {
        Log.v(TAG, "MainService.doLogIn(" + "login=" + login + ", password=" + password + ")");
        try {
            LoginProtocol.Request request = new LoginProtocol.Request(login, password);
            LoginProtocol.Response response = executeRequest(request, LoginProtocol.Response.class);

            assert (response.status != null);

            // XXX hardcode
            if(response.status.equals("success")) {
                Message msg = Message.obtain();
                msg.what = AppProtocol.LOG_IN_SUCCESSFUL;
                sendMessage(msg);
                mSharedPrefs.getToken().put(response.token);
                // XXX hardcode
            } else if (response.status.equals("error")) {
                showErrorMessage(response.message);
                Message msg = Message.obtain();
                msg.what = AppProtocol.LOG_IN_UNSUCCESSFUL;
                sendMessage(msg);
            } else {
                throw new RuntimeException("Illegal answer from server");
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "", e);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    @SupposeBackground
    <T extends AbstractResponse<T>> T executeRequest(AbstractRequest request, Class<T> tClass) throws IOException {
        String requestString = "data=" + gson.toJson(request);
        return gson.fromJson(doPost(requestString), tClass);
    }

    @SupposeBackground
    String doPost(String parameters) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
        // TODO probably wrap with Buffered writer
        Log.v(TAG, "parameters=\"" + parameters + "\"");
        writer.write(parameters);
        writer.close();
        urlConnection.connect();
        String responseString = readIt(urlConnection.getInputStream());
        Log.v(TAG, "response=" + responseString);
        return responseString;
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
