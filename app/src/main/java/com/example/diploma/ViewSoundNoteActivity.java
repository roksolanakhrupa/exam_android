package com.example.diploma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ViewSoundNoteActivity extends AppCompatActivity {

    private String path = "";
    private int id = 0;
    private int position = 0;

    private boolean isPossible = true;
    private MediaPlayer mediaPlayer;

    int passedSec = 0;

    TextView current_time;
    TextView all_time;
    Button play_start;
    Button play_pause;
    Button play_stop;
    SeekBar play_duration;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sound_note);


        final String pass = getIntent().getStringExtra("password");
        if (!pass.isEmpty()) {
            isPossible = false;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewSoundNoteActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.dialog_check_pass, null);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            Button btnCheck = mView.findViewById(R.id.checkPassBtn);
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passLocal = ((EditText) mView.findViewById(R.id.et_check_pass)).getText().toString();
                    if (!pass.equals(passLocal)) {
                        Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        dialog.cancel();
                        isPossible = true;
                        InitView();
                    }
                }
            });
            dialog.show();
        }


        if (isPossible) {
            InitView();
        }
    }

    private void InitView() {
        String title = getIntent().getStringExtra("title");
        path = getIntent().getStringExtra("path");
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);

        TextView et_title = findViewById(R.id.sound_title);
        et_title.setText(title);

        current_time = findViewById(R.id.current_time);
        all_time = findViewById(R.id.all_time);
        play_start = findViewById(R.id.play_start);
        play_stop = findViewById(R.id.play_stop);
        play_pause = findViewById(R.id.play_pause);
        play_duration = findViewById(R.id.play_duration);


        releasePlayer();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int duration = mediaPlayer.getDuration();
        play_duration.setMax(duration / 1000);
        play_duration.setProgress(0);

        int cntMin = (duration / 1000) / 60;
        int cntSec = (duration / 1000) % 60;

        String text = cntMin + ":";
        text += (cntSec < 10) ? "0" + cntSec : cntSec;
        all_time.setText(text);
        current_time.setText("00:00");

        ImageButton btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
                intent.putExtra("id", id);

                String title = getIntent().getStringExtra("title");
                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("position", position);
                String password = getIntent().getStringExtra("password");
                intent.putExtra("password", password);
                startActivityForResult(intent, 4);
            }
        });

        play_stop.setEnabled(false);


        play_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(timer!=null) {
                    mediaPlayer.pause();
                    timer.cancel();
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
                playStart(seekBar);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {

            case 4: {
                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");
                String password = data.getStringExtra("password");

                TextView tv_title = findViewById(R.id.sound_title);
                tv_title.setText(title);


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

    public void playStart(View v) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
            }
            mediaPlayer.start();
            play_start.setEnabled(false);
            play_pause.setEnabled(true);
            play_stop.setEnabled(true);

            int timerDuration = mediaPlayer.getDuration() - (passedSec*1000)+1000;
            timer = new CountDownTimer(timerDuration, 1000) {

                public void onTick(long millisUntilFinished) {
                    if (passedSec != 0) {
                        play_duration.setProgress(play_duration.getProgress() + 1);
                        int tmpCntMin = play_duration.getProgress() / 60;
                        int tmpCntSec = play_duration.getProgress() % 60;

                        String text = "";
                        text += (tmpCntMin < 10) ? "0" + tmpCntMin + ":" : tmpCntMin + ":";
                        text += (tmpCntSec < 10) ? "0" + tmpCntSec : tmpCntSec;
                        current_time.setText(text);

                    }
                    passedSec++;
                }

                public void onFinish() {
                    passedSec = 0;
                    play_start.setEnabled(true);
                    play_pause.setEnabled(false);
                    play_stop.setEnabled(false);

                    play_duration.setProgress(0);
                    current_time.setText("00:00");

                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void playPause(View v) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            play_pause.setEnabled(false);
            play_start.setEnabled(true);
            timer.cancel();
        }
    }

    public void playStop(View v) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();

            play_start.setEnabled(true);
            play_pause.setEnabled(false);
            play_stop.setEnabled(false);

            timer.cancel();
            play_duration.setProgress(0);
            current_time.setText("00:00");
            passedSec = 0;

            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
