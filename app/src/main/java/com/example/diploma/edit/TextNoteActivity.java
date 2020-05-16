package com.example.diploma.edit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.diploma.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TextNoteActivity extends AppCompatActivity {

    public String DIRECTORY = "diploma";

    boolean isEditable = false;
    long id = 0;
    int position = 0;
    String path = "";

    String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);


        Bundle extras = getIntent().getExtras();
        if (extras == null)
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
        else {
            isEditable = true;

            EditText et_title = findViewById(R.id.title);
            EditText et_content = findViewById(R.id.content);

            String title = getIntent().getStringExtra("title");
            path = getIntent().getStringExtra("path");
            id = getIntent().getLongExtra("id", 0);
            position = getIntent().getIntExtra("position", 0);
            password = getIntent().getStringExtra("password");

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
            et_content.setText(text.toString());

        }


        Button btnPassword = findViewById(R.id.btnPassword);
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordMenu(v);
            }
        });

        final ImageButton btnSaveNote = findViewById(R.id.btnSaveNote);
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveClick(v);
            }
        });

//        Toast.makeText(getApplicationContext(), "tmp id: " + id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp title: " + ((EditText) findViewById(R.id.title)).getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp content: " + ((EditText) findViewById(R.id.content)).getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp path: " + path, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp password: " + password, Toast.LENGTH_SHORT).show();

//        if (savedInstanceState != null) {
//            EditText et_title = findViewById(R.id.title);
//            EditText et_content = findViewById(R.id.content);
//
//            String title = savedInstanceState.getString("title");
//            String content = savedInstanceState.getString("content");
//            password = savedInstanceState.getString("password");
//            isEditable = savedInstanceState.getBoolean("isEditable");
//
//            et_title.setText(title);
//            et_content.setText(content);
//        }

    }

    private void btnSaveClick(View v) {
        String title = ((EditText) findViewById(R.id.title)).getText().toString();
        String content = ((EditText) findViewById(R.id.content)).getText().toString();
        String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        final Intent intent = new Intent();
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
//                if (!isEditable) {
//                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(TextNoteActivity.this);
//                    final View mView = getLayoutInflater().inflate(R.layout.dialog_set_pass, null);
//
//                    Button btnYes = mView.findViewById(R.id.btnYesPass);
//                    btnYes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String pass = ((EditText) mView.findViewById(R.id.pass)).getText().toString();
//                            if (!pass.isEmpty()) {
//                                password=pass;
//                                intent.putExtra("password", pass);
//                                finish();
//
//                            } else
//                                Toast.makeText(getApplicationContext(), "Please, enter password", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    Button btnNo = mView.findViewById(R.id.btnNoPass);
//                    btnNo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            intent.putExtra("password", "");
//                            finish();
//
//                        }
//                    });
//
//
//                    mBuilder.setView(mView);
//                    AlertDialog dialog = mBuilder.create();
//                    dialog.show();
//
//
//                } else {
//                    intent.putExtra("password", password);
//                    finish();
//                }

//        Toast.makeText(getApplicationContext(), "tmp id: " + id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp title: " + ((EditText) findViewById(R.id.title)).getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp content: " + ((EditText) findViewById(R.id.content)).getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp path: " + path, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "tmp password: " + password, Toast.LENGTH_SHORT).show();

        intent.putExtra("password", password);
        finish();
    }


    private void showPasswordMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.password_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_passwordAdd: {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TextNoteActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TextNoteActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TextNoteActivity.this);
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

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString("title", ((EditText) findViewById(R.id.title)).getText().toString());
//        outState.putString("content", ((EditText) findViewById(R.id.content)).getText().toString());
//        outState.putString("password", password);
//        outState.putBoolean("isEditable", isEditable);
//    }
}