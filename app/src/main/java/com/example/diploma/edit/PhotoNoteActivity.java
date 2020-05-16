//import androidx.appcompat.app.AppCompatActivity;
//package com.example.diploma;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.drawable.BitmapDrawable;
//import android.media.MediaScannerConnection;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//
//public class PhotoNoteActivity extends AppCompatActivity {
//
//
//    ImageView imageResult;
//    Bitmap bitmapMaster;
//    Canvas canvasMaster;
//
//    int prvX, prvY;
//    Paint paintDraw;
//    ImageView imageView;
//    public String IMAGE_TEST_DIRECTORY = "diplomaImages";
//
//    ImageButton prev = null;
//    int prevInt = R.color.colorBlack;
//
//    private int DIALOG_CHOOSE=1;
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
//
//        showDialog(DIALOG_CHOOSE);
//
//        SeekBar seekBar = findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) { }
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
//        boolean isEditable=intent.getBooleanExtra("isEditable", false);
//        if(isEditable) {
//            String path = intent.getStringExtra("path");
//
//            File imgFile = new File(path);
//            if (imgFile.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                imageView.setImageBitmap(myBitmap);
//            }
//        }
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
//    OnClickListener myClickListener = new OnClickListener() {
//        public void onClick(DialogInterface dialog, int which) {
//            // выводим в лог позицию нажатого элемента
//            Log.d("info", "which = " + which);
//        }
//    };
//
//    protected Dialog onCreateDialog(int id) {
//        if (id == DIALOG_CHOOSE) {
//            AlertDialog.Builder adb = new AlertDialog.Builder(this);
//            adb.setMessage("Choose photo");
//            adb.setIcon(android.R.drawable.btn_plus);
//
//
//            OnClickListener myClickListener = new OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    // выводим в лог позицию нажатого элемента
//                    Log.d("info", "which = " + which);
//                }
//            };
//
//            String data[] = { "camera", "gallery" };
//            adb.setItems(data, myClickListener);
//            return adb.create();
//        }
//        return super.onCreateDialog(id);
//    }
//
//
//
//
//
//    private void Draw() {
//        Bitmap tempBitmap = null;
//
////        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
////        tempBitmap = drawable.getBitmap();
//
//        Bitmap.Config config;
//        if (tempBitmap.getConfig() != null) {
//            config = tempBitmap.getConfig();
//        } else {
//            config = Bitmap.Config.ARGB_8888;
//        }
//
////        bitmapMaster = Bitmap.createBitmap(
////                tempBitmap.getWidth(),
////                tempBitmap.getHeight(),
////                config);
//
//        bitmapMaster=Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), config);
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
//

package com.example.diploma.edit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diploma.CanvasView;
import com.example.diploma.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PhotoNoteActivity extends AppCompatActivity {

    private CanvasView customCanvas;
    public String IMAGE_DIRECTORY = "diploma";

    private static final int FROM_GALLERY = 123;
    public static final int FROM_CAMERA = 124;

    boolean isEditable = false;
    long id = 0;
    int position = 0;
    String path = "";

    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_note);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
            customCanvas = (CanvasView) findViewById(R.id.canvas);
        } else {

            customCanvas = (CanvasView) findViewById(R.id.canvas);
            customCanvas.isEditable = true;
            isEditable = true;

            EditText et_title = findViewById(R.id.photo_title);

            String title = getIntent().getStringExtra("title");
            path = getIntent().getStringExtra("path");
            id = getIntent().getLongExtra("id", 0);
            position = getIntent().getIntExtra("position", 0);
            password = getIntent().getStringExtra("password");

            et_title.setText(title);

            try {
                Bitmap bitmap = null;
                File f = new File(path);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                bitmap = BitmapFactory.decodeFile(path);

//                ImageView im = findViewById(R.id.test_image);
//                im.setImageBitmap(bitmap);

                customCanvas.setBitmap(bitmap);


            } catch (Exception e) {
                //e.printStackTrace();
//                Toast.makeText(getApplicationContext(), e.printStackTrace(), Toast.LENGTH_LONG).show();
                Log.d("info", e.getMessage());
            }


        }


        Button btnPassword = findViewById(R.id.btnPassword);
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordMenu(v);
            }
        });


        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                customCanvas.changeWidth(seekBar.getProgress());
            }
        });
//


        final ImageButton pen_red = findViewById(R.id.pen_red);
//        final ImageButton pen_black = findViewById(R.id.pen_black);
//        final ImageButton pen_white = findViewById(R.id.pen_white);
//        final ImageButton pen_grey = findViewById(R.id.pen_grey);
       // pen_black.setBackgroundResource(R.drawable.button_border);

        pen_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvas.changeColor(Color.RED);
            }
        });


//        pen_black.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { customCanvas.changeColor(Color.BLACK);
//            }
//        });
//
//
//        pen_white.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { customCanvas.changeColor(Color.WHITE);
//            }
//        });
//
//
//        pen_grey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { customCanvas.changeColor(Color.GRAY);
//            }
//        });

        ImageButton btn_addGalleryPhoto = findViewById(R.id.btn_addGalleryPhoto);
        btn_addGalleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), FROM_GALLERY);
            }
        });


        ImageButton btn_addCameraPhoto = findViewById(R.id.btn_addCameraPhoto);
        btn_addCameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, FROM_CAMERA);
            }
        });


        Button draw_save = findViewById(R.id.draw_save);
        draw_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.photo_title);
                String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

                String path = saveImage(customCanvas.getBitmap(), IMAGE_DIRECTORY);
                String photo_title = et.getText().toString();
                Intent intent = getIntent();
                intent.putExtra("path", path);
                intent.putExtra("title", photo_title);
                intent.putExtra("changeDate", currentDate);
                if (isEditable) {
                    intent.putExtra("isEditable", isEditable);
                    intent.putExtra("id", id);
                    intent.putExtra("position", position);
                }
                setResult(RESULT_OK, intent);
                intent.putExtra("password", password);
                finish();
            }
        });


        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvas.setBackgroundImage(null);
                customCanvas.clearBitmap();
                customCanvas.clearCanvas();
            }
        });

    }


    public String saveImage(Bitmap myBitmap, String directory) {
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/" + directory);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".png");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);

            fo.flush();
            fo.close();


            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "error";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case FROM_CAMERA: {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    customCanvas.setBackgroundImage(photo);
                }
                break;
                case FROM_GALLERY: {
                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    customCanvas.setBackgroundImage(BitmapFactory.decodeFile(imgDecodableString));

                }
                break;

            }
        }
    }


    private void showPasswordMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.password_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_passwordAdd: {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PhotoNoteActivity.this);
                        final View mView = getLayoutInflater().inflate(R.layout.dialog_password_add, null);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        if (password.equals("")) {
                            Button btnSet = mView.findViewById(R.id.password_add_set);
                            btnSet.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    password = ((EditText) mView.findViewById(R.id.password_add_et)).getText().toString();
                                    if (password.equals(""))
                                        Toast.makeText(getApplicationContext(), "empty password", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getApplicationContext(), "Password added", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Password already added", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }


                        Button btnCancel = mView.findViewById(R.id.password_add_cancel);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        return true;
                    }
                    case R.id.action_passwordDelete: {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PhotoNoteActivity.this);
                        final View mView = getLayoutInflater().inflate(R.layout.dialog_password_delete, null);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        if (!password.equals("")) {
                            Button btnYes = mView.findViewById(R.id.password_delete_yes);
                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    password = "";
                                    Toast.makeText(getApplicationContext(), "Password deleted", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });

                            Button btnNo = mView.findViewById(R.id.password_delete_no);
                            btnNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Password deleted - error", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "No password", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        return true;
                    }
                    case R.id.action_passwordChange: {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PhotoNoteActivity.this);
                        final View mView = getLayoutInflater().inflate(R.layout.dialog_password_change, null);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();


                        if (!password.equals("")) {
                            Button btnChange = mView.findViewById(R.id.password_change_set);
                            btnChange.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String oldPass = ((EditText) mView.findViewById(R.id.password_change_oldpassword)).getText().toString();
                                    if (!password.equals("")) {
                                        if (oldPass.equals(password)) {
                                            String newPass = ((EditText) mView.findViewById(R.id.password_change_newpassword)).getText().toString();
                                            password = newPass;
                                            Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Incorrect old password", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No previous password", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }


                                }
                            });

                            Button btnCancel = mView.findViewById(R.id.password_change_cancel);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "No password", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }

                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

}