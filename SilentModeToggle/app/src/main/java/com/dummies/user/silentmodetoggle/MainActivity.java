package com.dummies.user.silentmodetoggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.ImageView;

public class MainActivity extends Activity {

    Button toggleButton;
    private AudioManager mAudioManager;
    private Boolean mPhoneIsSilent;
    ImageView newPhoneImage;

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
        newPhoneImage = findViewById(R.id.phone_icon);
        //доступ к звонку помощью базового класса AudioManager
        // который отвечает за управление состояниями звонка
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

         /* проверка состояния телефона */
        int ringerMode = mAudioManager.getRingerMode();
        if(ringerMode == AudioManager.RINGER_MODE_SILENT) {
            mPhoneIsSilent = true;
        }
        else {
            mPhoneIsSilent = false;
        }

        //ПРИЕМНИК ЩЕЛЧКА
        toggleButton = findViewById(R.id.toggleButton);
        setButtonClickListener(toggleButton);
    }




    private void setButtonClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }
    private void onButtonClick() {

        if(mPhoneIsSilent) {
            //переключение в режим громкого звонка
            newPhoneImage.setBackgroundResource(R.drawable.phone_on);
            //mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            //toggleButton.setText("Активный режим");
            mPhoneIsSilent = false;
        }
        else {
            //переключение в бесшумный режим
            newPhoneImage.setBackgroundResource(R.drawable.phone_silent);
            //mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            //toggleButton.setText("Бесшумный режим");
            mPhoneIsSilent = true;
        }

    }




    @Override
    protected void onResume() {
        super.onResume();

    }

}
