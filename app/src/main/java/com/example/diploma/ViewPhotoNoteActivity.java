package com.example.diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ViewPhotoNoteActivity extends AppCompatActivity {

    private String path = "";
    private int id = 0;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_note);


        TextView et_title = findViewById(R.id.view_photo_title);

        String title = getIntent().getStringExtra("title");
        path = getIntent().getStringExtra("path");
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);

        et_title.setText(title);

        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ImageView im=findViewById(R.id.view_photo_image);
        im.setImageBitmap(bitmap);


        ImageButton btnEdit = findViewById(R.id.view_photo_btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
                intent.putExtra("id", id);

                String title = getIntent().getStringExtra("title");
                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("position", position);
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 2: {
                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");

                TextView tv_title = findViewById(R.id.view_photo_title);
                tv_title.setText(title);


                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("position", position);

                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("changeDate", changeDate);

                intent.putExtra("isEditable", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }
    }


}
