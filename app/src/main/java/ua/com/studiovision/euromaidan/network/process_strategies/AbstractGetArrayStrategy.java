package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.activities.FirstRunActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;

/**
 * Created by gaidamak on 08.12.14.
 */
public abstract class AbstractGetArrayStrategy extends AbstractProcessResponseStrategy
        <AbstractGetArrayProtocol.AbstractArrayRequest, AbstractGetArrayProtocol.AbstractArrayResponse> {
    protected String namePart;
    protected Long parentItemId;

    public AbstractGetArrayStrategy(Context context, Message msg) {
        super(context, AbstractGetArrayProtocol.AbstractArrayResponse.class);
        Bundle bundle = msg.getData();
        namePart = bundle.getString(FirstRunActivity.NAME_PART);
        parentItemId = bundle.getLong(FirstRunActivity.PARENT_ITEM_ID);
    }

    @Override
    public final void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse response) {
        if (response.content == null || response.content.array == null)
            return;
        AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem[] array = response.content.array;
        onResponse(array);
    }

    abstract void onResponse(AbstractGetArrayProtocol.AbstractArrayResponse.AbstractItem[] array);
}
