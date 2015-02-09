package ua.com.studiovision.euromaidan.network.json_protocol;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaidamak on 03.12.14.
 */
public enum MessageCode {
    @SerializedName("1")
    NAME_SHOULD_BE_LONGER_THAN_2_CHARS,
    @SerializedName("2")
    TOKEN_IS_MISSING,
    @SerializedName("3")
    SURNAME_SHOULD_BE_LONGER_THAN_2_CHARS,
    @SerializedName("4")
    WRONG_SEX,
    @SerializedName("5")
    WRONG_RELATIONSHIP_STATUS,
    @SerializedName("6")
    TOKEN_DOES_NOT_EXIST,
    @SerializedName("7")
    DATA_IS_NOT_VALID,
    // Success
    @SerializedName("8")
    REQUEST_HAS_BEEN_SENT,
    // Success
    @SerializedName("9")
    REQUEST_HAS_BEEN_SENT_WAIT_FOR_RESPONSE,
    // Success
    @SerializedName("10")
    YOU_ARE_SUBSCRIBER_ALREADY,
    // Success
    @SerializedName("11")
    FRIEND_HAS_BEEN_ADDED,
    // Success
    @SerializedName("12")
    FRIEND_HAS_BEEN_DELETED,
    @SerializedName("13")
    OPERATION_IN_NOT_POSSIBLE,
    @SerializedName("14")
    YOU_ARE_FRIEND_ALREADY,
    @SerializedName("15")
    USER_ID_DOES_NOT_EXIST,
    @SerializedName("16")
    FILL_ALL_FIELDS,
    @SerializedName("17")
    TYPE_CORRECT_NAME,
    @SerializedName("18")
    TYPE_CORRECT_SURNAME,
    @SerializedName("19")
    PASSWORD_SHOULD_BE_LONGER_THAN_6_CHARS,
    @SerializedName("20")
    INVALID_EMAIL,
    @SerializedName("21")
    SUCH_EMAIL_IS_REGISTRED_ALLREADY,
    @SerializedName("22")
    PASSWORDS_DOES_NOT_MATHC,
    @SerializedName("23")
    INCORRECT_LOGIN,
    @SerializedName("24")
    INCORRECT_PASSWORD,
    // For universities, probably does not working
    @SerializedName("25")
    OKAY,
    @SerializedName("666")
    WRITING_TO_DB_ERROR,
    UNKNOWN
}