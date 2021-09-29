package com.example.assignment1;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Calculator {
    private String currentCalculation;

    private List<String> historyList;

    public Calculator() {
        this.currentCalculation = "";
        this.historyList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getHistoryList() {
        return toString(this.historyList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCurrentCalculation(View view) {
        //add space into the input, then convert string
        if (((Button) view).getText().toString().equals("C")){
            return "";
        }
        return Arrays.stream(this.currentCalculation.split("")).map(calc -> {
            if (calc.equals("=") || calc.equals("*") || calc.equals("/") || calc.equals("+") || calc.equals("-")){
                return " " + calc + " ";
            }
            return calc;
        }).collect(Collectors.joining());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    //function converting input to String
    public String toString(List<String> stringList) {
        stringList = stringList.stream().map(s -> {
            return Arrays.stream(s.split("")).map(calc -> {
                if (calc.equals("=") || calc.equals("*") || calc.equals("/") || calc.equals("+") || calc.equals("-"))
                    return " " + calc + " ";
                return calc;
            }).collect(Collectors.joining());
        }).collect(Collectors.toList());
        return stringList.toString()
                .replace(",", "\n")
                .replace("[", "")
                .replace("]", "");
    }

    //function checking the input number from user
    public boolean checkNumInput(String strOfNum) {
        int number = Integer.parseInt(strOfNum);
        return number <= Integer.MAX_VALUE && number >= Integer.MIN_VALUE;
    }

    //function checking the input operator from user
    public boolean checkOpInput(String op) {
        if (op.equals("*") || op.equals("/") || op.equals("-") || op.equals("+")){
            return true;
        }else{
            return false;
        }
    }

    //function doing the calculation
    @TargetApi(Build.VERSION_CODES.N)
    public int doCalculation(String num1, String num2, String op) {
        int finalMath = 0;
        if (!checkNumInput(num1) || !checkNumInput(num2) || !checkOpInput(op)){
            return Integer.MAX_VALUE;
        }
        switch (op) {
            case "*":
                finalMath = Integer.parseInt(num1) * Integer.parseInt(num2);
                break;
            case "/":
                finalMath = Integer.parseInt(num1) / Integer.parseInt(num2);
                break;
            case "+":
                finalMath = Integer.parseInt(num1) + Integer.parseInt(num2);
                break;
            case "-":
                finalMath = Integer.parseInt(num1) - Integer.parseInt(num2);
                break;
        }
        return finalMath;
    }

    public void clearHistory() {
        this.historyList = new ArrayList<>();
    }

    public void push(String value) {
        if (value.equals("C")) {
            this.currentCalculation = "";
            return;
        }

        this.currentCalculation += value;

        if (value.equals("=") && this.calculate() != Integer.MAX_VALUE) {
            //calculate the current string, and add it to history list
            this.currentCalculation += this.calculate();
            this.historyList.add(this.currentCalculation);
        }

    }

    public int calculate() {
        if (this.currentCalculation.isEmpty()){
            return Integer.MAX_VALUE;
        }
        int answer = doCalculation(String.valueOf(this.currentCalculation.charAt(0)), String.valueOf(this.currentCalculation.charAt(2)), String.valueOf(this.currentCalculation.charAt(1)));
        for (int i = 3; i < this.currentCalculation.length() && this.currentCalculation.charAt(i) != '='; i += 2) {
            answer = doCalculation(String.valueOf(answer), String.valueOf(this.currentCalculation.charAt(i + 1)), String.valueOf(this.currentCalculation.charAt(i)));
        }
        return answer;
    }
}
