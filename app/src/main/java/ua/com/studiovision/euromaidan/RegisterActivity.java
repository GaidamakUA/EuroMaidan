package ua.com.studiovision.euromaidan;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends ActivityServiceCommunicationActivity {
    private static final String TAG = "RegisterActivity";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm_password";
    public static final String EMAIL = "email";
    @ViewById
    EditText nameEditText;
    @ViewById
    EditText surnameEditText;
    @ViewById
    EditText passwordEditText;
    @ViewById
    EditText confirmPasswordEditText;
    @ViewById
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;
    }

    @Click (R.id.saveButton)
    void onSaveButtonClick() {
        Message msg = Message.obtain();
        msg.what = AppProtocol.DO_REGISTER;
        Bundle registrationData = new Bundle();
        registrationData.putString(NAME, nameEditText.getText().toString());
        registrationData.putString(SURNAME, surnameEditText.getText().toString());
        registrationData.putString(PASSWORD, passwordEditText.getText().toString());
        registrationData.putString(CONFIRM_PASSWORD, confirmPasswordEditText.getText().toString());
        registrationData.putString(EMAIL, emailEditText.getText().toString());
        msg.setData(registrationData);
        sendMessage(msg);
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "Service connected");
                break;
            case AppProtocol.REGISTRATION_SUCCESSFUL:
                Log.v(TAG, "REGISTRATION_SUCCESSFUL");
                break;
            case AppProtocol.REGISTRATION_UNSUCCESSFUL:
                Log.v(TAG, "REGISTRATION_UNSUCCESSFUL");
                break;
        }
    }
}

