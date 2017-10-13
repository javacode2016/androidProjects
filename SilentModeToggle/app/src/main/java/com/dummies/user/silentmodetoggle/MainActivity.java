package com.dummies.user.silentmodetoggle;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    Button toggleButton;
    private AudioManager mAudioManager;
    private Boolean mPhoneIsSilent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Объект Bundle представляет собой некий HashMap, который позволяет хранить ключ-значение.
        //Ключом выступает строковый ресурс.
        super.onCreate(savedInstanceState);
            /* переопределение метода onCreate, в классе-родителе*/
        setContentView(R.layout.activity_main);
           /* Чтобы операционная система отобразила пользовательский интерфейс на экране,
            нужно установить представление содержимого деятельности с помощью
            следующей инструкции:
            Аргумент R.layout.main ссылается на файл activity_main.xml, расположенный в папке
            res/layouts и определяющий компоновку пользовательского интерфейса
            */



        //доступ к звонку помощью базового класса AudioManager
        // который отвечает за управление состояниями звонка
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        checkIfPhoneIsSilent();

        //ПРИЕМНИК ЩЕЛЧКА
        toggleButton = findViewById(R.id.toggleButton);
        setButtonClickListener(toggleButton);

        Log.v("SilentModeApp", "Это моя запись");
    }



    //функция приема щелчка
    private void setButtonClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                ringerSwitchMode();
            }
        });
    }
    /** проверка состояния телефона */
    private void checkIfPhoneIsSilent() {
        mPhoneIsSilent = mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT;
    }
    /** проверка и запрос доступа к системным функциям*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkAccess() {
        NotificationManager n = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(!n.isNotificationPolicyAccessGranted()) {
           // Ask the user to grant access
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, 0);
        }
    }
    /**переключение режимов звонка */
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

    /** переключение рисунка */
    private void toggleUi() {
        ImageView newPhoneImage = findViewById(R.id.phone_icon);
        if(mPhoneIsSilent) {
            newPhoneImage.setImageResource(R.drawable.phone_silent);
            //toggleButton.setText("Тихий");
        }
        else {
            newPhoneImage.setImageResource(R.drawable.phone_on);
            //toggleButton.setText("Громкий");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfPhoneIsSilent();
        toggleUi();
    }

}
