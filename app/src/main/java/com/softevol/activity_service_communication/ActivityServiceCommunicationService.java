package com.softevol.activity_service_communication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

abstract public class ActivityServiceCommunicationService extends Service {

    private final static String TAG = "ActivityServiceCommunicationService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mService2ActivityMessenger = intent
                .getParcelableExtra(ActivityServiceCommunicationActivity.MESSENGER);
        Message msg = Message.obtain();

        return onStartCommand(intent, flags, startId, msg);
    }

    /*
     * Allows to send message with some data to Activity in ON_SERVICE_CONNECT message.
     * You can NOT use "replyTo" and "what" fields of this message
     */
    final protected int onStartCommand(Intent intent, int flags, int startId, Message msg) {

        if (msg.what != 0) {
            throw new RuntimeException("DevelopmentTimeException: do not use msg.what field");
        }
        if (msg.replyTo != null) {
            throw new RuntimeException("DevelopmentTimeException: do not use msg.replyTo field");
        }
        mService2ActivityMessenger = intent
                .getParcelableExtra(ActivityServiceCommunicationActivity.MESSENGER);

        //prepare message to send
        msg.what = Protocol.ON_SERVICE_CONNECTED;
        msg.replyTo = mActivity2ServiceMessenger;
        Log.i(TAG, "Send initialise message to Activity");
        sendMessage(msg);

        return START_NOT_STICKY;
    }

    abstract protected void handleMessage(Message msg);

    /**
     * Its strongly recommend to check result of sending message! If you 
     * try to send message to Activity, when its disconnected, you may
     * lose your data.
     */
    final protected boolean sendMessage(Message msg) {
        boolean isSuccessful = false;
        try {
            mService2ActivityMessenger.send(msg);
            isSuccessful = true;
        } catch (RemoteException e) {
            Log.d(TAG, "RemoteException sendMessage(msg: " + msg + ")");
            mService2ActivityMessenger = null; 
        } catch (NullPointerException e) {
            Log.d(TAG, "Can not send Message(msg: " + msg + ") to Activity. "
                    + "Service has not connected Acivities");
        }

        return isSuccessful;
    }

    private class Activity2ServiceMessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Protocol.ON_PAUSE) {
                mService2ActivityMessenger = null;     
                Log.i(TAG, "Service disconnected");
            }

            ActivityServiceCommunicationService.this.handleMessage(msg);
        }
    }

    private Messenger mService2ActivityMessenger = null;
    private Messenger mActivity2ServiceMessenger = new Messenger(
            new Activity2ServiceMessageHandler());
}

