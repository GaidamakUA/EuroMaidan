package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ua.com.studiovision.euromaidan.R;

import java.util.Calendar;
import java.util.Locale;

@EFragment(R.layout.fragment_user_detailes)
public class UserDetailsFragment extends Fragment{
    @ViewById(R.id.rb_female)
    RadioButton rbFemale;
    @ViewById(R.id.rb_male)
    RadioButton rbMale;
    @ViewById(R.id.btn_birthday)
    Button birthdayButton;

    private final String TAG = "User details fragment";

    final static Calendar calendar = Calendar.getInstance();

    @AfterViews
    void init(){
        rbFemale.setTextColor(getActivity().getBaseContext().getResources().getColorStateList(R.color.sex_rb_text_colors));
        rbMale.setTextColor(getActivity().getBaseContext().getResources().getColorStateList(R.color.sex_rb_text_colors));
    }

    @Click(R.id.btn_birthday)
    void pickBirthDate(){
        Log.v(TAG, "Pick date pressed");
        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                birthdayButton.setText(dayOfMonth + "  " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + "  " + year);
            }
        };

        new DatePickerDialog(getActivity(), mDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
    }
}
