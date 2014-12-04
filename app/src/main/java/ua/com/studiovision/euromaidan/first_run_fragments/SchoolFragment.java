package ua.com.studiovision.euromaidan.first_run_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.provider.city.CityColumns;
import ua.com.studiovision.euromaidan.provider.city.CityCursor;
import ua.com.studiovision.euromaidan.provider.country.CountryColumns;
import ua.com.studiovision.euromaidan.provider.country.CountryCursor;
import ua.com.studiovision.euromaidan.provider.school.SchoolColumns;
import ua.com.studiovision.euromaidan.provider.school.SchoolCursor;

@EFragment(R.layout.fragment_school)
public class SchoolFragment extends Fragment {
    private static final String TAG = "SchoolFragment";

    @ViewById(R.id.countryAutoCompleteTextView)
    AutoCompleteTextView countryAutoCompleteTextView;
    @ViewById(R.id.cityAutoCompleteTextView)
    AutoCompleteTextView cityAutoCompleteTextView;
    @ViewById(R.id.schoolAutoCompleteTextView)
    AutoCompleteTextView schoolAutoCompleteTextView;

    FirstRunFragmentListener firstRunFragmentListener;
    SimpleCursorAdapter countryCursorAdapter;
    SimpleCursorAdapter cityCursorAdapter;
    SimpleCursorAdapter schoolCursorAdapter;

    private Long countryId = -1L;
    private Long cityId = -1L;

    private Animation shake;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        firstRunFragmentListener = (FirstRunFragmentListener) activity;

        shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
    }

    @Override
    public void onStart() {
        super.onStart();

        //---------------Country autoCompleteTextView adapter--------------------------//
        countryCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_dropdown_item_1line,
                null,
                new String[]{CountryColumns.COUNTRY_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        countryAutoCompleteTextView.setAdapter(countryCursorAdapter);

        countryCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                if (str == null || str.length() < 1)
                    return null;
                firstRunFragmentListener.tryRequestCountries(str.toString());
                return getCursor(str, CountryColumns.COUNTRY_NAME, CountryColumns.CONTENT_URI, CountryColumns.ALL_COLUMNS);
            }
        });

        countryCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            public CharSequence convertToString(Cursor cur) {
                CountryCursor cursor = new CountryCursor(cur);
//                return cur.getString(cur.getColumnIndex(CountryColumns.COUNTRY_NAME));
                countryId = cursor.getCountryId();
                return cursor.getCountryName();
            }
        });

        //---------------City autoCompleteTextView adapter---------------------------//
        cityCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_dropdown_item_1line,
                null,
                new String[]{CityColumns.CITY_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        cityAutoCompleteTextView.setAdapter(cityCursorAdapter);

        cityCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence str) {
                if (str == null || str.length() < 1 || countryId == -1L)
                    return null;
                firstRunFragmentListener.tryRequestCities(str.toString(), countryId);
                return getCursor(str, CityColumns.CITY_NAME, CityColumns.CONTENT_URI, CityColumns.ALL_COLUMNS);
            }
        });

        cityCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cur) {
                CityCursor cursor = new CityCursor(cur);
                cityId = cursor.getCityId();
                return cursor.getCityName();
            }
        });

        //--------------School autoCompleteTextView adapter-------------------------//
        schoolCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_dropdown_item_1line,
                null,
                new String[]{SchoolColumns.SCHOOL_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        schoolAutoCompleteTextView.setAdapter(schoolCursorAdapter);

        schoolCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence str) {
                if (str == null || str.length() < 1 || cityId == -1L) {
                    return null;
                }
                firstRunFragmentListener.tryRequestSchools(str.toString(), cityId);
                return getCursor(str, SchoolColumns.SCHOOL_NAME, SchoolColumns.CONTENT_URI, SchoolColumns.ALL_COLUMNS);
            }
        });

        schoolCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cur) {
                SchoolCursor cursor = new SchoolCursor(cur);
                return cursor.getSchoolName();
            }
        });
    }

    /**
     * Get cursor from DB
     *
     * @param str               User input
     * @param columnName        Column for selection
     * @param columnsContentURI Uri for all columns
     * @param projection        Table projection for query
     * @return Cursor with selected data or empty cursor if data is not found
     */
    private Cursor getCursor(CharSequence str, String columnName, Uri columnsContentURI, String[] projection) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(columnName);
        stringBuilder.append(" LIKE '%");
        stringBuilder.append(str.toString());
        stringBuilder.append("%' OR ");
        stringBuilder.append(columnName);
        stringBuilder.append(" LIKE '%");
        stringBuilder.append(Character.toUpperCase(str.charAt(0)));
        stringBuilder.append(str.subSequence(1, str.length()));
        stringBuilder.append("%'");
        Log.v(TAG, "str=" + str + "; select=" + stringBuilder.toString());
        Cursor cursor = getActivity().getBaseContext().getContentResolver()
                .query(columnsContentURI, projection,
                        stringBuilder.toString(), null, null);
        Log.v(TAG, "Cursor=" + cursor.getCount());
        return cursor;
    }

    @Click(R.id.save_button)
    void onSaveButtonClick() {
        String country = countryAutoCompleteTextView.getText().toString();
        String city = cityAutoCompleteTextView.getText().toString();
        String school = schoolAutoCompleteTextView.getText().toString();

        boolean inputProblem = false;
        if (country.length() < 2) {
            countryAutoCompleteTextView.startAnimation(shake);
            inputProblem = true;
        }
        if (city.length() < 2) {
            cityAutoCompleteTextView.startAnimation(shake);
            inputProblem = true;
        }
        if (school.length() < 2) {
            schoolAutoCompleteTextView.startAnimation(shake);
            inputProblem = true;
        }

        if (inputProblem)
            return;

        firstRunFragmentListener.sendSchoolDataToServer(country, city, school);
        // TODO check whether request was successful
        skip();
    }

    @Click(R.id.skipButton)
    void skip() {
        firstRunFragmentListener.changeFragment(this);
    }
}
