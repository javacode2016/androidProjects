package com.dummies.user.silentbutton;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Main extends AppCompatActivity {
    Button silentButton, soundButton, vibrateButton;
    private AudioManager mAudioManager;
    private int currentStatus;//0 = norm, 1 = silent, 2 = vibrate, 3 = indefinite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        checkAudioStatus();
        silentButton = (Button)findViewById(R.id.silentButton);
        soundButton = (Button)findViewById(R.id.soundButton);
        vibrateButton = (Button)findViewById(R.id.vibrateButton);

        setButtonClick(vibrateButton);
        setButtonClick(soundButton);
        setButtonClick(silentButton);
    }
    void setButtonClick(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringerSwitchMode(button.getText());
            }
        });
    }
    void checkAccess() {
        NotificationManager notice = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(!notice.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, 0);
        }
    }
    void toggleUI() {
        ImageView newImage = (ImageView)findViewById(R.id.phone_icon);
        switch (currentStatus) {
            case 0: newImage.setImageResource(R.drawable.phone_on); break;
            case 1: newImage.setImageResource(R.drawable.phone_silent); break;
            case 2: newImage.setImageResource(R.drawable.phone_vibrate); break;
            case 3: newImage.setImageResource(R.drawable.phone_on); break;
            default: newImage.setImageResource(R.drawable.phone_on);
        }
    }
    private void ringerSwitchMode(CharSequence text) {
        checkAccess();
        if(text.equals("Sound")) {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            currentStatus = 0;
        }
        else if (text.equals("Silent")) {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            currentStatus = 1;
        }
        else if (text.equals("Vibrate")) {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            currentStatus = 2;
        }
        toggleUI();
    }
    private void checkAudioStatus() {
        if(AudioManager.RINGER_MODE_NORMAL == mAudioManager.getRingerMode())
            currentStatus = 0;
        else if(AudioManager.RINGER_MODE_SILENT == mAudioManager.getRingerMode())
            currentStatus = 1;
        else if(AudioManager.RINGER_MODE_VIBRATE == mAudioManager.getRingerMode())
            currentStatus = 2;
        else
            currentStatus = 3;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAudioStatus();
        toggleUI();

    }
}
