package com.softevol.activity_service_communication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * <b>Before starting this tutorial read carefully
 * <a href=http://developer.android.com/reference/android/app/Service.html>Service ref</a> and
 * <a href = http://developer.android.com/guide/components/services.html>Service guide </a></b>
 * In this example Activity and Service plases in one process.
 * <p/>
 * To provide communication between Service and Activity, both of this instances
 * create {@link android.os.Messenger} from Handler and send own Messengers each
 * other. <br>
 */

abstract public class ActivityServiceCommunicationFragmentActivity extends FragmentActivity {

    private final static String TAG = "ActivityServiceCommunicationActivity";

    public final static String MESSENGER = "com.softevol.activity_service_communication";

    // child class MUST override this field. 
    protected Class<? extends ActivityServiceCommunicationService> mServiceClass;

    /**
     * Note: processResponse startService(...) in onResume() have the same effect as processResponse
     * startService(...) in onCreate() method (from time of receive response from
     * service point of view)
     * <p/>
     * Note: The startService() [is asynchronus or non blocking method] method
     */

    @Override
    protected void onResume() {

        super.onResume();

        if (mServiceClass == null) {
            throw new RuntimeException("DevelopmentTime Exception: to start "
                    + "Service you must define it class in mServiceClass field "
                    + "in your Actvity before call super.onResume()");
        }
        /*connect send intent to connect Service*/
        Intent intent = new Intent(this, mServiceClass);
        intent.putExtra(ActivityServiceCommunicationFragmentActivity.MESSENGER,
                mService2ActivityMessenger);
        startService(intent);
        Log.i(TAG, "Intent was sent");
    }

    @Override
    protected void onPause() {
        /* send message to disconnect Service */
        Message msg = Message.obtain(null, Protocol.ON_PAUSE, 0, 0, null);
        sendMessage(msg);

        mActivity2ServiceMessenger = null;
        Log.i(TAG, "Activity disconnected");
        super.onPause();
    }

    abstract protected void handleMessage(Message msg);

    /**
     * sening message to Service.
     *
     * @return false
     * if message was not sent (while orientation changed, or when service
     * is not created yet)
     */
    final protected boolean sendMessage(Message msg) {

        boolean isSuccessful = false;
        try {
            mActivity2ServiceMessenger.send(msg);
            isSuccessful = true;
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException sendMessage(msg: " + msg + ")");
            mActivity2ServiceMessenger = null;
        } catch (NullPointerException e) {
            Log.i(TAG, "Can not send Message(msg: " + msg + ") to Service. "
                    + "Activity has not connected Services");
        }

        return isSuccessful;
    }

    private class Service2ActivityMessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == Protocol.ON_SERVICE_CONNECTED) {
                mActivity2ServiceMessenger = msg.replyTo;
                Log.i(TAG, "ON_SERVICE_CONNECT reseived");

                /* deny direct access to Messenger by library users */
                msg.replyTo = null;
            }
            ActivityServiceCommunicationFragmentActivity.this.handleMessage(msg);
        }
    }

    private Messenger mService2ActivityMessenger = new Messenger( // TODO simplified names
            new Service2ActivityMessageHandler());
    private Messenger mActivity2ServiceMessenger = null;
}
