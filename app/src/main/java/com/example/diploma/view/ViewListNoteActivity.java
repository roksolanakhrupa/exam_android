package com.example.diploma.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diploma.edit.ListNoteActivity;
import com.example.diploma.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewListNoteActivity extends AppCompatActivity {

    private String path = "";
    private long id = 0;
    private int position = 0;

    private boolean isPossible=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_note);

        path = getIntent().getStringExtra("path");
        id = getIntent().getLongExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);


        final String pass=getIntent().getStringExtra("password");
        if(!pass.isEmpty())
        {
            isPossible=false;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewListNoteActivity.this);
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

    TextView et_title = findViewById(R.id.view_list_title);
    String title = getIntent().getStringExtra("title");

    et_title.setText(title);

    String text = readFromTextFile(path);

    LinearLayout view_list_layout=findViewById(R.id.view_list_layout);
    String items[] = text.split("-----");
    for (int i = 0; i < items.length-1; i++) {

        TextView point=new TextView(view_list_layout.getContext());
        point.setText(" ■ ");
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
            String password = getIntent().getStringExtra("password");
            intent.putExtra("password", password);
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
