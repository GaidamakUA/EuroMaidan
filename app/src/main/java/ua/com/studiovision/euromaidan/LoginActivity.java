package ua.com.studiovision.euromaidan;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity (R.layout.activity_login)
public class LoginActivity extends ActivityServiceCommunicationActivity {
    private static final String TAG = "LoginActivity";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    @ViewById(R.id.email_edittext)
    TextView emailEditText;
    @ViewById(R.id.password_edittext)
    TextView passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "Service connected");
                break;
            case AppProtocol.LOG_IN_SUCCESSFUL:
                Log.v(TAG, "LOG_IN_SUCCESSFUL");
                FirstRunActivity_.intent(this).start();
                finish();
                break;
            case AppProtocol.LOG_IN_UNSUCCESSFUL:
                Log.v(TAG, "LOG_IN_UNSUCCESSFUL");
                break;
        }
    }

    @Click(R.id.register_button)
    void register() {
        RegisterActivity_.intent(this).start();
    }

    @Click(R.id.login_button)
    void login() {
        Message msg = Message.obtain();
        msg.what = AppProtocol.DO_LOG_IN;

        Bundle loginData = new Bundle();
        loginData.putString(LOGIN, emailEditText.getText().toString());
        loginData.putString(PASSWORD, passwordEditText.getText().toString());
        msg.setData(loginData);

        sendMessage(msg);
    }
}
