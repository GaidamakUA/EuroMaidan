package ua.com.studiovision.euromaidan;

import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;

public class ImageProcessor {
    private ImageProcessor() {}

    public static Bitmap getRoundedCornersImage(Bitmap source) {

        if (source == null) {
            //we cant proccess null image, go out
            return null;
        }
        final int sourceWidth = source.getWidth();
        final int sourceHeight = source.getHeight();
        final Bitmap output = Bitmap.createBitmap(sourceWidth, sourceHeight, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = 0xFF000000;
        final Paint paint = new Paint();
        paint.setColor(color);

        final Rect rect = new Rect(0, 0, sourceWidth, sourceHeight);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, 90, 90, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);

        return output;
    }
}