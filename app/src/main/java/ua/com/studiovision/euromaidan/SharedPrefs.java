package ua.com.studiovision.euromaidan;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SharedPrefs {
    @DefaultString("DEADBEEF")
    String getToken();
    @DefaultLong(-1l)
    long getUserId();
}
