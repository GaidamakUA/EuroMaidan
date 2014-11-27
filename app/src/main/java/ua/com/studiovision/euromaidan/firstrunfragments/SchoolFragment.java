package ua.com.studiovision.euromaidan.firstrunfragments;

import android.app.Activity;
import android.app.Fragment;
import android.widget.AutoCompleteTextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment (R.layout.fragment_school)
public class SchoolFragment extends Fragment {
    @ViewById(R.id.countryAutoCompleteTextView)
    AutoCompleteTextView countryAutoCompleteTextView;
    @ViewById(R.id.cityAutoCompleteTextView)
    AutoCompleteTextView cityAutoCompleteTextView;
    @ViewById(R.id.schoolAutoCompleteTextView)
    AutoCompleteTextView schoolAutoCompleteTextView;

    FirstRunFragmentListener firstRunFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        firstRunFragmentListener = (FirstRunFragmentListener) activity;
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
