package ua.com.studiovision.euromaidan.process_strategies;

import android.content.Context;
import android.widget.Toast;

import ua.com.studiovision.euromaidan.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.json_protocol.AbstractResponse;

public abstract class AbstractProcessResponseStrategy<T extends AbstractRequest, S extends AbstractResponse> {

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
        // TODO write string resources
        switch (response.message) {
            case NAME_SHOULD_BE_LONGER_THAN_2_CHARS:
                break;
            case TOKEN_IS_MISSING:
                break;
            case SURNAME_SHOULD_BE_LONGER_THAN_2_CHARS:
                break;
            case WRONG_SEX:
                break;
            case WRONG_RELATIONSHIP_STATUS:
                break;
            case TOKEN_DOES_NOT_EXIST:
                break;
            case WRITING_TO_DB_ERROR:
                break;
            default:
                throw new IllegalArgumentException("Unexpected error message");
        }
        /* XXX Test only
        errorText = response.message.toString();
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show();*/
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

    ;
}
