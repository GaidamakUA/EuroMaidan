package ua.com.studiovision.euromaidan;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.EActivity;

import ua.com.studiovision.euromaidan.firstrunfragments.FirstRunFragmentListener;
import ua.com.studiovision.euromaidan.firstrunfragments.SchoolFragment_;
import ua.com.studiovision.euromaidan.firstrunfragments.UniversityFragment_;

@EActivity
public class FirstRunActivity extends ActivityServiceCommunicationActivity
        implements FirstRunFragmentListener {
    private static final String TAG = "FirstRunActivity";

    public static final String COUNTRY_NAME = "country_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;
        setContentView(R.layout.activity_holder_only);
        Log.v(TAG, "First run activity");
        if (savedInstanceState == null) {
            SchoolFragment_ schoolFragment = new SchoolFragment_();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, schoolFragment, "SchoolFragment");
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "Service connected");
                break;
        }
    }

    public void changeFragment(Fragment fragment) {
        if (fragment instanceof SchoolFragment_) {
            UniversityFragment_ universityFragment = new UniversityFragment_();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, universityFragment, "UniversityFragment")
                    .addToBackStack(null)
                    .commit();
        } else if (fragment instanceof UniversityFragment_) {
            FeedActivity_.intent(this).start();
            finish();
        }
    }

    @Override
    public void tryRequestCountries(String countryName) {
        Log.v(TAG, "FirstRunActivity.tryRequestCountries(" + "countryName=" + countryName + ")");
        Message msg = Message.obtain();
        msg.what = AppProtocol.REQUEST_COUNTRIES;
        Bundle bundle = new Bundle();
        bundle.putString(COUNTRY_NAME, countryName);
        sendMessage(msg);
    }

    @Override
    public void tryRequestCities(String cityName) {

    }

    @Override
    public void tryRequestUniversities(String universityName) {

    }

    @Override
    public void tryRequestSchools(String schoolName) {

    }
}
