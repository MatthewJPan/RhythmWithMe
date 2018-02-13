package com.example.mac.uemr;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.didikee.uitoast.UIToast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class AddRecordActivity extends AppCompatActivity {
    Intent mIntent;
    private AutoCompleteTextView diagnosisField;
    private EditText descriptionField;

    //arrays of disease id, name and description list, data retrived from database
    ArrayList<String> diseaseIDList;
    ArrayList<String> diseaseList;
    ArrayList<String> descriptionList;

    //array of name value pair will be used to generate Jason object
    ArrayList<NameValuePair> params;

    TextView ClearButton;
    TextView AddButton;
    private EditText datePickerField;

    //current year, month and date get from system will be stored in the followin three integers
    private int yearNow;
    private int monthNow;
    private int dayNow;

    private InputStream is;

    //username and id retrived from previous activity
    private String userName;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        //retireve username and id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("username");
            //userID=extras.getString("userID");
        }

        userID="1";
        getPatientID();
        mIntent = new Intent(getApplicationContext(), MainActivity.class);
        mIntent.putExtra("username",userName);
        diseaseIDList=new ArrayList<>();
        diseaseList=new ArrayList<>();
        descriptionList=new ArrayList<>();
        getDataDisease();

        //enable the use of Jason to upload data to database in API 5.
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        //get current date and show as default
        Calendar c = Calendar.getInstance();
        yearNow = c.get(Calendar.YEAR);
        monthNow = c.get(Calendar.MONTH)+1;
        dayNow = c.get(Calendar.DATE);

        diagnosisField=(AutoCompleteTextView)findViewById(R.id.diagnosisfieldadd);
        descriptionField=(EditText)findViewById(R.id.descriptionadd);

        ImageButton datePickerButton= (ImageButton)findViewById(R.id.imageButtoncalendar);
        datePickerField= (EditText)findViewById(R.id.datefieldadd);
        datePickerField.setText(yearNow + "-" + monthNow + "-" + dayNow);

        //open a datepicker dialog when the button is clicked
        datePickerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRecordActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                        datePickerField.setText( year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                    }
                }, yearNow, monthNow-1, dayNow);

                datePickerDialog.show();
            }
        });

        //get disease list from database and set diagnosis field autofile according to this list
        ArrayAdapter<String> AutoFillAdapter = new
                ArrayAdapter <String>(this,android.R.layout.simple_list_item_1,diseaseList);

        diagnosisField.setAdapter(AutoFillAdapter);
        diagnosisField.setThreshold(0);

        descriptionField.setOnFocusChangeListener(new AddRecordActivity.DescriptionOnFocusChanageListener());
        AddButton=(TextView)findViewById(R.id.addbutton);
        ClearButton=(TextView)findViewById(R.id.clearbutton);
        AddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s=diagnosisField.getText().toString();
                Boolean diseaseExistFlag=false;
                for (int i=0; i<diseaseList.size();i++) {
                    if (s.equals(diseaseList.get(i))) {
                        diseaseExistFlag=true;
                    }
                    else{

                    }
                }
                if (diseaseExistFlag) {

                    String description=descriptionField.getText().toString();
                    String diseaseID=diseaseIDList.get(diseaseList.indexOf(diagnosisField.getText().toString()));
                    String date=datePickerField.getText().toString();
                    params = new ArrayList<NameValuePair>();

                    params.add(new BasicNameValuePair("patient_id", userID));
                    params.add(new BasicNameValuePair("disease_id", diseaseID));
                    params.add(new BasicNameValuePair("description", description));
                    params.add(new BasicNameValuePair("date",date));
                    params.add(new BasicNameValuePair("type","1"));

                    JSONParser jsonParser = new JSONParser();

                    String url_up = "http://192.168.43.134/record_add.php";
                    try{
                        JSONObject json = jsonParser.makeHttpRequest(url_up,"POST", params);
                        Log.v("uploadsucceed", "uploadsucceed");
                        UIToast.showStyleToast(AddRecordActivity.this,"The medical record is successfully added",false, Color.WHITE,getResources().getColor(R.color.darkGreen), UIToast.NONE);
                        startActivity(mIntent);
                        finish();
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }
                else{
                    UIToast.showStyleToast(AddRecordActivity.this,"diagnosis not in system, please select one in drop down box",false, Color.WHITE,getResources().getColor(R.color.darkYellow), UIToast.NONE);
                }
            }
        });

        ClearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                descriptionField.setText("");
                diagnosisField.setText("");
                datePickerField.setText(yearNow + "-" + monthNow + "-" + dayNow);
            }
        });


    }

    //get default description according to disease name when the filed is clicked.
    private class DescriptionOnFocusChanageListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (view.getId() == descriptionField.getId()) {
                String s = diagnosisField.getText().toString();

                for (int i = 0; i < diseaseList.size(); i++) {
                    if (s.equals(diseaseList.get(i))) {
                        descriptionField.setText(descriptionList.get(i));

                    }
                }
            }
        }
    }

    public void  getPatientID(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{

            String link="http://192.168.43.134/patient_id.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(userName, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));


            String line = null;

            while((reader.readLine()) != null) {
                line=reader.readLine();
                userID=line.trim();
                break;
            }

            System.out.println(line+"******");

        } catch(Exception e){

        }
            }
        }).start();
    }
    public void getDataDisease() {

        new Thread(new Runnable(){
            @Override
            public void run() {

                try{

                    String link="http://192.168.43.134/patient_id.php";
                    String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                            URLEncoder.encode(userName, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));


                    String line = null;

                    while((reader.readLine()) != null) {
                        line=reader.readLine();
                        userID=line.trim();
                        break;
                    }

                    System.out.println(line+"******");

                } catch(Exception e){

                }

                String result = "";

                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://192.168.43.134/disease_select.php");
                    //httppost.setEntity(new UrlEncodedFormEntity(searchID));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                }catch(Exception e){
                    Log.e("log_tag", "Error in http connection "+e.toString());
                }

                try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);

                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();

                    result=sb.toString();
                }catch(Exception e){
                    Log.e("log_tag", "Error converting result "+e.toString());
                }
                System.out.println(result);

                try{
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag","disease_name: "+json_data.getString("disease_name"));
                        diseaseIDList.add(json_data.getString("disease_id"));
                        diseaseList.add(json_data.getString("disease_name"));
                        descriptionList.add(json_data.getString("disease_description"));
                    }

                }catch(JSONException e){
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }
            }
        }).start();

    }
}
