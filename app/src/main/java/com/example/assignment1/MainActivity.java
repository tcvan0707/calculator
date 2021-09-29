package com.example.assignment1;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Calculator calculator;
    private TextView displayDefault, displayHistory, switchBtn;
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    private Button plusOp, minusOp, mulOp, divideOp, equalOp;
    private Button clear;
    private Boolean showHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calculator = new Calculator();

        displayDefault = (TextView) findViewById(R.id.displayDefault);
        displayHistory = (TextView) findViewById(R.id.displayHistory);
        switchBtn = (TextView) findViewById(R.id.switchBtn);
        switchBtn.setText("ADVANCE - WITH HISTORY");
        switchBtn.setBackgroundResource(R.color.violet);
        this.showHistory = false;

        num0 = (Button) findViewById(R.id.zero);
        num1 = (Button) findViewById(R.id.one);
        num2 = (Button) findViewById(R.id.two);
        num3 = (Button) findViewById(R.id.three);
        num4 = (Button) findViewById(R.id.four);
        num5 = (Button) findViewById(R.id.five);
        num6 = (Button) findViewById(R.id.six);
        num7 = (Button) findViewById(R.id.seven);
        num8 = (Button) findViewById(R.id.eight);
        num9 = (Button) findViewById(R.id.nine);

        plusOp = (Button) findViewById(R.id.plusOp);
        minusOp = (Button) findViewById(R.id.minusOp);
        mulOp = (Button) findViewById(R.id.mulOp);
        divideOp = (Button) findViewById(R.id.divide);
        equalOp = (Button) findViewById(R.id.equal);
        clear = (Button) findViewById(R.id.clear);
        switchBtn = (TextView) findViewById(R.id.switchBtn);

        // button on click
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        plusOp.setOnClickListener(this);
        minusOp.setOnClickListener(this);
        mulOp.setOnClickListener(this);
        divideOp.setOnClickListener(this);
        equalOp.setOnClickListener(this);
        clear.setOnClickListener(this);
        switchBtn.setOnClickListener(this);
    }

    public void historyButton(MainActivity main, TextView switchBtn, Boolean showHistory){
        if(!showHistory){
            switchBtn.setText("ADVANCE - WITH HISTORY");
            switchBtn.setBackgroundResource(R.color.orange);
            switchBtn.setTextColor(main.getResources().getColor(R.color.black));
        }
        else{
            switchBtn.setText("STANDARD - NO HISTORY");
            switchBtn.setBackgroundResource(R.color.violet);
            switchBtn.setTextColor(main.getResources().getColor(R.color.white));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view){
        String idView = view.getResources().getResourceEntryName(view.getId());

        if(idView.equals("switchBtn")){
            //clear history calculation
            this.showHistory = !this.showHistory;
            this.calculator.clearHistory();
            displayHistory.setText(String.format("%s",""));
            historyButton(this, this.switchBtn, this.showHistory);
        }
        else{
            //get the numbers from user, then add it to the history list
            String numOfString = ((Button)view).getText().toString();
            this.calculator.push(numOfString);
            String displayCalculation = this.calculator.getCurrentCalculation(view);

            //display calculation on screen
            displayDefault.setText(String.format("%s",displayCalculation));

            //if user clicks on history button, show the history list
            if (this.showHistory && ((Button) view).getText().equals("=")){
                displayHistory.setText(String.format("%s", this.calculator.getHistoryList()));
            }
        }
    }
}