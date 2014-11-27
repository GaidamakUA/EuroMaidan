package ua.com.studiovision.euromaidan.firstrunfragments;

import android.app.Fragment;

public interface FirstRunFragmentListener {
    void changeFragment(Fragment fragment);
    void tryRequestCountries(String countryName);
    void tryRequestCities(String cityName);
    void tryRequestUniversities(String universityName);
    void tryRequestSchools(String schoolName);
}
