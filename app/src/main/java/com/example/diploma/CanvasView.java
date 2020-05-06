package com.example.diploma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

        // we set a new Path
        mPath = new Path();
        paths.add(mPath);

        // and we set a new Paint with the desired attributes
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

        // and we set a new Paint with the desired attributes
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

        // and we set a new Paint with the desired attributes
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
        Toast.makeText(getContext(), "h: "+mCanvas.getHeight()+"\nw: "+mCanvas.getWidth(), Toast.LENGTH_SHORT).show();
    }

    public void clearBitmap() {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }


    public void setBackgroundImage(Bitmap backgroundImage) {
        this.backgroundImage = backgroundImage;
        if (backgroundImage != null) {
            DrawScaledBitmap(mCanvas);
//            Rect src = new Rect(0, 0, backgroundImage.getWidth() - 1, backgroundImage.getHeight() - 1);
//            Rect dest;
//            if (backgroundImage.getWidth() > backgroundImage.getHeight())
//                dest = new Rect(0, 0, mCanvas.getHeight(), mCanvas.getHeight());
//            else
//                dest = new Rect(0, 0, mCanvas.getWidth(), mCanvas.getWidth());
//            mCanvas.drawBitmap(backgroundImage, src, dest, null);


//            if(backgroundImage.getWidth()>backgroundImage.getHeight()) {
//                percentWidth=100;
//                percentHeight=(backgroundImage.getHeight()*100)/backgroundImage.getWidth();
//                int imgHeight=(mCanvas.getHeight()*percentHeight)/100;
//                int imgWidth=mCanvas.getWidth();
//                Rect src = new Rect(0, 0, imgWidth, imgHeight);
//                Rect dest=new Rect(0,0, mCanvas.getWidth(), mCanvas.getHeight());
//                mCanvas.drawBitmap(backgroundImage, src, dest, null);
//                Toast.makeText(getContext(), "canvas h: "+mCanvas.getHeight()
//                        +"\ncanvas w: "
//                        + mCanvas.getWidth()
//                        +"\nimg h: "
//                        + imgHeight
//                        +"\nimg w: "
//                        +imgWidth, Toast.LENGTH_LONG).show();
        }
    }

    void DrawScaledBitmap(Canvas tmpCanvas)
    {
        final int maxSize = tmpCanvas.getWidth();
        int outWidth;
        int outHeight;
        int inWidth = backgroundImage.getWidth();
        int inHeight = backgroundImage.getHeight();
        int marginLeft=0;
        int marginTop=0;
        if (inWidth > inHeight) {
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
            marginTop=(outWidth-outHeight)/2;

        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
            marginLeft=(outHeight-outWidth)/2;
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(backgroundImage, outWidth, outHeight, false);
        tmpCanvas.drawBitmap(resizedBitmap, marginLeft, marginTop, null);
      //  tmpCanvas.drawBitmap(mBitmap, 0, 0, null);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // your Canvas will draw onto the defined Bitmap
        width = w;
        height = h;
        if (!isEditable) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        if (backgroundImage != null) {

            DrawScaledBitmap(canvas);
//            final int maxSize = mCanvas.getWidth();
//            int outWidth;
//            int outHeight;
//            int inWidth = backgroundImage.getWidth();
//            int inHeight = backgroundImage.getHeight();
//            int marginLeft=0;
//            int marginTop=0;
//            if (inWidth > inHeight) {
//                outWidth = maxSize;
//                outHeight = (inHeight * maxSize) / inWidth;
//                marginTop=(outWidth-outHeight)/2;
//
//            } else {
//                outHeight = maxSize;
//                outWidth = (inWidth * maxSize) / inHeight;
//                marginLeft=(outHeight-outWidth)/2;
//            }
//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(backgroundImage, outWidth, outHeight, false);
//            mCanvas.drawBitmap(resizedBitmap, marginLeft, marginTop, null);

        }

        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }


    public void clearCanvas() {
        for (int i = 0; i < paths.size(); i++)
            paths.get(i).reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    //override the onTouchEvent
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