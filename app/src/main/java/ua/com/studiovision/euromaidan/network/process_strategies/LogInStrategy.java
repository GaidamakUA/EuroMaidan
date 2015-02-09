package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.activities.LoginActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.LoginProtocol;

/**
 * Created by gaidamak on 08.12.14.
 */
public class LogInStrategy extends AbstractProcessResponseStrategy
        <LoginProtocol.LogInRequest, LoginProtocol.LogInResponse> {
    private static final String TAG = "SearchStrategy";
    StrategyCallbacks callbacks;

    public LogInStrategy(Context context, Message message, StrategyCallbacks callbacks) {
        super(context, LoginProtocol.LogInResponse.class);

        Bundle loginData = message.getData();
        String login = loginData.getString(LoginActivity.LOGIN);
        String password = loginData.getString(LoginActivity.PASSWORD);
        this.callbacks = callbacks;
        request = new LoginProtocol.LogInRequest(login, password);
    }

    @Override
    public void onResponse(LoginProtocol.LogInResponse response) {
        Bundle userData = new Bundle();
        userData.putString(LoginActivity.TOKEN, response.token);
        userData.putLong(LoginActivity.USER_ID, response.id_user);
        Message msg = Message.obtain();
        msg.setData(userData);
        msg.what = AppProtocol.LOG_IN_SUCCESSFUL;
        callbacks.sendMessageToActivity(msg);
    }

    @Override
    protected void onError(LoginProtocol.LogInResponse response) {
        Message msg = Message.obtain();
        msg.what = AppProtocol.LOG_IN_UNSUCCESSFUL;
        callbacks.sendMessageToActivity(msg);
        super.onError(response);
    }
}
