package ua.com.studiovision.euromaidan.activities;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.MainService_;

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

    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
    }

    @Click(R.id.save_button)
    void onSaveButtonClick() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirmation = confirmPasswordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        boolean inputProblem = false;
        if (name.length() < 2) {
            nameEditText.startAnimation(shake);
            inputProblem = true;
        }
        if (surname.length() < 2) {
            surnameEditText.startAnimation(shake);
            inputProblem = true;
        }
        if (password.length() < 6) {
            passwordEditText.startAnimation(shake);
            passwordEditText.setText("");
            inputProblem = true;
        }
        if (passwordConfirmation.length() < 6 || !passwordConfirmation.equals(password)) {
            confirmPasswordEditText.startAnimation(shake);
            confirmPasswordEditText.setText("");
            inputProblem = true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.startAnimation(shake);
            inputProblem = true;
        }
        if (inputProblem) return;

        Message msg = Message.obtain();
        msg.what = AppProtocol.DO_REGISTER;
        Bundle registrationData = new Bundle();
        registrationData.putString(NAME, name);
        registrationData.putString(SURNAME, surname);
        registrationData.putString(PASSWORD, password);
        registrationData.putString(CONFIRM_PASSWORD, passwordConfirmation);
        registrationData.putString(EMAIL, email);
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

