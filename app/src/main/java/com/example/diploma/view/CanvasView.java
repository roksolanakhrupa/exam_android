package com.example.diploma.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Bitmap backgroundImage = null;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    private ArrayList<Paint> paints = new ArrayList<>();
    private ArrayList<Path> paths = new ArrayList<>();

    private int prevColor = Color.BLACK;
    private int prevWidth = 4;

    public boolean isEditable = false;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        mPath = new Path();
        paths.add(mPath);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        paints.add(mPaint);


    }

    public void changeColor(int newColor) {
        prevColor = newColor;

        mPath = new Path();
        paths.add(mPath);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(prevColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(prevWidth);
        paints.add(mPaint);

    }

    public void changeWidth(int newWidth) {
        prevWidth = newWidth;

        mPath = new Path();
        paths.add(mPath);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(prevColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(prevWidth);
        paints.add(mPaint);

    }

    public Bitmap getBitmap() {

        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }

    public void setBitmap(Bitmap b) {
        mBitmap = b;
        Bitmap copy = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
        mCanvas = new Canvas(copy);
    }


    public void setBackgroundImage(Bitmap backgroundImage) {
        this.backgroundImage = backgroundImage;
        if (backgroundImage != null) {
            DrawScaledBitmap(mCanvas);
        }
    }

    void DrawScaledBitmap(Canvas tmpCanvas) {
        final int maxSize = tmpCanvas.getWidth();
        int outWidth;
        int outHeight;
        int inWidth = backgroundImage.getWidth();
        int inHeight = backgroundImage.getHeight();
        int marginLeft = 0;
        int marginTop = 0;
        if (inWidth > inHeight) {
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
            marginTop = (outWidth - outHeight) / 2;

        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
            marginLeft = (outHeight - outWidth) / 2;
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(backgroundImage, outWidth, outHeight, false);
        tmpCanvas.drawBitmap(resizedBitmap, marginLeft, marginTop, null);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        if (!isEditable) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (backgroundImage != null) {
            DrawScaledBitmap(canvas);
        }

        canvas.drawBitmap(mBitmap, 0, 0, null);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
    }



    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }



    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}