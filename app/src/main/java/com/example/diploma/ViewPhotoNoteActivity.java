package com.example.diploma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ViewPhotoNoteActivity extends AppCompatActivity {

    private String path = "";
    private int id = 0;
    private int position = 0;

    private boolean isPossible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_note);





        path = getIntent().getStringExtra("path");
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);



        final String pass=getIntent().getStringExtra("password");
        if(!pass.isEmpty())
        {
            isPossible=false;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewPhotoNoteActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.dialog_check_pass, null);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            Button btnCheck=mView.findViewById(R.id.checkPassBtn);
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passLocal=((EditText)mView.findViewById(R.id.et_check_pass)).getText().toString();
                    if(!pass.equals(passLocal))
                    {
                        Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        dialog.cancel();
                        isPossible=true;
                        InitView();
                    }
                }
            });
            dialog.show();
        }


        if(isPossible) {
            InitView();
        }
    }


    private void InitView()
    {
        String title = getIntent().getStringExtra("title");
        TextView et_title = findViewById(R.id.view_photo_title);
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
                String password = getIntent().getStringExtra("password");
                intent.putExtra("password", password);
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

                String password = data.getStringExtra("password");

                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("position", position);

                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("password", password);
                intent.putExtra("changeDate", changeDate);

                intent.putExtra("isEditable", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }
    }


}
