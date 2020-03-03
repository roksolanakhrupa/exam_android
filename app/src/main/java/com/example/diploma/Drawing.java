//package com.example.diploma;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.drawable.BitmapDrawable;
//import android.media.MediaScannerConnection;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//
//public class Drawing extends AppCompatActivity {
//
//
//    ImageView imageResult;
//    Bitmap bitmapMaster;
//    Canvas canvasMaster;
//
//    int prvX, prvY;
//
//    Paint paintDraw;
//
//    ImageView imageView;
//
//    public String IMAGE_TEST_DIRECTORY = "diplomaTestImages";
//
//    ImageButton prev = null;
//    int prevInt = R.color.colorBlack;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawing);
//
//        imageView = findViewById(R.id.draw_imageView);
//
//
//        paintDraw = new Paint();
//        paintDraw.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintDraw.setColor(Color.BLACK);
//        paintDraw.setStrokeWidth(10);
//
//        SeekBar seekBar = findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                paintDraw.setStrokeWidth(seekBar.getProgress());
//            }
//        });
//
//
//        final ImageButton pen_red = findViewById(R.id.pen_red);
//        final ImageButton pen_black = findViewById(R.id.pen_black);
//        final ImageButton pen_white = findViewById(R.id.pen_white);
//        final ImageButton pen_grey = findViewById(R.id.pen_grey);
//        prev = pen_black;
//        pen_black.setBackgroundResource(R.drawable.button_border);
//
//        pen_red.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paintDraw.setColor(Color.RED);
//                pen_red.setBackgroundResource(R.drawable.button_border);
//                if (prev != null)
//                    prev.setBackgroundResource(prevInt);
//
//                prevInt = R.color.colorRed;
//                prev = pen_red;
//            }
//        });
//
//
//        pen_black.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paintDraw.setColor(Color.BLACK);
//                pen_black.setBackgroundResource(R.drawable.button_border);
//                if (prev != null)
//                    prev.setBackgroundResource(prevInt);
//
//                prev = pen_black;
//                prevInt = R.color.colorBlack;
//            }
//        });
//
//
//        pen_white.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paintDraw.setColor(Color.WHITE);
//                pen_white.setBackgroundResource(R.drawable.button_border);
//                if (prev != null)
//                    prev.setBackgroundResource(prevInt);
//
//                prev = pen_white;
//                prevInt = R.color.colorWhite;
//            }
//        });
//
//
//        pen_grey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paintDraw.setColor(Color.GRAY);
//                pen_grey.setBackgroundResource(R.drawable.button_border);
//                if (prev != null)
//                    prev.setBackgroundResource(prevInt);
//
//                prev = pen_grey;
//                prevInt = R.color.colorGrey;
//            }
//        });
//
//
//        Intent intent = getIntent();
//        String path = intent.getStringExtra("path");
//
//        File imgFile = new File(path);
//        if (imgFile.exists()) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            imageView.setImageBitmap(myBitmap);
//        }
//
//        Draw();
//
//
//        Button draw_save = findViewById(R.id.draw_save);
//        draw_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImageView imageView = findViewById(R.id.draw_imageView);
//
//                String path = saveImage(((BitmapDrawable) imageView.getDrawable()).getBitmap(), IMAGE_TEST_DIRECTORY);
//
//                Intent intent = getIntent();
//                intent.putExtra("path", path);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//
//
//    }
//
//
//    private void Draw() {
//        Bitmap tempBitmap = null;
//
//        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//        tempBitmap = drawable.getBitmap();
//
//        Bitmap.Config config;
//        if (tempBitmap.getConfig() != null) {
//            config = tempBitmap.getConfig();
//        } else {
//            config = Bitmap.Config.ARGB_8888;
//        }
//
//        bitmapMaster = Bitmap.createBitmap(
//                tempBitmap.getWidth(),
//                tempBitmap.getHeight(),
//                config);
//
//        canvasMaster = new Canvas(bitmapMaster);
//        canvasMaster.drawBitmap(tempBitmap, 0, 0, null);
//
//        imageView.setImageBitmap(bitmapMaster);
//
//        imageResult = imageView;
//
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        prvX = x;
//                        prvY = y;
//                        drawOnProjectedBitMap((ImageView) v, bitmapMaster, prvX, prvY, x, y);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        drawOnProjectedBitMap((ImageView) v, bitmapMaster, prvX, prvY, x, y);
//                        prvX = x;
//                        prvY = y;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        drawOnProjectedBitMap((ImageView) v, bitmapMaster, prvX, prvY, x, y);
//                        break;
//                }
//                return true;
//            }
//        });
//
//
//    }
//
//
//    public String saveImage(Bitmap myBitmap, String directory) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/" + directory);
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "error";
//    }
//
//    private void drawOnProjectedBitMap(ImageView iv, Bitmap bm,
//                                       float x0, float y0, float x, float y) {
////        if (x < 0 || y < 0 || x > iv.getWidth() || y > iv.getHeight()) {
//        //outside ImageView
////            return;
////        } else {
//
//        float ratioWidth = (float) bm.getWidth() / (float) iv.getWidth();
//        float ratioHeight = (float) bm.getHeight() / (float) iv.getHeight();
//
//        canvasMaster.drawLine(
//                x0 * ratioWidth,
//                y0 * ratioHeight,
//                x * ratioWidth,
//                y * ratioHeight,
//                paintDraw);
//        imageResult.invalidate();
////        }
//    }
//
//}
