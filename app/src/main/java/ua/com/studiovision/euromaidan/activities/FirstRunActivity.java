package ua.com.studiovision.euromaidan.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.first_run_fragments.FirstRunFragmentListener;
import ua.com.studiovision.euromaidan.first_run_fragments.SchoolFragment_;
import ua.com.studiovision.euromaidan.first_run_fragments.UniversityFragment_;
import ua.com.studiovision.euromaidan.network.MainService_;

@EActivity
public class FirstRunActivity extends ActivityServiceCommunicationActivity
        implements FirstRunFragmentListener {
    private static final String TAG = "FirstRunActivity";

    // GET
    public static final String NAME_PART = "name_part";
    public static final String PARENT_ITEM_ID = "parent_item_id";

    // SEND
    public static final String COUNTRY_NAME = "country_name";
    public static final String CITY_NAME = "city_name";
    public static final String SCHOOL_NAME = "school_name";
    public static final String TOKEN = "token";

    @Pref
    SharedPrefs_ preferences;

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
        Bundle bundle = new Bundle();
        bundle.putString(NAME_PART, countryName);
        sendStuff(AppProtocol.REQUEST_COUNTRIES, bundle);
    }

    @Override
    public void tryRequestCities(String cityName, long countryId) {
        Bundle bundle = new Bundle();
        bundle.putString(NAME_PART, cityName);
        bundle.putLong(PARENT_ITEM_ID, countryId);
        sendStuff(AppProtocol.REQUEST_CITIES, bundle);
    }

    @Override
    public void tryRequestUniversities(String universityName, long cityId) {
        Bundle bundle = new Bundle();
        bundle.putString(NAME_PART, universityName);
        bundle.putLong(PARENT_ITEM_ID, cityId);
        sendStuff(AppProtocol.REQUEST_UNIVERSITIES, bundle);
    }

    @Override
    public void tryRequestSchools(String schoolName, long cityId) {
        Bundle bundle = new Bundle();
        bundle.putString(NAME_PART, schoolName);
        bundle.putLong(PARENT_ITEM_ID, cityId);
        sendStuff(AppProtocol.REQUEST_SCHOOLS, bundle);
    }

    @Override
    public void sendSchoolDataToServer(String country, String city, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(COUNTRY_NAME, country);
        bundle.putString(CITY_NAME, city);
        bundle.putString(SCHOOL_NAME, name);
        bundle.putString(TOKEN, preferences.getToken().get());
        sendStuff(AppProtocol.SEND_SCHOOL, bundle);
    }

    @Override
    public void sendUniversityDataToServer(String country, String city, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(COUNTRY_NAME, country);
        bundle.putString(CITY_NAME, city);
        bundle.putString(SCHOOL_NAME, name);
        bundle.putString(TOKEN, preferences.getToken().get());
        sendStuff(AppProtocol.SEND_UNIVERSITY, bundle);
    }

    private void sendStuff(int what, Bundle parameters) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.setData(parameters);
        sendMessage(msg);
    }
}
