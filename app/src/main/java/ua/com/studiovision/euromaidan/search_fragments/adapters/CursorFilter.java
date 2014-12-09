package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.database.Cursor;
import android.widget.Filter;

class CursorFilter<C extends Cursor> extends Filter {

    CursorFilterClient<C> mClient;

    interface CursorFilterClient<C> {
        CharSequence convertToString(C cursor);
        C runQueryOnBackgroundThread(CharSequence constraint);
        C getCursor();
        void changeCursor(C cursor);
    }

    CursorFilter(CursorFilterClient<C> client) {
        mClient = client;
    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return mClient.convertToString((C) resultValue);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        C cursor = mClient.runQueryOnBackgroundThread(constraint);

        FilterResults results = new FilterResults();
        if (cursor != null) {
            results.count = cursor.getCount();
            results.values = cursor;
        } else {
            results.count = 0;
            results.values = null;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        C oldCursor = mClient.getCursor();

        if (results.values != null && results.values != oldCursor) {
            mClient.changeCursor((C) results.values);
        }
    }
}