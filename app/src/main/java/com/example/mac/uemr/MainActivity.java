package com.example.mac.uemr;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

//https://github.com/didikee/UIToast
//an open source customized toast class
import com.didikee.uitoast.UIToast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.*;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.mac.uemr.NetBroadcastReceiver;
import com.example.mac.uemr.NetBroadcastReceiver.netEventHandler;

import static android.R.attr.color;
import static android.R.attr.entries;
import static android.R.attr.phoneNumber;
import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements OnClickListener,OnPageChangeListener,SwipeRefreshLayout.OnRefreshListener {

    private InputStream is;

    private String userName;
    // 4 Linearlayout in bottom menu
    private LinearLayout layout_record;
    private LinearLayout layout_pharmacy;
    private LinearLayout layout_statistics;
    private LinearLayout layout_profile;

    // 4 ImageView in bottom menu
    private ImageView image_record;
    private ImageView image_pharmacy;
    private ImageView image_statistics;
    private ImageView image_profile;

    // 4 TextView in bottom menu
    private TextView text_record;
    private TextView text_pharmacy;
    private TextView text_statistics;
    private TextView text_profile;

    // middle area
    private ViewPager viewPager;

    private View page_01;
    private View page_02;
    private View page_03;
    private View page_04;

    // ViewPager ContentAdapter
    private ContentAdapter adapter;

    private List<View> views;

    private ImageButton addActivityButton;

    private ArrayList<BeanClassForRecyclerView_EMR> beanClassForListViewArrayRecyclerHistroy_emr;
    private SwipeRefreshLayout mSwipeRefreshLayout_emr;
    private ArrayList<BeanClassForRecyclerView_EMR> rawList_emr;
    //private ArrayList<BeanClassForRecyclerView_EMR> rawList_emr_search;
    private RecyclerView recyclerView_emr;
    private MyRecycleAdapter_EMR mAdapter_emr;

    private ArrayList<BeanClassForRecyclerView_Phmc> beanClassForListViewArrayRecyclerHistroy_phmc;
    private SwipeRefreshLayout mSwipeRefreshLayout_Phmc;
    // store the information from database
    private ArrayList<BeanClassForRecyclerView_Phmc> rawList_phmc;
    //store the information ready to present in UI
    private RecyclerView recyclerView_phmc;
    private MyRecycleAdapter_Phmc mAdapter_phmc;
    private int[] medicineImages={R.drawable.medicineexample, R.drawable.medicineexample2, R.drawable.medicineexample3,R.drawable.medicineexample4,R.drawable.pharmacy};

    private EditText searchEMR;
    private ImageButton clearSearchEMR;

    private EditText searchPhmc;
    private ImageButton clearSearchPhmc;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private Spinner myMonthSpinner;
    private List<String> monthList = new ArrayList<String>();
    private ArrayAdapter<String> monthAdapter;

    private Spinner myYearSpinner;
    private List<String> yearList = new ArrayList<String>();
    private ArrayAdapter<String> yearAdapter;

    //the month and year picked by user
    private String monthChosen=" ";
    private String yearChosen=" ";

    private TextView timePeriod;
    public static int MAIN_THEME_STYLE_ID = R.style.AppTheme;
    public static String MAIN_THEME_STYLE = "App_Theme";

    //current date obtained in system
    private int yearNow;
    private int monthNow;
    private int dayNow;

    //list of name value pair for creating Jason object and update user profile
    ArrayList<NameValuePair> params;
    ArrayList<BarEntry> entries;

    //labels for bar chart
    ArrayList <String> labels;
    //colocrs for bar chart
    List<Integer> colors;
    private TextView timeLabel;
    //a button used to reload statistics data from database
    private ImageButton statisticsRefreshButton;

    TextView QuitButton;
    TextView SaveButton;
    EditText PasswordField;
    EditText AllgeryField;
    String ID;
    String password;
    String gender;
    String allgery;
    TextView IDView;
    RadioButton Male;
    RadioButton Female;
    RadioButton OtherGender;
    private getDataStatistic StatisticTask;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("username");
        }

        rawList_phmc=new ArrayList<>();
        rawList_emr=new ArrayList<>();

        // initialize views
        initView();
        // initialize events
        initEvent();
        addActivityButton=(ImageButton)page_01.findViewById(R.id.imageButtonAdd);

        //get current date using Calendar object
        Calendar c = Calendar.getInstance();
        yearNow = c.get(Calendar.YEAR);
        yearChosen=yearNow+"";

        monthNow = c.get(Calendar.MONTH)+1;
        monthChosen=getMonthName(monthNow);
        dayNow = c.get(Calendar.DATE);
        timeLabel=(TextView)  page_03.findViewById(R.id.timelabel);
        statisticsRefreshButton=(ImageButton)page_03.findViewById(R.id.imageButtonRefresh);

        //get medical record data from database
        getDataEMR();
        //get pharmacy data from database
        getDataPhmc();

        //entries for bar chart
        entries = new ArrayList<> ();
        labels = new ArrayList <String> ();
        colors = new ArrayList<Integer>();

        PasswordField=(EditText)page_04.findViewById(R.id.passwordfield);
        setCompent();
        searchEMR=(EditText)page_01.findViewById(R.id.search_emr);
        clearSearchEMR=(ImageButton)page_01.findViewById(R.id.imageButtonClearEMR);
        searchPhmc=(EditText)page_02.findViewById(R.id.search_phmc);
        clearSearchPhmc=(ImageButton)page_02.findViewById(R.id.imageButtonClearPhmc);
        QuitButton=(TextView)page_04.findViewById(R.id.quitbutton);
        SaveButton=(TextView)page_04.findViewById(R.id.savebutton);
        AllgeryField=(EditText)page_04.findViewById(R.id.allergyfield);
        allgery=AllgeryField.getText().toString();
        IDView=(TextView)page_04.findViewById(R.id.idfield);
        Male=(RadioButton)page_04.findViewById(R.id.radioButtonM);
        Female=(RadioButton)page_04.findViewById(R.id.radioButtonF);
        OtherGender=(RadioButton)page_04.findViewById(R.id.radioButtonO);
        RadioGroup group = (RadioGroup)page_04.findViewById(R.id.radioGroup);

        //execute task to get current user's profile data from database
        getDataProfile profileTask=new getDataProfile();
        profileTask.execute();
        //set action listener for radio button group
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
                public void onCheckedChanged(RadioGroup arg0, int arg1) {
            int radioButtonId = arg0.getCheckedRadioButtonId();
            //get radio button by ID
            RadioButton rb = (RadioButton)page_04.findViewById(radioButtonId);
            gender=rb.getText().toString();
              }
                   });
        PasswordField.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialogPassword();
                }
            });

        addActivityButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent addActivity=new Intent(getApplicationContext(), AddRecordActivity.class);
                    addActivity.putExtra("username",userName);
                    startActivity(addActivity);
                }
            });
        clearSearchEMR.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
            searchEMR.setText("");
            loadData_EMR(false);
                }
            });
            clearSearchPhmc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    searchPhmc.setText("");
                    loadData_Phmc(false);
                }
            });
        SaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = PasswordField.getText().toString();

                Boolean passwordLengthFlag = false;
                Boolean passwordDigitFlag = false;
                Boolean passwordCharacterFlag = false;
                allgery=AllgeryField.getText().toString();

                if (s.length() > 7) {
                    passwordLengthFlag = true;
                }
                //iterate characters in password
                for (int i = 0; i < s.length(); i++) {
                    //check if password contains digit
                    if (Character.isDigit(s.charAt(i))) {
                        passwordDigitFlag = true;
                    }
                    //check if password contains letter
                    if (Character.isLetter(s.charAt(i))) {
                        passwordCharacterFlag = true;
                    }
                }

                //if the data fits requirements, update to databse using Jason
                if (passwordDigitFlag == true && passwordCharacterFlag == true && passwordLengthFlag == true) {
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("patient_id", ID));
                    params.add(new BasicNameValuePair("gender", gender));
                    params.add(new BasicNameValuePair("password", password));
                    params.add(new BasicNameValuePair("allgery",allgery));
                    params.add(new BasicNameValuePair("username",userName));

                    JSONParser jsonParser = new JSONParser();

                    String url_up = "http://192.168.43.134/profile_update.php";
                    try{
                        JSONObject json = jsonParser.makeHttpRequest(url_up,"POST", params);
                        Log.v("uploadsucceed", "uploadsucceed");
                        UIToast.showStyleToast(MainActivity.this,"Your profile is successfully updated",false, Color.WHITE,getResources().getColor(R.color.darkGreen), UIToast.NONE);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else {
                    UIToast.showStyleToast(MainActivity.this,"please enter a new password that is more than 7 digit, with both characters and numbers",false, Color.WHITE,getResources().getColor(R.color.darkRed), UIToast.NONE);

                }
            }
        });

        QuitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDataProfile profileTask=new getDataProfile();
                profileTask.execute();
                UIToast.showStyleToast(MainActivity.this,"your profile has been reset",false, Color.WHITE,getResources().getColor(R.color.darkGreen), UIToast.NONE);

            }
        });
        timeLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               showDialog();
            }
        });
            statisticsRefreshButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getDataStatistic newStatisticTask = new getDataStatistic();
                    newStatisticTask.execute();
                }
            });

            addTextListener();

    }

    //test watcher that implements searchable recycler view
    public void addTextListener() {

        searchEMR.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();
                final ArrayList<BeanClassForRecyclerView_EMR> filteredList = new ArrayList<>();
                for (int i = 0; i < rawList_emr.size(); i++) {
                    final String diseaseName = rawList_emr.get(i).getDiseaseName().toLowerCase();
                    final String description = rawList_emr.get(i).getDescription().toLowerCase();
                    final String medcineName = rawList_emr.get(i).getMedicineName().toLowerCase();
                    final String date = rawList_emr.get(i).getDate().toLowerCase();

                    if (diseaseName.contains(query)||description.contains(query)||medcineName.contains(query)||date.contains(query)) {
                        filteredList.add(rawList_emr.get(i));
                    }
                }
                beanClassForListViewArrayRecyclerHistroy_emr=new ArrayList<BeanClassForRecyclerView_EMR>();
                //reset adapter of recycler view to filtered list
                setSearchAdapter_EMR(filteredList);
            }
        });

        searchPhmc.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final ArrayList<BeanClassForRecyclerView_Phmc> filteredList = new ArrayList<>();

                for (int i = 0; i < rawList_phmc.size(); i++) {

                    final String medcineName = rawList_phmc.get(i).getMedcineName().toLowerCase();
                    final String medcineFunction = rawList_phmc.get(i).getFunction().toLowerCase();

                    if (medcineName.contains(query)||medcineFunction.contains(query)) {
                        filteredList.add(rawList_phmc.get(i));
                    }
                }

                beanClassForListViewArrayRecyclerHistroy_phmc=new ArrayList<BeanClassForRecyclerView_Phmc>();
                setSearchAdapter_Phmc(filteredList);
            }
        });
    }

    //get statistics data from database and present in bar chart
    private class getDataStatistic extends AsyncTask<String, Integer, String> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            System.out.println("onPreExecute() called");

        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            entries.clear();
            labels.clear();
            colors.clear();

            String result = "";

            try{

                String link="http://192.168.43.134/month_disease.php";
                String data  = URLEncoder.encode("year", "UTF-8") + "=" +
                        URLEncoder.encode((yearChosen)+"", "UTF-8");
                data += "&" + URLEncoder.encode("month", "UTF-8") + "=" +
                        URLEncoder.encode(getMonthNumber(monthChosen)+"", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //is.close();

                result=sb.toString();
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }
            System.out.println(result);
            // result=result.trim();

            try{
                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag",json_data.getInt("ICOUNT")+json_data.getString("disease_name")+"_____*****_______");
                    entries.add(new BarEntry(json_data.getInt("ICOUNT"),i));
                    labels.add(json_data.getString("disease_name"));
                    //labels.add("disease_name");
                    colors.add(Color.BLUE);
                }

            }catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

            result = "";

            try{

                String link="http://192.168.43.134/month_disease_self.php";
                String data  = URLEncoder.encode("year", "UTF-8") + "=" +
                        URLEncoder.encode((yearChosen)+"", "UTF-8");
                data += "&" + URLEncoder.encode("month", "UTF-8") + "=" +
                        URLEncoder.encode(getMonthNumber(monthChosen)+"", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //is.close();

                result=sb.toString();
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }
            System.out.println(result);
            // result=result.trim();
            int entrySize=entries.size();
            try{
                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag",json_data.getString("ICOUNT")+json_data.getString("disease_name")+"_____*****_______");
                    entries.add(new BarEntry(json_data.getInt("ICOUNT"),i+entrySize));
//                        int indexTemp=labels.indexOf(json_data.getString("disease_name"));
//                        entries.add(new BarEntry(json_data.getInt("ICOUNT"),indexTemp+1));
                    labels.add(json_data.getString("disease_name"));
                    colors.add(Color.rgb(216, 231, 255));
                }

            }catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {

        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            BarChart barChart = (BarChart) page_03.findViewById(R.id.chart);
            BarDataSet dataset = new BarDataSet(entries, "");
                    //"Visits (clinics reports/ self reports)");
            //set data
            //Collections.reverse(labels);
//            ArrayList<String> rlist = new ArrayList<>(labels);
//            System.out.println(labels+"llllllllllllllllllllllllllllllllll");
//            //Collections.reverse(rlist);
//            int relistSize=rlist.size()/2;
//            for(int i=0;i<relistSize;i++){
//                String temp=new String(rlist.get(i));
//                rlist.set(i,rlist.get(relistSize+i));
//                rlist.set(relistSize+i,temp);
//            }
//            System.out.println(rlist+"rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            BarData data = new BarData(labels,dataset);
            barChart.setDescription("");
            barChart.getLegend().setEnabled(false);
            barChart.setData(data);
            dataset.setColors(colors);
            //set animation
            barChart.animateY(1500);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }
    //get profile data from database and present in profile page
    private class getDataProfile extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            System.out.println("onPreExecute() called");

        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try{

                String link="http://192.168.43.134/profile_select.php";
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

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //is.close();

                result=sb.toString();
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }
            System.out.println(result);


            try{
                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    ID=json_data.getString("patient_id");
                    password=json_data.getString("DECODE(`password`, CONCAT('uemr','test'))");
                    gender=json_data.getString("gender");
                    allgery=json_data.getString("allgery");
System.out.println(password+"***&&&&&****************pass");
                }

            }catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {

        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            AllgeryField.setText(allgery);
            IDView.setText(ID);
            if (gender.equals("Male")){
                Male.setChecked(true);
                Female.setChecked(false);
                OtherGender.setChecked(false);
            }
            else if (gender.equals("Female")){
                Male.setChecked(false);
                Female.setChecked(true);
                OtherGender.setChecked(false);
            }
            else{
                Male.setChecked(false);
                Female.setChecked(false);
                OtherGender.setChecked(true);
            }

        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }
    //get medical record data from database
    public void getDataEMR() {

        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = "";

                try{

                    String link="http://192.168.43.134/emr_select.php";
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

                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    //is.close();

                    result=sb.toString();
                }catch(Exception e){
                    Log.e("log_tag", "Error converting result "+e.toString());
                }
                System.out.println(result);
                // result=result.trim();

                try{
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag",json_data.getString("histroy_id")+json_data.getString("disease_name")+
                                json_data.getString("description")+ json_data.getString("out_quantity")+
                                json_data.getString("medicine_name")+ json_data.getString("price")+json_data.getString("date"));

                        if (json_data.getString("type").equals("1")){
                            rawList_emr.add(new BeanClassForRecyclerView_EMR(
                                    "history NO."+json_data.getString("histroy_id"), json_data.getString("disease_name"),
                                    json_data.getString("description"), " ",
                                    " ", " ",json_data.getString("date"),
                                    true));

                        }
                        else {
                            rawList_emr.add(new BeanClassForRecyclerView_EMR(
                                    "history NO." + json_data.getString("histroy_id"), json_data.getString("disease_name"),
                                    json_data.getString("description"), "medicine quantity: " + json_data.getString("out_quantity"),
                                    json_data.getString("medicine_name"), "¥" + json_data.getString("price"), json_data.getString("date")
                                    ,false));
                        }
                       // userID=json_data.getString("patient_id");
                    }

                }catch(JSONException e){
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }
            }
        }).start();

    }
    //get pharmacy data from database
    public void getDataPhmc() {

        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = "";

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.134/pharmacy_select.php");
            //httppost.setEntity(new UrlEncodedFormEntity(searchID));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
//将HttpEntity转化为String
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
               // result=result.trim();
//将String通过JSONArray解析成最终结果
        try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                if(i<5) {
                    Log.i("log_tag", "medicine_id: " + json_data.getString("medicine_id") +
                            ", medicine_name: " + json_data.getString("medicine_name") +
                            ", price: " + json_data.getInt("price"));
                    System.out.println("medicine_id: " + json_data.getString("medicine_id") +
                            ", medicine_name: " + json_data.getString("medicine_name") +
                            ", price: " + json_data.getDouble("price"));
                    rawList_phmc.add(new BeanClassForRecyclerView_Phmc(medicineImages[i],
                            "medicine#:" + json_data.getString("medicine_id"), json_data.getString("medicine_name"), json_data.getInt("quantity"), json_data.getDouble("price"), json_data.getString("medicine_function")));
                }
                else{
                    rawList_phmc.add(new BeanClassForRecyclerView_Phmc(medicineImages[4],
                            "medicine#:" + json_data.getString("medicine_id"), json_data.getString("medicine_name"), json_data.getInt("quantity"), json_data.getDouble("price"), json_data.getString("medicine_function")));

                }
                }

    }catch(JSONException e){
        Log.e("log_tag", "Error parsing data "+e.toString());
    }
            }
        }).start();

    }


    public void onToBackActivity(View view) {
        Intent intentDate = new Intent(this, DatePickerActivity.class);
        intentDate.putExtra(MAIN_THEME_STYLE, MAIN_THEME_STYLE_ID);
        startActivity(intentDate);
        Intent intentSearch = new Intent(this, SearchActivity.class);
        intentSearch.putExtra(MAIN_THEME_STYLE, MAIN_THEME_STYLE_ID);
        startActivity(intentSearch);
    }

    //get action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //add items for action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call:
                //go to call page, with phone number
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "057755870120"));
                startActivity(dialIntent);
                return true;
            case R.id.seekhelp:
                Intent seekIntent =  new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(seekIntent);
                return true;
//            case R.id.choosetime:
//                Intent pickIntent =  new Intent(getApplicationContext(), DatePickerActivity.class);
//                startActivity(pickIntent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCompent(){
        //initialize medical record recycler view
        mSwipeRefreshLayout_emr = (SwipeRefreshLayout) page_01.findViewById(R.id.swiperefreshlayout_emr);
        mSwipeRefreshLayout_emr.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout_emr.setOnRefreshListener(this);
        recyclerView_emr = (RecyclerView) page_01.findViewById(R.id.recycleviewrecord);
        System.out.println(recyclerView_emr);
        beanClassForListViewArrayRecyclerHistroy_emr = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView_emr.setLayoutManager(mLayoutManager);
        recyclerView_emr.setItemAnimator(new DefaultItemAnimator());
        WrapContentLinearLayoutManager mLayoutManager_emr = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 6000;
            }
        };
        recyclerView_emr.setLayoutManager(mLayoutManager_emr);
        recyclerView_emr.setItemAnimator(new DefaultItemAnimator());
        recyclerView_emr.setAdapter(mAdapter_emr);

        mSwipeRefreshLayout_emr.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout_emr.setRefreshing(true);
            }
        });
        loadData_EMR(false);

        //initialize medical record recycler view
        mSwipeRefreshLayout_Phmc = (SwipeRefreshLayout) page_02.findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout_Phmc.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout_Phmc.setOnRefreshListener(this);
        recyclerView_phmc = (RecyclerView) page_02.findViewById(R.id.recycleviewphmc);
        beanClassForListViewArrayRecyclerHistroy_phmc = new ArrayList<>();

        WrapContentLinearLayoutManager mLayoutManager_phmc = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 6000;
            }
        };
        recyclerView_phmc.setLayoutManager(mLayoutManager_phmc);
        recyclerView_phmc.setItemAnimator(new DefaultItemAnimator());
        recyclerView_phmc.setAdapter(mAdapter_phmc);

        mSwipeRefreshLayout_Phmc.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout_Phmc.setRefreshing(true);
            }
        });
        loadData_Phmc(false);

        //get year and month
        timePeriod=(TextView) page_03.findViewById(R.id.timelabel);
        Calendar c = Calendar.getInstance();
        yearNow = c.get(Calendar.YEAR);
        monthNow = c.get(Calendar.MONTH)+1;
        dayNow = c.get(Calendar.DATE);
        String monthNowString=getMonthName(monthNow);
        timePeriod.setText( monthNowString+", "+yearNow);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    //load medical record data to recycler view
    private void loadData_EMR(final boolean isMore){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout_emr.setRefreshing(false);
                            if(isMore){
                                mAdapter_emr.setLoaded();
                                if(beanClassForListViewArrayRecyclerHistroy_emr.size()>0) {
                                    beanClassForListViewArrayRecyclerHistroy_emr.remove(beanClassForListViewArrayRecyclerHistroy_emr.size() - 1);
                                }
                                mAdapter_emr.notifyItemRemoved(beanClassForListViewArrayRecyclerHistroy_emr.size());
                            }
                            //**********

                            int index = beanClassForListViewArrayRecyclerHistroy_emr.size();
//                            if(rawList_emr.size()-beanClassForListViewArrayRecyclerHistroy_emr.size()>10) {
//                                for (int i = index; i < index + 10; i++) {
//                                    beanClassForListViewArrayRecyclerHistroy_emr.add(rawList_emr.get(i));
//                                }
//                            }
//                            else{
                                for (int i = 0; i < rawList_emr.size(); i++) {

                                    beanClassForListViewArrayRecyclerHistroy_emr.add(rawList_emr.get(i));
                                }
//                            }
                            setAdapter_EMR();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //load pharmacy data to recycler view
    private void loadData_Phmc(final boolean isMore){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout_Phmc.setRefreshing(false);
                            if(isMore){
                                mAdapter_phmc.setLoaded();
                                if(beanClassForListViewArrayRecyclerHistroy_phmc.size()>0) {
                                    beanClassForListViewArrayRecyclerHistroy_phmc.remove(beanClassForListViewArrayRecyclerHistroy_phmc.size() - 1);
                                }
                                mAdapter_phmc.notifyItemRemoved(beanClassForListViewArrayRecyclerHistroy_phmc.size());
                            }
                            //**********

                            int index = beanClassForListViewArrayRecyclerHistroy_phmc.size();
//                            if(rawList_phmc.size()-beanClassForListViewArrayRecyclerHistroy_phmc.size()>10) {
//                                for (int i = index; i < index + 10; i++) {
//
//                                    //new BeanClassForRecyclerView_Phmc(R.drawable.pharmacy, i+"", "name", "inventory", "price");
//
//                                    //System.out.println(mPharmacy+"****************");
////                                System.out.println(mPharmacy.getMedicineID().get(i)+"****************");
//
////                                BeanClassForRecyclerView_Phmc beanClassForRecyclerViewPhmc_contacts
////                                        = new BeanClassForRecyclerView_Phmc(R.drawable.pharmacy,mPharmacy.getMedicineID().get(i), mPharmacy.getMedicineName().get(i), mPharmacy.getInventory().get(i), mPharmacy.getMedicinePrice().get(i));
////
////                                beanClassForListViewArrayRecyclerHistroy_phmc.add(beanClassForRecyclerViewPhmc_contacts);
//
//                                    beanClassForListViewArrayRecyclerHistroy_phmc.add(rawList_phmc.get(i));
//                                }
//                            }
//                            else{
                                for (int i = index; i < rawList_phmc.size(); i++) {

                                    //new BeanClassForRecyclerView_Phmc(R.drawable.pharmacy, i+"", "name", "inventory", "price");

                                    //System.out.println(mPharmacy+"****************");
//                                System.out.println(mPharmacy.getMedicineID().get(i)+"****************");

//                                BeanClassForRecyclerView_Phmc beanClassForRecyclerViewPhmc_contacts
//                                        = new BeanClassForRecyclerView_Phmc(R.drawable.pharmacy,mPharmacy.getMedicineID().get(i), mPharmacy.getMedicineName().get(i), mPharmacy.getInventory().get(i), mPharmacy.getMedicinePrice().get(i));
//
//                                beanClassForListViewArrayRecyclerHistroy_phmc.add(beanClassForRecyclerViewPhmc_contacts);

                                    beanClassForListViewArrayRecyclerHistroy_phmc.add(rawList_phmc.get(i));
                                }
//                            }
                            setAdapter_Phmc();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void setAdapter_Phmc(){
        if(mAdapter_phmc==null){
            mAdapter_phmc = new MyRecycleAdapter_Phmc(this,beanClassForListViewArrayRecyclerHistroy_phmc,recyclerView_phmc);
            recyclerView_phmc.setAdapter(mAdapter_phmc);
//            mAdapter_phmc.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore() {
//                    beanClassForListViewArrayRecyclerHistroy_phmc.add(null);
//                    mAdapter_phmc.notifyItemInserted(beanClassForListViewArrayRecyclerHistroy_phmc.size() - 1);
//                    loadData_Phmc(true);
//                    //loadData_Phmc(false);
//                    mAdapter_phmc.setLoading();
//                }
//            });
        }else{
            mAdapter_phmc.notifyDataSetChanged();
        }
    }

    private void setAdapter_EMR(){

        if(mAdapter_emr==null){
            mAdapter_emr = new MyRecycleAdapter_EMR(this,beanClassForListViewArrayRecyclerHistroy_emr,recyclerView_emr);
            recyclerView_emr.setAdapter(mAdapter_emr);
//            mAdapter_emr.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore() {
//                    beanClassForListViewArrayRecyclerHistroy_emr.add(null);
//                    mAdapter_emr.notifyItemInserted(beanClassForListViewArrayRecyclerHistroy_emr.size() - 1);
//                    loadData_EMR(true);
//                    mAdapter_emr.setLoading();
//                }
//            });
        }else{
            mAdapter_emr.notifyDataSetChanged();
        }
    }

    private void setSearchAdapter_EMR(final ArrayList<BeanClassForRecyclerView_EMR> searchList){

//        if(mAdapter_emr==null){
                            mAdapter_emr = new MyRecycleAdapter_EMR(MainActivity.this,searchList,recyclerView_emr);
                            recyclerView_emr.setAdapter(mAdapter_emr);
//                            mAdapter_emr.setOnLoadMoreListener(new OnLoadMoreListener() {
//                            @Override
//                                public void onLoadMore() {
//                                beanClassForListViewArrayRecyclerHistroy_emr.add(null);
//                                    mAdapter_emr.notifyItemInserted(beanClassForListViewArrayRecyclerHistroy_emr.size() - 1);
//                                    loadData_EMR(true);
//                                    mAdapter_emr.setLoading();
//                                }
//                            });
//        }else{
//            mAdapter_emr.notifyDataSetChanged();
//        }
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    private void setSearchAdapter_Phmc(final ArrayList<BeanClassForRecyclerView_Phmc> searchList){

        mAdapter_phmc = new MyRecycleAdapter_Phmc(MainActivity.this,searchList,recyclerView_phmc);
        recyclerView_phmc.setAdapter(mAdapter_phmc);
    }

    @Override
    public void onRefresh() {

        beanClassForListViewArrayRecyclerHistroy_phmc.clear();
        searchPhmc.setText("");
        mAdapter_phmc=null;
        loadData_Phmc(false);
        beanClassForListViewArrayRecyclerHistroy_emr.clear();


        searchEMR.setText("");
        mAdapter_emr=null;
        loadData_EMR(false);
        //setAdapter_EMR();
    }


    private void initEvent() {
        // edit action listener
        layout_record.setOnClickListener(this);
        layout_pharmacy.setOnClickListener(this);
        layout_statistics.setOnClickListener(this);
        layout_profile.setOnClickListener(this);

        // edit ViewPager listener
        viewPager.setOnPageChangeListener(this);
    }

    private void initView() {

        // 4 Linearlayout
        this.layout_record = (LinearLayout) findViewById(R.id.ll_record);
        this.layout_pharmacy = (LinearLayout) findViewById(R.id.ll_pharmacy);
        this.layout_statistics = (LinearLayout) findViewById(R.id.ll_statistics);
        this.layout_profile = (LinearLayout) findViewById(R.id.ll_profile);

        // 4 ImageView
        this.image_record = (ImageView) findViewById(R.id.iv_record);
        this.image_pharmacy = (ImageView) findViewById(R.id.iv_pharmacy);
        this.image_statistics = (ImageView) findViewById(R.id.iv_statistics);
        this.image_profile = (ImageView) findViewById(R.id.iv_profile);

        // 4 Textview
        this.text_record = (TextView) findViewById(R.id.tv_record);
        this.text_pharmacy = (TextView) findViewById(R.id.tv_pharmacy);
        this.text_statistics = (TextView) findViewById(R.id.tv_statistics);
        this.text_profile = (TextView) findViewById(R.id.tv_profile);

        // ViewPager
        this.viewPager = (ViewPager) findViewById(R.id.id_viewpage);


        page_01 = View.inflate(MainActivity.this, R.layout.record, null);
        page_02 = View.inflate(MainActivity.this, R.layout.pharmacy, null);
        page_03 = View.inflate(MainActivity.this, R.layout.statistics, null);
        page_04 = View.inflate(MainActivity.this, R.layout.profile, null);

        views = new ArrayList<View>();
        views.add(page_01);
        views.add(page_02);
        views.add(page_03);
        views.add(page_04);

        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        // change color of (ImageView,TextView)to grey，then change the source of click to blue
        restartBotton();
        // ImageView and TetxView to blue
        switch (v.getId()) {
            case R.id.ll_record:
                image_record.setImageResource(R.drawable.medicalrecord_pressed);
                text_record.setTextColor(0xffC2FDFD);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_pharmacy:
                image_pharmacy.setImageResource(R.drawable.pharmacy_pressed);
                text_pharmacy.setTextColor(0xffC2FDFD);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_statistics:
                image_statistics.setImageResource(R.drawable.statistics_pressed);
                text_statistics.setTextColor(0xffC2FDFD);

                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_profile:
                image_profile.setImageResource(R.drawable.profile_pressed);
                text_profile.setTextColor(0xffC2FDFD);
                viewPager.setCurrentItem(3);
                break;

            default:
                break;
        }

    }

    private void restartBotton() {
        // ImageView to grey
        image_record.setImageResource(R.drawable.medicalrecord);
        image_pharmacy.setImageResource(R.drawable.pharmacy);
        image_statistics.setImageResource(R.drawable.statistics);
        image_profile.setImageResource(R.drawable.profile);
        // TextView to grey
        text_record.setTextColor(0xffd9d9d9);
        text_pharmacy.setTextColor(0xffd9d9d9);
        text_statistics.setTextColor(0xffd9d9d9);
        text_profile.setTextColor(0xffd9d9d9);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        restartBotton();
        //when the view is chosen, change the text color and image icon
        switch (arg0) {
            case 0:
                image_record.setImageResource(R.drawable.medicalrecord_pressed);
                text_record.setTextColor(0xffC2FDFD);
                break;
            case 1:
                image_pharmacy.setImageResource(R.drawable.pharmacy_pressed);
                text_pharmacy.setTextColor(0xffC2FDFD);
                break;
            case 2:
                image_statistics.setImageResource(R.drawable.statistics_pressed);
                text_statistics.setTextColor(0xffC2FDFD);
                StatisticTask=new getDataStatistic();
                StatisticTask.execute();
                break;
            case 3:
                image_profile.setImageResource(R.drawable.profile_pressed);
                text_profile.setTextColor(0xffC2FDFD);
                break;

            default:
                break;
        }

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
                MonthName="NULL";
                break;
        }
        return MonthName;
    }

    public int getMonthNumber(String monthName) {
        int MonthIndex;
        switch (monthName) {
            case "Decemeber":
                MonthIndex=0;
                break;
            case "January":
                MonthIndex=1;
                break;
            case "February":
                MonthIndex=2;
                break;
            case "March":
                MonthIndex=3;
                break;
            case "April":
                MonthIndex=4;
                break;
            case "May":
                MonthIndex=5;
                break;
            case "June":
                MonthIndex=6;
                break;
            case "July":
                MonthIndex=7;
                break;
            case "August":
                MonthIndex=8;
                break;
            case "September":
                MonthIndex=9;
                break;
            case "October":
                MonthIndex=10;
                break;
            case "November":
                MonthIndex=11;
                break;

            default:
                MonthIndex=-1;
                break;
        }
        return MonthIndex;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        //get customized layout
        final View layout = inflater.inflate(R.layout.month_picker, null);
        builder.setView(layout);
        builder.setIcon(R.drawable.calendar);
        builder.setTitle("Please Pick a Month");
        myMonthSpinner = (Spinner) layout.findViewById(R.id.monthspinner);
        myYearSpinner= (Spinner) layout.findViewById(R.id.yearspinner);
        Button okButton = (Button)layout.findViewById(R.id.okbuttonmonthpicker);
        Button cancleButton = (Button)layout.findViewById(R.id.canclebuttonmonthpicker);
        final AlertDialog dlg = builder.create();
        int numOfYear = (yearNow - 2013);

        //add month and year to array in reverse order
        for (int count = 0; count < numOfYear; count++) {
            yearList.add(String.valueOf(yearNow-count));
        }
        for (int count = 0; count < 12; count++) {
            if ((monthNow-count+1)>0)
                monthList.add(getMonthName((monthNow-count)+1-1));
            else
                monthList.add(getMonthName((monthNow-count+1-1)+12));
        }

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
                yearChosen=yearAdapter.getItem(arg2);
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });
        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if( Integer.parseInt(yearChosen)<2017||(Integer.parseInt(yearChosen)==2017&&getMonthNumber(monthChosen)<3)||(Integer.parseInt(yearChosen)==yearNow)&&getMonthNumber(monthChosen)>monthNow){
                    UIToast.showStyleToast(MainActivity.this,"Sorry, no record in the system for the month chosen",false, Color.WHITE,getResources().getColor(R.color.darkRed), UIToast.NONE);
                }
                else {
                    timeLabel.setText(monthChosen + ", " + yearChosen);
                    getDataStatistic newStatisticTask = new getDataStatistic();
                    newStatisticTask.execute();
                    dlg.dismiss();
                }
            }
        });
        cancleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                yearChosen=yearNow+"";
                monthChosen=getMonthName(monthNow);
                dlg.dismiss();
            }
        });
        dlg.show();
    }

    //shwo password dialog which require user to enter new password twice
    public void showDialogPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.password_reset, null);
        builder.setView(layout);
        builder.setIcon(R.drawable.password);
        builder.setTitle("Reset Password");
        final EditText newPassword=(EditText)layout.findViewById(R.id.editTextPassword);
        final EditText rePassword=(EditText)layout.findViewById(R.id.editTextAgain);
        Button okButton = (Button)layout.findViewById(R.id.okbuttonpassword);
        Button cancleButton = (Button)layout.findViewById(R.id.canclebuttonpassword);
        final AlertDialog dlg = builder.create();

        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!newPassword.getText().toString().equals(rePassword.getText().toString())){
                    UIToast.showStyleToast(MainActivity.this,"The password you entered does not match, please renter",false, Color.WHITE,getResources().getColor(R.color.darkRed), UIToast.NONE);
                }
                else {

                    password=newPassword.getText().toString();
                    dlg.dismiss();
                }
            }
        });
        cancleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                newPassword.setText("");
                rePassword.setText("");
                dlg.dismiss();
            }
        });

        dlg.show();
    }
}
