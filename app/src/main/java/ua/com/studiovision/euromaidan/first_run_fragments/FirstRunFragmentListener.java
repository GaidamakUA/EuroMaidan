package ua.com.studiovision.euromaidan.first_run_fragments;

import android.app.Fragment;

public interface FirstRunFragmentListener {
    void changeFragment(Fragment fragment);
    void tryRequestCountries(String countryName);
    void tryRequestCities(String cityName, long countryId);
    void tryRequestUniversities(String universityName, long cityId);
    void tryRequestSchools(String schoolName, long cityId);
}
