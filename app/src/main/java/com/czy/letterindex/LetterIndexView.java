package com.czy.letterindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：叶应是叶
 * 时间：2017/8/20 11:38
 * 描述：
 */
public class LetterIndexView extends View {

    public interface OnTouchingLetterChangedListener {

        void onHit(String letter);

        void onCancel();
    }

    private OnTouchingLetterChangedListener touchingLetterChangedListener;

    private Paint paint;

    private boolean hit;

    private final String[] letters = {"↑", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "#"};

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#565656"));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hit = true;
                onHit(event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onHit(event.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                hit = false;
                if (touchingLetterChangedListener != null) {
                    touchingLetterChangedListener.onCancel();
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hit) {
            //字母索引条背景色
            canvas.drawColor(Color.parseColor("#bababa"));
        }
        float letterHeight = ((float) getHeight()) / letters.length;
        float width = getWidth();
        float textSize = letterHeight * 5 / 7;
        paint.setTextSize(textSize);
        for (int i = 0; i < letters.length; i++) {
            canvas.drawText(letters[i], width / 2, letterHeight * i + textSize, paint);
        }
    }

    private void onHit(float offset) {
        if (hit && touchingLetterChangedListener != null) {
            int index = (int) (offset / getHeight() * letters.length);
            index = Math.max(index, 0);
            index = Math.min(index, letters.length - 1);
            touchingLetterChangedListener.onHit(letters[index]);
        }
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.touchingLetterChangedListener = onTouchingLetterChangedListener;
    }

}