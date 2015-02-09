package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.util.Log;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

public abstract class AbstractProcessResponseStrategy<T extends AbstractRequest, S extends AbstractResponse> {

    private final String TAG = "AbstractProcessResponseStrategy";

    protected Context context;
    protected T request;
    protected Class<S> responseClass;

    public AbstractProcessResponseStrategy(Context context, Class<S> responseClass) {
        this.context = context;
        this.responseClass = responseClass;
    }

    public AbstractRequest getRequest() {
        return request;
    }

    public void handleResponse(S response) {
        if (response.status == AbstractResponse.QueryStatus.SUCCESS) {
            onResponse(response);
        } else if (response.status == AbstractResponse.QueryStatus.ERROR) {
            onError(response);
        } else {
            throw new IllegalArgumentException("Response status was neither successful nor error");
        }
    }

    /**
     * You have to override this method in order to handle error manually.
     *
     * @param response
     */
    protected void onError(S response) {
        String errorText = "text that no one will ever see";
        switch (response.message) {
            case NAME_SHOULD_BE_LONGER_THAN_2_CHARS:
            case TOKEN_IS_MISSING:
            case SURNAME_SHOULD_BE_LONGER_THAN_2_CHARS:
            case WRONG_SEX:
            case WRONG_RELATIONSHIP_STATUS:
            case TOKEN_DOES_NOT_EXIST:
            case WRITING_TO_DB_ERROR:
            case DATA_IS_NOT_VALID:
            case OPERATION_IN_NOT_POSSIBLE:
            case YOU_ARE_FRIEND_ALREADY:
            case USER_ID_DOES_NOT_EXIST:
            case FILL_ALL_FIELDS:
            case TYPE_CORRECT_NAME:
            case TYPE_CORRECT_SURNAME:
            case PASSWORD_SHOULD_BE_LONGER_THAN_6_CHARS:
            case INVALID_EMAIL:
            case SUCH_EMAIL_IS_REGISTRED_ALLREADY:
            case PASSWORDS_DOES_NOT_MATHC:
            case INCORRECT_LOGIN:
            case INCORRECT_PASSWORD:
                Log.v(TAG, response.message.toString());
                break;
            default:
                throw new IllegalArgumentException("Unexpected error message=" + response.message);
        }
    }

    /**
     * You have to override this method in order to handle success manually.
     *
     * @param response
     */
    protected void onResponse(S response) {
    }

    public Class<S> getResponseClass() {
        return responseClass;
    }
}
