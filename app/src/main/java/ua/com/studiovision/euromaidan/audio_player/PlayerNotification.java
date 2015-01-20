package ua.com.studiovision.euromaidan.audio_player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import ua.com.studiovision.euromaidan.R;


/**
 * Helper class for showing and canceling player
 * notifications.
 * <p/>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class PlayerNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "Player";

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     * <p/>
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     * <p/>
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of player notifications. Make
     * sure to follow the
     * <a href="https://developer.android.com/design/patterns/notifications.html">
     * Notification design guidelines</a> when doing so.
     *
     * @see #cancel(Context)
     */
    public static void notify(final Context context, final String author, final String composition,
                              final PlayerActionState playerActionState) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.play);


        final String ticker = composition;
        final String title = author;
        final String text = composition;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(0)

                        // Set required fields, including the small icon, the
                        // notification title, and text.
                .setSmallIcon(R.drawable.play)
                .setContentTitle(title)
                .setContentText(text)

                        // All fields below this line are optional.

                        // Use a default priority (recognized on devices running Android
                        // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        // Provide a large icon, shown with the notification in the
                        // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture)

                        // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                        // Show a number. This is useful when stacking notifications of
                        // a single type.
//                .setNumber(number)

                        // If this notification relates to a past or upcoming event, you
                        // should set the relevant time information using the setWhen
                        // method below. If this call is omitted, the notification's
                        // timestamp will by set to the time at which it was shown.
                        // TODO: Call setWhen if this notification relates to a past or
                        // upcoming event. The sole argument to this method should be
                        // the notification timestamp in milliseconds.
                        //.setWhen(...)

                        // Set the pending intent to be initiated when the user touches
                        // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                AudioActivity_.intent(context).mIsStartedFromNotification(true).get(),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                        // Show expanded text content on devices running Android 4.1 or
                        // later.
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text)
                        .setBigContentTitle(title))

                        // Example additional actions for this notification. These will
                        // only show on devices running Android 4.1 or later, so you
                        // should ensure that the activity in this notification's
                        // content intent provides access to the same actions in
                        // another way.
                .addAction(
                        android.R.drawable.ic_media_previous,
                        null,
                        PendingIntent.getService(
                                context,
                                0,
                                AudioPlayerService_.intent(context).get().setAction(AudioPlayerService.ACTION_PREVIOUS),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(
                        android.R.drawable.ic_media_next,
                        null,
                        PendingIntent.getService(
                                context,
                                0,
                                AudioPlayerService_.intent(context).get().setAction(AudioPlayerService.ACTION_NEXT),
                                PendingIntent.FLAG_UPDATE_CURRENT));
        switch (playerActionState) {
            case PLAY:
                builder.addAction(
                        android.R.drawable.ic_media_play,
                        null,
                        PendingIntent.getService(
                                context,
                                0,
                                AudioPlayerService_.intent(context).get().setAction(AudioPlayerService.ACTION_PLAY),
                                PendingIntent.FLAG_UPDATE_CURRENT));
                break;
            case PAUSE:
                builder.addAction(
                        android.R.drawable.ic_media_pause,
                        null,
                        PendingIntent.getService(
                                context,
                                0,
                                AudioPlayerService_.intent(context).get().setAction(AudioPlayerService.ACTION_PAUSE),
                                PendingIntent.FLAG_UPDATE_CURRENT));

                break;
        }

        notify(context, builder.build());
    }

    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_TAG, 0);
    }

    public enum PlayerActionState {
        PLAY,
        PAUSE
    }
}