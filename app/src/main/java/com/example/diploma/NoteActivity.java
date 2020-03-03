//package com.example.diploma;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.media.MediaScannerConnection;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.PopupMenu;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
//
//public class NoteActivity extends AppCompatActivity {
//
//    LinearLayout photosLayout;
//
//    public static final int FROM_CAMERA = 1;
//    public static final int FROM_GALLERY = 2;
//    public static final int DRAWING = 3;
//
//
//    ArrayList<String> photos = new ArrayList<String>();
//    ArrayList<String> photosTmp = new ArrayList<String>();
//    ArrayList<Bitmap> photosBitmap = new ArrayList<>();
//    ArrayList<Bitmap> photosBitmapTmp = new ArrayList<>();
//
//    ImageView mainImageView;
//
//    Boolean isEditable = false;
//    Boolean isDrawing = false;
//
//
//    public String IMAGE_TEST_DIRECTORY = "diplomaTestImages";
//    public String IMAGE_DIRECTORY = "diplomaImages";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_note);
//
//
//        Bundle extras = getIntent().getExtras();
//        if (extras == null)
//            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
//        else {
//            isEditable = true;
//
//            EditText et_title = findViewById(R.id.title);
//            EditText et_content = findViewById(R.id.content);
//
//            String title = getIntent().getStringExtra("title");
//            String content = getIntent().getStringExtra("content");
//
//            et_title.setText(title);
//            et_content.setText(content);
//
//            photos = getIntent().getStringArrayListExtra("photos");
//            Toast.makeText(getApplicationContext(), String.valueOf(photos.size()), Toast.LENGTH_LONG).show();
//            LinearLayout photosLayout = findViewById(R.id.photos);
//
//            for (int i = 0; i < photos.size(); i++) {
//                ImageView image = new ImageView(getApplicationContext());
//
//                Bitmap myBitmap = BitmapFactory.decodeFile(photos.get(i));
//                image.setImageBitmap(myBitmap);
//                LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                image.setLayoutParams(lp);
//
//                photosLayout.addView(image);
//            }
//        }
//
//
//        ImageButton btnSaveNote = findViewById(R.id.btnSaveNote);
//        btnSaveNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String title = ((EditText) findViewById(R.id.title)).getText().toString();
//                String content = ((EditText) findViewById(R.id.content)).getText().toString();
//
//                String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
//
//                Intent intent = new Intent();
//                intent.putExtra("title", title);
//                intent.putExtra("content", content);
//                intent.putExtra("createDate", currentDate);
//                intent.putExtra("isEditable", isEditable);
//                intent.putStringArrayListExtra("photos", photos);
//                if (isEditable)
//                    intent.putExtra("id", getIntent().getIntExtra("id", 0));
//
//                for (int i = 0; i < photosLayout.getChildCount(); i++) {
//                    ImageView image = (ImageView) photosLayout.getChildAt(i);
//                    photosBitmapTmp.add(((BitmapDrawable) image.getDrawable()).getBitmap());
//                }
//                for (Bitmap b : photosBitmapTmp) {
//                    String fileName = saveImage(b, IMAGE_DIRECTORY);
//                    photosTmp.add(fileName);
//                }
//
//                intent.putStringArrayListExtra("photosTmp", photosTmp);
//
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//
//        photosLayout = findViewById(R.id.photos);
//        ImageButton btnAddElement = findViewById(R.id.btnAddElement);
//        btnAddElement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupMenu(v);
//
//            }
//        });
//
//
//        final ImageButton draw = findViewById(R.id.btnDraw);
//        draw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isDrawing == false) {
//                    isDrawing = true;
//                    draw.setImageResource(R.mipmap.ic_undraw);
//                }
//                else {
//                    isDrawing = false;
//                    draw.setImageResource(R.mipmap.ic_draw);
//                }
//
//            }
//        });
//    }
//
//
//    private void showPopupMenu(View v) {
//        PopupMenu popupMenu = new PopupMenu(this, v);
//        popupMenu.inflate(R.menu.addelement_menu);
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_addPhotoFromCamera: {
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, FROM_CAMERA);
//
//                        return true;
//                    }
//                    case R.id.action_addPhotoFromGallery: {
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent, "Pick an image"), FROM_GALLERY);
//                        return true;
//                    }
//                    default:
//                        return false;
//                }
//            }
//        });
//        popupMenu.show();
//    }
//
//
//    public String saveImage(Bitmap myBitmap, String directory) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory()+"/" + directory);
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
//        return "";
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(data!=null) {
//            switch (requestCode) {
//                case FROM_CAMERA: {
//                    final ImageView imageView = new ImageView(getApplicationContext());
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    photosBitmap.add(photo);
//
//                    imageView.setImageBitmap(photo);
//                    photos.add(saveImage(photo, IMAGE_TEST_DIRECTORY));
//
//                    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//                    imageView.setLayoutParams(lp);
//                    photosLayout.addView(imageView);
//
//
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (isDrawing) {
//                                mainImageView = imageView;
//
//                                Intent intent = new Intent(v.getContext(), Drawing.class);
//                                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//                                Bitmap b = drawable.getBitmap();
//
//                                String path = saveImage(b, IMAGE_TEST_DIRECTORY);
//                                intent.putExtra("path", path);
//                                startActivityForResult(intent, DRAWING);
//                            }
//                        }
//                    });
//                }
//                break;
//                case FROM_GALLERY: {
//                    Uri selectedImage = data.getData();
//
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String imgDecodableString = cursor.getString(columnIndex);
//                    cursor.close();
//
//                    photos.add(imgDecodableString);
//                    final ImageView imageView = new ImageView(getApplicationContext());
//                    imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
//
//                    LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                    imageView.setLayoutParams(lp);
//                    photosLayout.addView(imageView);
//
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (isDrawing) {
//                                mainImageView = imageView;
//
//                                Intent intent = new Intent(v.getContext(), Drawing.class);
//                                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//                                Bitmap b = drawable.getBitmap();
//
//                                String path = saveImage(b, IMAGE_TEST_DIRECTORY);
//                                intent.putExtra("path", path);
//                                startActivityForResult(intent, DRAWING);
//                            }
//                        }
//                    });
//                }
//                break;
//                case DRAWING: {
//                    String path = data.getStringExtra("path");
//
//                    File f = new File(path);
//                    mainImageView.setImageURI(Uri.fromFile(f));
//                }
//                break;
//            }
//        }
//    }
//
//}
//
