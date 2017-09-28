package mydomain.com.calcf;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnSum, btnBack, btnDivide, btnSub, btnMul, btnStart;
    TextView textV;
    Handler mainHandler;
    Integer firstValue = null, secondValue = null, sumValue;
    String currentOperation = "", currentVal="";// Переменная которая хранит в себе символ текущей операции


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnSum = (Button) findViewById(R.id.btnSum);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnStart = (Button) findViewById(R.id.btnStart);
        textV = (TextView) findViewById(R.id.textV);

        mainHandler = new Handler(getMainLooper());


        View.OnClickListener onNumbersClicknew = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View v - ссылка, откуда пришел вызов от элемента, чтобы не забыть
                Button thisButton = (Button) v;
                currentVal += thisButton.getText();
                textV.setText(currentVal);
            }
        };
        btn0.setOnClickListener(onNumbersClicknew);
        btn1.setOnClickListener(onNumbersClicknew);
        btn2.setOnClickListener(onNumbersClicknew);
        btn3.setOnClickListener(onNumbersClicknew);
        btn4.setOnClickListener(onNumbersClicknew);
        btn5.setOnClickListener(onNumbersClicknew);
        btn6.setOnClickListener(onNumbersClicknew);
        btn7.setOnClickListener(onNumbersClicknew);
        btn8.setOnClickListener(onNumbersClicknew);
        btn9.setOnClickListener(onNumbersClicknew);

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOperation = "+";
                setValue();
            }
        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOperation="/";
                setValue();
            }
        });
        //
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOperation="-";
                setValue();
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOperation="*";
                setValue();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumValue = 0;
                showResult();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue();
                switch (currentOperation) {
                    case "+": sumValue = firstValue + secondValue; break;
                    case "-": sumValue = firstValue - secondValue; break;
                    case "/": sumValue = firstValue / secondValue; break;
                    case "*": sumValue = firstValue * secondValue; break;
                }
                showResult();
            }
        });
    }
//1+1=2 +1
    void setValue () {
        if (isEmpty(firstValue)) {
            firstValue = Integer.parseInt(currentVal);
            textV.setText(firstValue.toString());
        } else {
            secondValue = Integer.parseInt(currentVal);
            textV.setText(secondValue.toString());
        }
        currentVal="";

    }
    boolean isEmpty(Integer val) {
        return val == null;
    }

    void showResult() {
        textV.setText(sumValue.toString());
        firstValue = sumValue;
        secondValue = null;
        sumValue=0;
        currentVal = sumValue.toString();
    }


}
