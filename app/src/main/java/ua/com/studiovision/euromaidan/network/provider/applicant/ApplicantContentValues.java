package ua.com.studiovision.euromaidan.network.provider.applicant;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code applicant} table.
 */
public class ApplicantContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ApplicantColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, ApplicantSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Applicant id from server
     */
    public ApplicantContentValues putApplicantId(long value) {
        mContentValues.put(ApplicantColumns.APPLICANT_ID, value);
        return this;
    }



    /**
     * Applicant name
     */
    public ApplicantContentValues putApplicantName(String value) {
        if (value == null) throw new IllegalArgumentException("value for applicantName must not be null");
        mContentValues.put(ApplicantColumns.APPLICANT_NAME, value);
        return this;
    }



    /**
     * Applicant surname
     */
    public ApplicantContentValues putApplicantSurname(String value) {
        if (value == null) throw new IllegalArgumentException("value for applicantSurname must not be null");
        mContentValues.put(ApplicantColumns.APPLICANT_SURNAME, value);
        return this;
    }



    /**
     * Applicant avatar
     */
    public ApplicantContentValues putApplicantAvatar(String value) {
        if (value == null) throw new IllegalArgumentException("value for applicantAvatar must not be null");
        mContentValues.put(ApplicantColumns.APPLICANT_AVATAR, value);
        return this;
    }


}
