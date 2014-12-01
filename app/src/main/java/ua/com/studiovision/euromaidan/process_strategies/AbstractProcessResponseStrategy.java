package ua.com.studiovision.euromaidan.process_strategies;

import android.content.ContentResolver;

import ua.com.studiovision.euromaidan.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.json_protocol.AbstractResponse;

public abstract class AbstractProcessResponseStrategy <T extends AbstractResponse> {

    protected ContentResolver resolver;
    protected AbstractRequest request;
    protected Class<T> responseClass;

    public AbstractRequest getRequest() {
        return request;
    }

    public abstract void processResponse(T response);
    public Class<T> getResponseClass() {
        return responseClass;
    };
}
