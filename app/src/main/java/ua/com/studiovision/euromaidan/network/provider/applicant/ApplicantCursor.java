package ua.com.studiovision.euromaidan.network.provider.applicant;

import java.util.Date;

import android.database.Cursor;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code applicant} table.
 */
public class ApplicantCursor extends AbstractCursor {
    public ApplicantCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Applicant id from server
     */
    public long getApplicantId() {
        return getLongOrNull(ApplicantColumns.APPLICANT_ID);
    }

    /**
     * Applicant name
     * Cannot be {@code null}.
     */
    public String getApplicantName() {
        Integer index = getCachedColumnIndexOrThrow(ApplicantColumns.APPLICANT_NAME);
        return getString(index);
    }

    /**
     * Applicant surname
     * Cannot be {@code null}.
     */
    public String getApplicantSurname() {
        Integer index = getCachedColumnIndexOrThrow(ApplicantColumns.APPLICANT_SURNAME);
        return getString(index);
    }

    /**
     * Applicant avatar
     * Cannot be {@code null}.
     */
    public String getApplicantAvatar() {
        Integer index = getCachedColumnIndexOrThrow(ApplicantColumns.APPLICANT_AVATAR);
        return getString(index);
    }
}
