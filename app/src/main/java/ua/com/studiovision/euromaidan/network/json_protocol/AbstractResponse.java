package ua.com.studiovision.euromaidan.network.json_protocol;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractResponse<T extends AbstractResponse<T>> {
    public QueryStatus status;
    public ErrorMessageCode message;

    public enum QueryStatus {
        @SerializedName("success")
        SUCCESS,
        @SerializedName("error")
        ERROR
    }
}
