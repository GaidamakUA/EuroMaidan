package ua.com.studiovision.euromaidan.json_protocol;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaidamak on 03.12.14.
 */
public enum ErrorMessageCode {
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
    @SerializedName("666")
    WRITING_TO_DB_ERROR
}
