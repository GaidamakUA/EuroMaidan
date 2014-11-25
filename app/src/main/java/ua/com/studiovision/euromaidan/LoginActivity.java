package ua.com.studiovision.euromaidan;

import android.app.Activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity (R.layout.activity_login)
public class LoginActivity extends Activity {
    @Click(R.id.register_button)
    void register() {
        RegisterActivity_.intent(this).start();
    }
}
