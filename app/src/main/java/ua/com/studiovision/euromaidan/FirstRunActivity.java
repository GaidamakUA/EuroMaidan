package ua.com.studiovision.euromaidan;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import org.androidannotations.annotations.EActivity;

import ua.com.studiovision.euromaidan.firstrunfragments.SchoolFragment_;
import ua.com.studiovision.euromaidan.firstrunfragments.UniversityFragment_;

@EActivity
public class FirstRunActivity extends Activity implements FirstRunFragmentListener {
    private static final String TAG = "FirstRunActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        Log.v(TAG, "First run activity");
        if (savedInstanceState == null) {
            SchoolFragment_ schoolFragment = new SchoolFragment_();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, schoolFragment, "SchoolFragment");
            fragmentTransaction.commit();
        }
    }

    public void changeFragment(Fragment fragment) {
        if (fragment instanceof SchoolFragment_) {
            UniversityFragment_ universityFragment = new UniversityFragment_();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, universityFragment, "UniversityFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}
