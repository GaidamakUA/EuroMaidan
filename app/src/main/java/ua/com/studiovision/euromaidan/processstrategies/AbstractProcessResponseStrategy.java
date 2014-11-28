package ua.com.studiovision.euromaidan.processstrategies;

import android.content.ContentResolver;

import ua.com.studiovision.euromaidan.jsonprotocol.AbstractGetProtocol;

public abstract class AbstractProcessResponseStrategy {

    protected ContentResolver resolver;
    protected AbstractGetProtocol.AbstractArrayRequest request;

    public abstract void processResponse(AbstractGetProtocol.Response response);

    public AbstractGetProtocol.AbstractArrayRequest getRequest() {
        return request;
    }
}
