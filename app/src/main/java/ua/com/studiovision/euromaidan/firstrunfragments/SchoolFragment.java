package ua.com.studiovision.euromaidan.firstrunfragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.provider.country.CountryColumns;
import ua.com.studiovision.euromaidan.provider.country.CountrySelection;

@EFragment (R.layout.fragment_school)
public class SchoolFragment extends Fragment {
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
        countryCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), android.R.layout.simple_dropdown_item_1line, null,
                new String[] { CountryColumns.COUNTRY_NAME },
                new int[] {android.R.id.text1},
                0);
        countryAutoCompleteTextView.setAdapter(countryCursorAdapter);

        countryCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                return getCursor(str);
            } });

        countryCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            public CharSequence convertToString(Cursor cur) {
                return cur.getString(cur.getColumnIndex(CountryColumns.COUNTRY_NAME));
            }});
    }

    private Cursor getCursor(CharSequence charSequence){
        CountrySelection countrySelection = new CountrySelection();
        countrySelection.countryName(charSequence.toString());
        return getActivity().getBaseContext().getContentResolver().query(CountryColumns.CONTENT_URI,CountryColumns.ALL_COLUMNS,
                countrySelection.sel(), countrySelection.args(),null);
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
