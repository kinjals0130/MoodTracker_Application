package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileDescriptor;

public class VoiceNoteActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener  {

    private static final int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    TextView tv_userMessage , tv_recordbtn, tv_stopBtn;
    Button btn_saveVoiceNote, btn_record, btn_stopRrecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_note);

        if (checkMicrophonePresent()) {
            getMicrophonePermission();
        }

        tv_userMessage = findViewById(R.id.tv_message);





        btn_record = (Button) findViewById(R.id.btn_record);
        tv_recordbtn= findViewById(R.id.tv_recordbtn);

        btn_stopRrecord = (Button) findViewById(R.id.btn_stop);
        tv_stopBtn= findViewById(R.id.tv_stopbtn);
        btn_stopRrecord.setVisibility(View.INVISIBLE);
        tv_stopBtn.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("SetTextI18n")
    public void btnPressRecord(View view) {

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            tv_userMessage.setText("Audio is now recording.");
            btn_stopRrecord.setVisibility(View.VISIBLE);
            tv_stopBtn.setVisibility(View.VISIBLE);

            btn_record.setVisibility(View.INVISIBLE);
            tv_recordbtn.setVisibility(View.INVISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnPressStop(View view) {

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        tv_userMessage.setText("Audio has stopped recording.");
        btn_stopRrecord.setVisibility(View.INVISIBLE);
        tv_stopBtn.setVisibility(View.INVISIBLE);
        btn_record.setVisibility(View.VISIBLE);
        tv_recordbtn.setVisibility(View.VISIBLE);

    }

    public void btnPressPlay(View view) {

        try {
            mediaPlayer = new MediaPlayer();
//            mediaPlayer.reset();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            FileDescriptor fd = file.get
//            File file =new File(getRecordingFilePath());
            mediaPlayer.setDataSource(getRecordingFilePath());
            Log.d("Voice Playback", getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            tv_userMessage.setText("Recording is now playing.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private boolean checkMicrophonePresent() {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    private void getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());

        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(getApplicationContext().getFilesDir(), "recordingTestFile" + ".mp3");
        return file.getPath();

    }

    public void discardVoiceNote(View view) {
//        Intent backPage = new Intent(VoiceNoteActivity.this, NoteActivity.class);
        Intent returnIntent = new Intent();
//        File file = new File(getApplicationContext().getFilesDir(), "recordingTestFile" + ".mp3");
//        Log.d("returning", String.valueOf(file.toURI()));
        returnIntent.putExtra("filePath", "");
//        returnIntent.setData(file.toUri());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }
    public void saveVoiceNote(View view) {

        Intent returnIntent = new Intent();
        File file = new File(getApplicationContext().getFilesDir(), "recordingTestFile" + ".mp3");
        Log.d("returning", String.valueOf(file.toURI()));
        returnIntent.putExtra("filePath", file.getAbsolutePath());
//        returnIntent.setData(file.toUri());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();


    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        mediaPlayer.start();
    }

}