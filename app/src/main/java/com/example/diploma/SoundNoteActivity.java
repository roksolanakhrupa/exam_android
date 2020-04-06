package com.example.diploma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SoundNoteActivity extends AppCompatActivity {

    public String DIRECTORY = "diploma";

    boolean isEditable = false;
    int id = 0;
    int position = 0;
    String path = "";

    String password = "";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    TextView record_time;
    TextView play_current_time;
    TextView play_all_time;

    Button play_start;
    Button play_pause;
    Button play_stop;
    Button record_start;
    Button record_pause;
    Button record_stop;

    SeekBar play_duration;
    CountDownTimer play_timer;

    int passedSec = 0;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_note);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
            path = Environment.getExternalStorageDirectory() + "/" + DIRECTORY + "/" + Calendar.getInstance().getTimeInMillis() + ".3gpp";
        } else {
            isEditable = true;

            EditText et_title = findViewById(R.id.sound_title);

            String title = getIntent().getStringExtra("title");
            path = getIntent().getStringExtra("path");
            id = getIntent().getIntExtra("id", 0);
            position = getIntent().getIntExtra("position", 0);
            password = getIntent().getStringExtra("password");

            et_title.setText(title);


        }

        record_start = findViewById(R.id.recordnew_start);
        record_pause = findViewById(R.id.recordnew_pause);
        record_stop = findViewById(R.id.recordnew_stop);
        play_start = findViewById(R.id.play_start);
        play_pause = findViewById(R.id.play_pause);
        play_stop = findViewById(R.id.play_stop);

        record_start.setEnabled(true);
        record_start.setEnabled(false);
        play_pause.setEnabled(false);
        play_stop.setEnabled(false);
        play_start.setEnabled(true);

        record_time = findViewById(R.id.record_time);
        play_current_time = findViewById(R.id.current_time);
        play_all_time = findViewById(R.id.all_time);

        play_duration = findViewById(R.id.play_duration);
        handler = new Handler();

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

                String title = ((EditText) findViewById(R.id.sound_title)).getText().toString();
                String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

                final Intent intent = new Intent();
                if (isEditable) {
                    intent.putExtra("id", id);
                    intent.putExtra("position", position);
                }

                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("changeDate", currentDate);


                intent.putExtra("isEditable", isEditable);
                setResult(RESULT_OK, intent);

                intent.putExtra("password", password);
                finish();
            }
        });

        record_start.setEnabled(true);
        record_pause.setEnabled(false);
        record_stop.setEnabled(false);


        play_start.setEnabled(true);
        play_pause.setEnabled(false);
        play_stop.setEnabled(false);


        play_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (play_timer != null) {
                    mediaPlayer.pause();
                    play_timer.cancel();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                passedSec = seekBar.getProgress();
                if (passedSec != 0)
                    mediaPlayer.seekTo((passedSec + 1) * 1000);
                else
                    mediaPlayer.seekTo(0);

                mediaPlayer.start();
                MyTimer();
            }
        });


    }

    private void showPasswordMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.password_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_passwordAdd: {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SoundNoteActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SoundNoteActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SoundNoteActivity.this);
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


    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            record_time.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };


    public void recordStart(View v) {
        try {
            //releaseRecorder();

            File outFile = new File(path);
            if (outFile.exists()) {
                outFile.delete();
            }
            record_start.setEnabled(false);
            record_pause.setEnabled(true);
            record_stop.setEnabled(true);

            if (mediaRecorder == null) {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile(path);
                mediaRecorder.prepare();
                mediaRecorder.start();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder.resume();
            }


            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recordPause(View v) {
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder.pause();
        }

        record_start.setEnabled(true);
        record_pause.setEnabled(false);
        record_stop.setEnabled(true);
    }

    public void recordStop(View v) {
        if (mediaRecorder != null) {
            mediaRecorder.stop();

            record_start.setEnabled(true);
            record_pause.setEnabled(false);
            record_stop.setEnabled(false);


            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
            SetAllTime(MillisecondTime);

            MillisecondTime = 0L;
            StartTime = 0L;
            TimeBuff = 0L;
            UpdateTime = 0L;
            Seconds = 0;
            Minutes = 0;
            MilliSeconds = 0;
            handler.removeCallbacks(runnable);
            record_time.setText("00:00:00");

            Toast.makeText(getApplicationContext(), "Recorded", Toast.LENGTH_SHORT).show();

            releaseRecorder();
        }
    }

    private void SetAllTime(long milTime) {
        long duration = milTime;
        play_duration.setMax((int) duration / 1000);

        long cntMin = (duration / 1000) / 60;
        long cntSec = (duration / 1000) % 60;

        String text = "";
        text += (cntMin < 10) ? "0" + cntMin + ":" : cntMin + ":";
        text += (cntSec < 10) ? "0" + cntSec : cntSec;
        play_all_time.setText(text);
    }


    private void MyTimer() {
        int duration = (mediaPlayer.getDuration() - (passedSec * 1000));
        play_timer = new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
                if (passedSec != 0) {
                    play_duration.setProgress(play_duration.getProgress() + 1);
                    int tmpCntMin = play_duration.getProgress() / 60;
                    int tmpCntSec = play_duration.getProgress() % 60;


                    String text = "";
                    text += (tmpCntMin < 10) ? "0" + tmpCntMin + ":" : tmpCntMin + ":";
                    text += (tmpCntSec < 10) ? "0" + tmpCntSec : tmpCntSec;
                    play_current_time.setText(text);
                }
                passedSec++;
            }

            public void onFinish() {
                passedSec = 0;
                play_start.setEnabled(true);
                play_pause.setEnabled(false);
                play_stop.setEnabled(false);

                play_duration.setProgress(0);
                play_current_time.setText("00:00");

            }
        }.start();
    }

    public void playStart(View v) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.start();

                play_stop.setEnabled(true);
                play_pause.setEnabled(true);
                play_start.setEnabled(false);
                SetAllTime(mediaPlayer.getDuration());
                MyTimer();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isEditable) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();

                play_stop.setEnabled(true);
                play_pause.setEnabled(true);
                play_start.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SetAllTime(mediaPlayer.getDuration());
            MyTimer();
        }
        if (mediaPlayer == null)
            Toast.makeText(getApplicationContext(), "Empty record", Toast.LENGTH_SHORT).show();
    }

    public void playPause(View v) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();

            play_pause.setEnabled(false);
            play_start.setEnabled(true);
            play_stop.setEnabled(true);
            play_timer.cancel();
        }
    }


    public void playStop(View v) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            play_start.setEnabled(true);
            play_pause.setEnabled(false);
            play_stop.setEnabled(false);

            play_timer.cancel();
            play_duration.setProgress(0);
            play_current_time.setText("00:00");
            passedSec = 0;

            releasePlayer();
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
    }


}
