package com.yimu.uireview.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.yimu.uireview.R;


/**
 * Created by linwei on 15-8-19.
 */
public class TextViewScanner implements UiReview.Scanner {

    private static final String TAG = TextViewScanner.class.getSimpleName();
    private boolean mShowBorder;
    private boolean mShowDp;
    private boolean mShowSp;
    private boolean mShowColor;

    @Override
    public void init(Context context) {
        // better load from SharedPreference
        mShowBorder = true;
        mShowDp = false;
        mShowSp = true;
        mShowColor = false;
    }

    @Override
    public boolean filter(View view) {
        return TextView.class.isInstance(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void handle(View view) {
        final TextView textView = (TextView) view;
        if (null == textView) {
            return;
        }
        Drawable drawable = (Drawable) textView.getTag(R.id.uireview_textview_tag_id);
        if (drawable == null) {
            textView.getOverlay()
                    .clear();
            drawable = buildDrawable(textView);
            textView.getOverlay()
                    .add(drawable);
            textView.setTag(R.id.uireview_textview_tag_id, drawable);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private Drawable buildDrawable(final TextView textView) {
        return new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                String text = getTextViewInfo(textView);
                Rect canvasBounds = canvas.getClipBounds();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                //border
                if (mShowBorder) {
                    paint.setStrokeWidth(1);
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(canvasBounds, paint);
                }
                //text
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.FILL);
                paint.setAlpha(180);
                paint.setTextSize(20);
                Rect textBounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), textBounds);
                final int padding = 2;
                canvas.drawRect(canvasBounds.left, canvasBounds.top,
                        canvasBounds.left + textBounds.width() + padding * 2,
                        canvasBounds.top + textBounds.height() + padding * 2,
                        paint);

                paint.setColor(Color.WHITE);
                canvas.drawText(text, canvasBounds.left, canvasBounds.top + textBounds.height(), paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
    }

    private String getTextViewInfo(TextView textView) {
        StringBuilder sb = new StringBuilder();
        float pixelSize = textView.getTextSize();
        textView.getCurrentTextColor();
        String split = " ";
        if (mShowDp) {
            sb.append(Utils.px2dip(textView.getContext(), pixelSize));
            sb.append("dp");
        }
        if (mShowSp) {
            if (sb.length() > 0) {
                sb.append(split);
            }
            sb.append(Utils.px2sp(textView.getContext(), pixelSize));
            sb.append("sp");
        }
        if(mShowColor){
            if(sb.length() > 0){
                sb.append(split);
            }
            sb.append("#");
            sb.append(Integer.toHexString(textView.getCurrentTextColor()));
        }
        return sb.toString();
    }

}
