package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class VoiceNoteActivity extends AppCompatActivity {

    private static int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    TextView tv_userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_note);

        if(checkMicrophonePresent()){
            getMicrophonePermission();
        }

        tv_userMessage = findViewById(R.id.tv_message);
    }

    @SuppressLint("SetTextI18n")
    public void btnPressRecord(View view){

        try{
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            tv_userMessage.setText("Audio is now recording.");

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public void btnPressStop(View view){

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        tv_userMessage.setText("Audio has stopped recording.");

    }
    public void btnPressPlay(View view){

        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            tv_userMessage.setText("Recording is now playing.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkMicrophonePresent(){
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        };
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());

        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "recordingTestFile" + ".mp3");
        return file.getPath();

    }

    public void discardVoiceNote(View view){
        Intent backPage = new Intent(VoiceNoteActivity.this, NoteActivity.class);
        startActivity(backPage);

    }
}