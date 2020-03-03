package com.example.diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewListNoteActivity extends AppCompatActivity {

    private String path = "";
    private int id = 0;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_note);

        TextView et_title = findViewById(R.id.view_list_title);

        String title = getIntent().getStringExtra("title");
        path = getIntent().getStringExtra("path");
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);

        et_title.setText(title);

        String text = readFromTextFile(path);

        LinearLayout view_list_layout=findViewById(R.id.view_list_layout);
        String items[] = text.split("-----");
        for (int i = 0; i < items.length-1; i++) {

            TextView point=new TextView(view_list_layout.getContext());
            point.setText(" ■ ");
//            CheckBox cb = new CheckBox(view_list_layout.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            point.setLayoutParams(lp);

            TextView tv=new TextView(view_list_layout.getContext());
            tv.setText(items[i]);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(lp2);

            LinearLayout lin=new LinearLayout(view_list_layout.getContext());
            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lin.setLayoutParams(lp3);
            lin.setOrientation(LinearLayout.HORIZONTAL);
            lin.addView(point);
            lin.addView(tv);

            view_list_layout.addView(lin);
        }



        ImageButton btnEdit = findViewById(R.id.view_list_btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class);
                intent.putExtra("id", id);

                String title = getIntent().getStringExtra("title");
                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("position", position);
                startActivityForResult(intent, 3);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 3: {
                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");

//                TextView tv_title = findViewById(R.id.view_list_title);
//                tv_title.setText(title);


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

}
