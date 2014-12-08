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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.Genders;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.RelationshipStatus;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SettingsParamsBuilder;

@EFragment(R.layout.fragment_user_detailes)
public class UserDetailsFragment extends Fragment {
    @ViewById(R.id.nameDetails)
    EditText userName;
    @ViewById(R.id.surnameDetails)
    EditText userSurname;
    @ViewById(R.id.genderGroup)
    RadioGroup genderGroup;
    @ViewById(R.id.rb_female)
    RadioButton rbFemale;
    @ViewById(R.id.rb_male)
    RadioButton rbMale;
    @ViewById(R.id.btn_birthday)
    Button birthdayButton;
    @ViewById(R.id.native_city)
    EditText nativeCity;
    @ViewById(R.id.relationshipGroup)
    RadioGroup relationshipGroup;
    @ViewById(R.id.save_button)
    Button saveButton;

    private final String TAG = "User details fragment";

    SettingsFragmentListener settingsFragmentListener;

    final static Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        settingsFragmentListener = (SettingsFragmentListener) activity;
    }

    @AfterViews
    void init() {
        rbFemale.setTextColor(getActivity().getBaseContext().getResources().getColorStateList(R.color.sex_rb_text_colors));
        rbMale.setTextColor(getActivity().getBaseContext().getResources().getColorStateList(R.color.sex_rb_text_colors));
    }

    @Click(R.id.btn_birthday)
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
        final String name = userName.getText().toString();
        final String surname = userSurname.getText().toString();
        final Genders gender = genderGroup.getCheckedRadioButtonId() == R.id.rb_female ? Genders.FEMALE : Genders.MALE;
        final String dob;
        final String city = nativeCity.getText().toString();

        if (!birthdayButton.getText().toString().equals(getResources().getString(R.string.pickBirthDate))) {
            dob = sdFormat.format(calendar.getTime());
        } else {
            Toast.makeText(getActivity().getBaseContext(), "Введите пожалуйста дату рождения", Toast.LENGTH_SHORT).show();
            return;
        }

        final RelationshipStatus status;

        switch (relationshipGroup.getCheckedRadioButtonId()) {
            case R.id.relationSingle:
                status = RelationshipStatus.SINGLE;
                break;
            case R.id.relationDating:
                status = RelationshipStatus.DATING;
                break;
            case R.id.relationEngaged:
                status = RelationshipStatus.ENGAGED;
                break;
            case R.id.relationMarried:
                status = RelationshipStatus.MARRIED;
                break;
            case R.id.relationInLove:
                status = RelationshipStatus.IN_LOVE;
                break;
            case R.id.relationComplicated:
                status = RelationshipStatus.COMPLICATED;
                break;
            case R.id.relationActiveSearch:
                status = RelationshipStatus.IN_ACTIVE_SEARCH;
                break;
            default:
                status = RelationshipStatus.NONE;
        }

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
}
