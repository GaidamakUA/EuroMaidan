package ua.com.studiovision.euromaidan.network.provider.applicant;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.studiovision.euromaidan.network.provider.base.AbstractSelection;

/**
 * Selection for the {@code applicant} table.
 */
public class ApplicantSelection extends AbstractSelection<ApplicantSelection> {
    @Override
    public Uri uri() {
        return ApplicantColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ApplicantCursor} object, which is positioned before the first entry, or null.
     */
    public ApplicantCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ApplicantCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public ApplicantCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public ApplicantCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ApplicantSelection id(long... value) {
        addEquals("applicant." + ApplicantColumns._ID, toObjectArray(value));
        return this;
    }


    public ApplicantSelection applicantId(long... value) {
        addEquals(ApplicantColumns.APPLICANT_ID, toObjectArray(value));
        return this;
    }

    public ApplicantSelection applicantIdNot(long... value) {
        addNotEquals(ApplicantColumns.APPLICANT_ID, toObjectArray(value));
        return this;
    }

    public ApplicantSelection applicantIdGt(long value) {
        addGreaterThan(ApplicantColumns.APPLICANT_ID, value);
        return this;
    }

    public ApplicantSelection applicantIdGtEq(long value) {
        addGreaterThanOrEquals(ApplicantColumns.APPLICANT_ID, value);
        return this;
    }

    public ApplicantSelection applicantIdLt(long value) {
        addLessThan(ApplicantColumns.APPLICANT_ID, value);
        return this;
    }

    public ApplicantSelection applicantIdLtEq(long value) {
        addLessThanOrEquals(ApplicantColumns.APPLICANT_ID, value);
        return this;
    }

    public ApplicantSelection applicantName(String... value) {
        addEquals(ApplicantColumns.APPLICANT_NAME, value);
        return this;
    }

    public ApplicantSelection applicantNameNot(String... value) {
        addNotEquals(ApplicantColumns.APPLICANT_NAME, value);
        return this;
    }

    public ApplicantSelection applicantNameLike(String... value) {
        addLike(ApplicantColumns.APPLICANT_NAME, value);
        return this;
    }

    public ApplicantSelection applicantSurname(String... value) {
        addEquals(ApplicantColumns.APPLICANT_SURNAME, value);
        return this;
    }

    public ApplicantSelection applicantSurnameNot(String... value) {
        addNotEquals(ApplicantColumns.APPLICANT_SURNAME, value);
        return this;
    }

    public ApplicantSelection applicantSurnameLike(String... value) {
        addLike(ApplicantColumns.APPLICANT_SURNAME, value);
        return this;
    }

    public ApplicantSelection applicantAvatar(String... value) {
        addEquals(ApplicantColumns.APPLICANT_AVATAR, value);
        return this;
    }

    public ApplicantSelection applicantAvatarNot(String... value) {
        addNotEquals(ApplicantColumns.APPLICANT_AVATAR, value);
        return this;
    }

    public ApplicantSelection applicantAvatarLike(String... value) {
        addLike(ApplicantColumns.APPLICANT_AVATAR, value);
        return this;
    }
}
