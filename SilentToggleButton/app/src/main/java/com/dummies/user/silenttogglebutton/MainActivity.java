package com.dummies.user.silenttogglebutton;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button toggleButton; //Кнопка переключения
    private AudioManager mAudioManager; //доступ к состоянию звонка
    private Boolean mPhoneIsSilent;// текущее состояние звонка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        checkIfPhoneIsSilent();

        toggleButton = (Button) findViewById(R.id.toggleButton);
        clickButton(toggleButton);


    }
    private void checkIfPhoneIsSilent(){
        //получение текущего состояние звонка
        //передача значения булев параметру mPhoneIsSilent
        mPhoneIsSilent = mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT;

    }
    /**перехват клика по кнопке*/
    private void clickButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //функция после нажатия кнопки
                ringerSwitchMode();
            }
        });
    }
    /**Проверка текущего состояния и переключение*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ringerSwitchMode() {
        checkAccess();
        if(mPhoneIsSilent) {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mPhoneIsSilent = false;
        }
        else {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            mPhoneIsSilent = true;
        }
        toggleUi();
    }
    /**проверка и запрос доступа к системным функциям*/
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private  void checkAccess() {
    NotificationManager n = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    if(!n.isNotificationPolicyAccessGranted()) {
        //Ask the user to grant access
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        startActivityForResult(intent, 0);
    }
}
    /**проверка состояния телефона*/

    /**Переключение рисунка */
    private void toggleUi() {
        ImageView newPhoneImage = (ImageView) findViewById(R.id.phone_icon);
        if(mPhoneIsSilent)
            newPhoneImage.setImageResource(R.drawable.phone_silent);
        else
            newPhoneImage.setImageResource(R.drawable.phone_on);
    }
    /**состояние в режиме ожидания и восстановления доступа к программе*/
    @Override
    protected void onResume() {
        super.onResume();
        checkIfPhoneIsSilent();
        toggleUi();
    }


}
