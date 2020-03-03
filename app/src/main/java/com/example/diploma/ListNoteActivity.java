package com.example.diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ListNoteActivity extends AppCompatActivity {

    public String DIRECTORY = "diploma";

    boolean isEditable = false;
    int id = 0;
    int position = 0;
    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);


        Bundle extras = getIntent().getExtras();
        if (extras == null)
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
        else {
            isEditable = true;

            EditText et_title = findViewById(R.id.list_title);
            LinearLayout list_layout = findViewById(R.id.list_layout);

            String title = getIntent().getStringExtra("title");
            path = getIntent().getStringExtra("path");
            id = getIntent().getIntExtra("id", 0);
            position = getIntent().getIntExtra("position", 0);

            et_title.setText(title);


            File file = new File(getIntent().getStringExtra("path"));
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
            }


            String textStr = text.toString();
            String items[] = textStr.split("-----");
            for (int i = 0; i < items.length-1; i++) {
                CheckBox cb = new CheckBox(list_layout.getContext());
                cb.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cb.setLayoutParams(lp);

                EditText et=new EditText(list_layout.getContext());
                et.setText(items[i]);
                et.setBackground(null);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(lp2);

                LinearLayout lin=new LinearLayout(list_layout.getContext());
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lin.setLayoutParams(lp3);
                lin.setOrientation(LinearLayout.HORIZONTAL);
                lin.addView(cb);
                lin.addView(et);

                list_layout.addView(lin);
            }

        }


        Button list_add_item=findViewById(R.id.list_add_item);
        list_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout list_layout = findViewById(R.id.list_layout);

                CheckBox cb = new CheckBox(v.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cb.setLayoutParams(lp);


                EditText et=new EditText(v.getContext());
                et.setBackground(null);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(lp2);

                LinearLayout lin=new LinearLayout(v.getContext());
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lin.setLayoutParams(lp3);
                lin.setOrientation(LinearLayout.HORIZONTAL);
                lin.addView(cb);
                lin.addView(et);

                list_layout.addView(lin);


            }
        });

        ImageButton btnSaveNote = findViewById(R.id.list_btnSaveNote);
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = ((EditText) findViewById(R.id.list_title)).getText().toString();
                String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

                String content = getListItems();

                Intent intent = new Intent();
                if (isEditable) {
                    intent.putExtra("id", id);
                    WriteToFile(content, path);
                    intent.putExtra("position", position);
                } else
                    path = createFile(content);

                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("changeDate", currentDate);


                intent.putExtra("isEditable", isEditable);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private String createFile(String data) {

        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory(), DIRECTORY);
        if (!wallpaperDirectory.exists())
            wallpaperDirectory.mkdirs();

        try {
            File file = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".txt");
            String path = file.getAbsolutePath();
            file.createNewFile();
            WriteToFile(data, path);

            return path;
        } catch (IOException e) {
            Log.d("file", e.getMessage());
        }
        return "error";
    }


    private boolean WriteToFile(String data, String path) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
            fw.append(data);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getListItems() {
        String text = "";
        LinearLayout lin = findViewById(R.id.list_layout);
        for (int i = 0; i < lin.getChildCount(); i++) {
            CheckBox cb=((CheckBox)((LinearLayout)lin.getChildAt(i)).getChildAt(0));
            if(!cb.isChecked()) {
                String item = ((EditText) ((LinearLayout) lin.getChildAt(i)).getChildAt(1)).getText().toString();
                text += item;
                text += "-----";
            }
        }

        return text;

    }

}
