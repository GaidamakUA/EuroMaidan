package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.Gender;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.GetSettingsResponseContent;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.RelationshipStatus;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SettingsParamsBuilder;

@EFragment(R.layout.fragment_user_detailes)
public class UserDetailsFragment extends Fragment {
    @ViewById(R.id.name_details_edit_text)
    EditText userNameEditText;
    @ViewById(R.id.surname_details_edit_text)
    EditText userSurnameEditText;
    @ViewById(R.id.gender_radio_group)
    RadioGroup genderGroup;
    @ViewById(R.id.female_radio_button)
    RadioButton femaleRadioButton;
    @ViewById(R.id.male_radio_button)
    RadioButton maleRadioButton;
    @ViewById(R.id.birthday_button)
    Button birthdayButton;
    @ViewById(R.id.native_city_edit_text)
    EditText nativeCityEditText;
    @ViewById(R.id.relationship_radio_group)
    RadioGroup relationshipRadioGroup;
    @ViewById(R.id.save_button)
    Button saveButton;

    private final String TAG = "User details fragment";

//    SettingsFragmentListener settingsFragmentListener;

    // TODO Button should be updated with value from calendar to fallow MVC pattern
    final static Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sendDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    SimpleDateFormat displayDateFormat = new SimpleDateFormat("d MMMM yyyy");

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        settingsFragmentListener = (SettingsFragmentListener) activity;
    }

    @AfterViews
    void init() {
        femaleRadioButton.setTextColor(getActivity().getBaseContext().getResources().getColorStateList(R.color.sex_rb_text_colors));
        maleRadioButton.setTextColor(getActivity().getBaseContext().getResources().getColorStateList(R.color.sex_rb_text_colors));
    }

    @Click(R.id.birthday_button)
    void pickBirthDate() {
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

    @Click(R.id.save_button)
    void save() {
        final String name = userNameEditText.getText().toString();
        final String surname = userSurnameEditText.getText().toString();
        final Gender gender = genderGroup.getCheckedRadioButtonId() == R.id.female_radio_button ? Gender.FEMALE : Gender.MALE;
        final String dob;
        final String city = nativeCityEditText.getText().toString();

        if (!birthdayButton.getText().toString().equals(getResources().getString(R.string.pickBirthDate))) {
            dob = sendDateFormat.format(calendar.getTime());
            Log.v(TAG, "Date of birth=" + dob);
        } else {
            Toast.makeText(getActivity().getBaseContext(), "Введите пожалуйста дату рождения", Toast.LENGTH_SHORT).show();
            return;
        }

        final RelationshipStatus status = RelationshipStatus
                .getRelationshipStatus(relationshipRadioGroup.getCheckedRadioButtonId());

        Log.v(TAG, "UserDetailsFragment.save(" + ")");
        SettingsParamsBuilder builder = new SettingsParamsBuilder();
        builder.setName(name)
                .setSurname(surname)
                .setGender(gender)
                .setRelationshipStatus(status)
                .setNativeCity(city)
                .setDob(dob);
        ((SettingsFragmentListener) getActivity())
                .sendProfileDataToServer(builder.createRequestParams());
    }

    public void setData(GetSettingsResponseContent content) {
        userNameEditText.setText(content.user_name);
        userSurnameEditText.setText(content.user_surname);
        switch (content.user_gender) {
            case FEMALE:
                Log.v(TAG, "Go to kitchen.");
                maleRadioButton.setChecked(true);
                break;
            case MALE:
                Log.v(TAG, "Have a beer.");
                femaleRadioButton.setChecked(true);
                break;
            case UNKNOWN:
                Log.v(TAG, "Not a fan of futa");
                break;
            default:
                throw new IllegalArgumentException("Your sex is sick");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(content.date_of_birth);
            calendar.setTime(date);
            birthdayButton.setText(displayDateFormat.format(date));
        } catch (ParseException e) {
            Log.w(TAG, "", e);
        }
        nativeCityEditText.setText(content.native_city);

        relationshipRadioGroup.check(content.family_status.getId());
    }
}
