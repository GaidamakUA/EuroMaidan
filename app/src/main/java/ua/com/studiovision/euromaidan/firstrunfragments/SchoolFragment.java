package ua.com.studiovision.euromaidan.firstrunfragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.provider.country.CountryColumns;

@EFragment (R.layout.fragment_school)
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        firstRunFragmentListener = (FirstRunFragmentListener) activity;


    }

    @Override
    public void onStart() {
        super.onStart();
        countryCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_dropdown_item_1line,
                null,
                new String[] { CountryColumns.COUNTRY_NAME },
                new int[] {android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        countryAutoCompleteTextView.setAdapter(countryCursorAdapter);

        countryCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                if (str == null || str.length() < 1)
                    return null;
                firstRunFragmentListener.tryRequestCountries(str.toString());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(CountryColumns.COUNTRY_NAME);
                stringBuilder.append(" LIKE '%");
                stringBuilder.append(str.toString());
                stringBuilder.append("%' OR ");
                stringBuilder.append(CountryColumns.COUNTRY_NAME);
                stringBuilder.append(" LIKE '%");
                stringBuilder.append(Character.toUpperCase(str.charAt(0)));
                stringBuilder.append(str.subSequence(1, str.length()));
                stringBuilder.append("%'");
                Log.v(TAG, "str=" + str + "; select=" + stringBuilder.toString());
                Cursor cursor =  getActivity().getBaseContext().getContentResolver()
                        .query(CountryColumns.CONTENT_URI, CountryColumns.ALL_COLUMNS,
                                stringBuilder.toString(), null, null);
                Log.v(TAG, "Cursor=" + cursor.getCount());
                return cursor;
            } });

        countryCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            public CharSequence convertToString(Cursor cur) {
                return cur.getString(cur.getColumnIndex(CountryColumns.COUNTRY_NAME));
            }});
    }

    @Click(R.id.saveButton)
    void onSaveButtonClick() {
        // XXX test content
        firstRunFragmentListener.tryRequestCountries(countryAutoCompleteTextView.getText().toString());
    }
    @Click(R.id.skip_button)
    void skip() {
        firstRunFragmentListener.changeFragment(this);
    }
}
