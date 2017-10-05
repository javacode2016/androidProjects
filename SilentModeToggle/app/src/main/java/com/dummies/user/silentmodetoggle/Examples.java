package com.dummies.user.silentmodetoggle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by User on 05.10.2017.
 */

public class Examples extends AppCompatActivity {



    int count = 0;

    //ПРИЕМНИК ЩЕЛЧКА
    Button toggleButton = (Button) findViewById(R.id.toggleButton);
            /*метод findViewById(), доступный для всех деятельностей Android, позволяет найти в к
            омпоновке деятельности любое представление и
            сделать с ним что-нибудь.
            Метод всегда возвращает класс View (Представление), который нужно привести к
            соответствующему типу перед его использованием.
            В приведенной выще строке возвращенный объект View приводится к классуButton (Кнопка),
            производному от класса View */
    //УСТАНОВКА ОБРАБОТЧИКА СОБЫТИЙ.
    //# toggleButton.setOnClickListener(new View.OnClickListener() {
            /*Установка обработчика выполняется путем создания
            приемника View.OnClickListener, который содержит метод onClick()*/
    //# public void onClick(View v) {
                /*Обработчиком события служит метод onClick(),
                вызываемый ОС при нажатии кнопки пользователем*/
    //# }
//# });



    private void onButtonClick() {
        count++;
        ImageView phone_icon = (ImageView) findViewById(R.id.phone_icon);
        if((count%2)==0) {
            toggleButton.setText("Переключен в режим молчания");
            phone_icon.setBackgroundResource(R.drawable.phone_silent);
        }
        else {
            toggleButton.setText("Переключен в режим ожидания");
            phone_icon.setBackgroundResource(R.drawable.phone_on);
        }

    }
}

