package com.example.mac.uemr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatePickerActivity extends AppCompatActivity {
    int style_id;

    private Spinner myMonthSpinner;
    private List<String> monthList = new ArrayList<String>();
    private ArrayAdapter<String> monthAdapter;

    private Spinner myYearSpinner;
    private List<String> yearList = new ArrayList<String>();
    private ArrayAdapter<String> yearAdapter;

    Intent i;

    private String monthChosen=" ";
    private String yearChosen=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Calendar c = Calendar.getInstance();
        int yearNow = c.get(Calendar.YEAR);
        int monthNow = c.get(Calendar.MONTH);
        System.out.println(yearNow);
        System.out.println(monthNow);

        int numOfYear = (yearNow - 2013);

        //add month and year to array in reverse order
        for (int count = 0; count < numOfYear; count++) {
            yearList.add(String.valueOf(yearNow-count));
        }
        for (int count = 0; count < 12; count++) {
            if ((monthNow-count+1)>=0)
                monthList.add(getMonthName((monthNow-count)+1));
            else
                monthList.add(getMonthName((monthNow-count+1)+12));
        }


        myMonthSpinner = (Spinner)findViewById(R.id.datemonthspinner);
        myYearSpinner = (Spinner)findViewById(R.id.dateyearspinner);
        monthAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, monthList);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myMonthSpinner.setAdapter(monthAdapter);
        myMonthSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                monthChosen=monthAdapter.getItem(arg2);

                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {

                arg0.setVisibility(View.VISIBLE);
            }
        });
        yearAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myYearSpinner.setAdapter(yearAdapter);
        myYearSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                yearChosen=yearAdapter.getItem(arg2);

                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {

                arg0.setVisibility(View.VISIBLE);
            }
        });

    }
    void getMainIntent() {
        Intent intent = getIntent();
        style_id = intent.getIntExtra(MainActivity.MAIN_THEME_STYLE, 0);

    }

    public String getMonthName(int monthIndex) {
        String MonthName;
        switch (monthIndex) {
            case 0:
                MonthName="Decemeber";
                break;
            case 1:
                MonthName="January";
                break;
            case 2:
                MonthName="February";
                break;
            case 3:
                MonthName="March";
                break;
            case 4:
                MonthName="April";
                break;
            case 5:
                MonthName="May";
                break;
            case 6:
                MonthName="June";
                break;
            case 7:
                MonthName="July";
                break;
            case 8:
                MonthName="August";
                break;
            case 9:
                MonthName="September";
                break;
            case 10:
                MonthName="October";
                break;
            case 11:
                MonthName="November";
                break;

            default:
                MonthName="Null";
                break;
        }
        return MonthName;
    }
}
