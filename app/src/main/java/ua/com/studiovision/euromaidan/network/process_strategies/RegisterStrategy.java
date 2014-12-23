package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.activities.RegisterActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.RegistrationProtocol;

/**
 * Created by gaidamak on 08.12.14.
 */
public class RegisterStrategy extends AbstractProcessResponseStrategy
        <RegistrationProtocol.RegistrationRequest, RegistrationProtocol.RegistrationResponse> {
    private static final String TAG = "SearchStrategy";
    StrategyCallbacks callbacks;

    public RegisterStrategy(Context context, Message message, StrategyCallbacks callbacks) {
        super(context, RegistrationProtocol.RegistrationResponse.class);
        Bundle registrationData = message.getData();
        String name = registrationData.getString(RegisterActivity.NAME);
        String surname = registrationData.getString(RegisterActivity.SURNAME);
        String password = registrationData.getString(RegisterActivity.PASSWORD);
        String confirmPassword = registrationData.getString(RegisterActivity.CONFIRM_PASSWORD);
        String email = registrationData.getString(RegisterActivity.EMAIL);

        this.callbacks = callbacks;

        request = new RegistrationProtocol.RegistrationRequest(name, surname,
                password, confirmPassword, email);
        this.callbacks = callbacks;
    }

    @Override
    public void onResponse(RegistrationProtocol.RegistrationResponse response) {
        Message msg = Message.obtain();
        msg.what = AppProtocol.REGISTRATION_SUCCESSFUL;
        callbacks.sendMessageToActivity(msg);
    }

    @Override
    protected void onError(RegistrationProtocol.RegistrationResponse response) {
        Message msg = Message.obtain();
        msg.what = AppProtocol.REGISTRATION_UNSUCCESSFUL;
        callbacks.sendMessageToActivity(msg);
        super.onError(response);
    }
}
