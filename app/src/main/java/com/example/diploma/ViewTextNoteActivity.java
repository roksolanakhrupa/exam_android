package com.example.diploma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewTextNoteActivity extends AppCompatActivity {


    private String path = "";
    private int id = 0;
    private int position = 0;

    private boolean isPossible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_note);


        final String pass=getIntent().getStringExtra("password");
        if(!pass.isEmpty())
        {
            isPossible=false;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewTextNoteActivity.this);
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
        TextView et_title = findViewById(R.id.view_text_title);
        TextView et_content = findViewById(R.id.view_text_content);

        String title = getIntent().getStringExtra("title");
        path = getIntent().getStringExtra("path");
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);

        et_title.setText(title);

        String text = readFromTextFile(path);
        et_content.setText(text);


        ImageButton btnEdit = findViewById(R.id.view_text_btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextNoteActivity.class);
                intent.putExtra("id", id);

                String title = getIntent().getStringExtra("title");
                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("position", position);
                String password = getIntent().getStringExtra("password");
                intent.putExtra("password", password);
                startActivityForResult(intent, 1);
            }
        });

    }


    private String readFromTextFile(String path) {
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text.toString();
        } catch (IOException e) {
        }
        return "error";

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {

            case 1: {

//                String title = ((TextView) findViewById(R.id.title)).getText().toString();
//                String content = ((TextView) findViewById(R.id.content)).getText().toString();
//                String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());


                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");
                String password = data.getStringExtra("password");

                TextView tv_title = findViewById(R.id.view_text_title);
                tv_title.setText(title);

                TextView tv_content = findViewById(R.id.view_text_content);
                tv_content.setText(readFromTextFile(path));

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

