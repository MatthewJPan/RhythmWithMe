package com.example.mac.uemr;
/**
 * etsy.android.grid.StaggeredGridView example from http://blog.csdn.net/xu_fu/article/details/20705595
 */

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.didikee.uitoast.UIToast;
import com.etsy.android.grid.StaggeredGridView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private StaggeredGridView mGridView;
    String searchKey;
    Button searchButton;
    AutoCompleteTextView searchField;
    private String[] DATA;
    List<List<String>> transaction;
    String [] [] resultSet = new String [4] [10];
    int reCount=0;
    private MyTask mTask;
    private InputStream is;
    ArrayList<String> diseaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        diseaseList=new ArrayList<>();
        getDataDisease();

        // call a staggered grid view to place search results
        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        searchButton=(Button)findViewById(R.id.buttonsearch);
        searchField=(AutoCompleteTextView)findViewById(R.id.editTextKey);

        //set search filed autofill enabled
        ArrayAdapter<String> AutoFillAdapter = new
                ArrayAdapter <String>(this,android.R.layout.simple_list_item_1,diseaseList);
        searchField.setAdapter(AutoFillAdapter);
        searchField.setThreshold(0);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchKey=searchField.getText().toString();

                Boolean diseaseExistFlag=false;
                for (int i=0; i<diseaseList.size();i++) {
                    if (searchKey.equals(diseaseList.get(i))) {
                        diseaseExistFlag=true;
                    }
                    else{

                    }
                }
                if (diseaseExistFlag) {
                    reCount=0;
                    mTask = new MyTask();
                    mTask.execute();
                }
                else{
                    Toast.makeText(SearchActivity.this,
                            String.valueOf("Sorry your search key is not in the system, please try to search in Baidu"),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //use asyntask to handle UI effects and database operation
    private class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            System.out.println("onPreExecute() called");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<String> temp=new ArrayList<String>();
                String result = "";
                transaction=new LinkedList<List<String>>();

                try{

                    String link="http://192.168.43.134/search_select.php";
                    String data  = URLEncoder.encode("searchKey", "UTF-8") + "=" +
                            URLEncoder.encode(searchKey, "UTF-8");

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

                    result=sb.toString();
                }catch(Exception e){
                    Log.e("log_tag", "Error converting result "+e.toString());
                }
                System.out.println(result);

                //decode Jason and store data in record, later add records to transation
                try{
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag",json_data.getString("disease_name")+
                                json_data.getString("description")+
                                json_data.getString("medicine_name"));
                        List<String> record=new LinkedList<String>();
                        record.add(json_data.getString("disease_name"));
                        record.add(json_data.getString("medicine_name"));
                        record.add(json_data.getString("description"));
                        transaction.add(record);
                    }

                }catch(JSONException e){
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }

                FPGrowth(transaction, null);
//                for(int i=0;i<10;i++){
//                    for (int j=0;j<3;j++){
//                        System.out.println(resultSet[j][i]+" "+i+j+"testtesttest");
//                    }
//                }

                for(int i=0;i<10;i++){
                    StringBuilder sb=new StringBuilder();

                        if(true

                                ){
                            sb.append(resultSet[2][i]);
                    //    }
                    }
                    String tempResult=sb.toString();
                    // add entries that contains search key but is not search key itself to the result list
                    if (!tempResult.equals("")&&tempResult.trim()!=null&&!tempResult.equals(", ")&&!tempResult.contains(", , ")
                            &&!tempResult.contains("null")&&!temp.contains(tempResult)&&!tempResult.contains(searchKey)){
                        temp.add(tempResult);
                    }

                    sb.delete(0,sb.length());

                }
                DATA=new String[temp.size()];
                for (int i=0;i<DATA.length;i++){
                    DATA[i]=temp.get(i);
                }
            } catch (Exception e) {
               Log.e("error", e.getMessage());
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... progresses) {

        }

        @Override
        protected void onPostExecute(String result) {
          //show search result using mGridView
            mGridView.setAdapter(new StaggeredAdapter());
        }

        @Override
        protected void onCancelled() {

        }
    }

    public void getDataDisease() {

        new Thread(new Runnable(){
            @Override
            public void run() {
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
                        Log.i("log_tag","disease_name: "+json_data.getString("disease_name"));
                        diseaseList.add(json_data.getString("disease_name"));
                        //descriptionList.add(json_data.getString("disease_description"));
                    }

                }catch(JSONException e){
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }
            }
        }).start();

    }

    public class StaggeredAdapter extends BaseAdapter {
        @Override
        public int getCount() {
                return DATA.length;
        }

        @Override
        public Object getItem(int position) {
            return DATA[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(SearchActivity.this);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(lp);
            }
            TextView view = (TextView) convertView;
            view.setText(DATA[position]);
           //set color according to relative support within the result sets
            view.setBackgroundColor(COLOR[position / 2]);
            view.setGravity(Gravity.BOTTOM);
            view.setTextColor(Color.BLACK);
            view.setTextSize(20);
            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
            lp.height = (int) (getPositionRatio(position) * 200);
            view.setLayoutParams(lp);
            return view;
        }
        }

    //set colors
    private static final int[] COLOR = new int[] {
            0xff328eff, 0xff32e0ff, 0xff32ffad, 0xff32ff3d, 0xffa9ff32
    };
    private final Random mRandom = new Random();
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0;
    }


    public void FPGrowth(List<List<String>> transRecords,
                         List<String> postPattern) {
        //build header table
        ArrayList<TreeNode> HeaderTable = buildHeaderTable(transRecords);
        // FP tree
        TreeNode treeRoot = buildFPTree(transRecords, HeaderTable);

        if(postPattern!=null){

            for (TreeNode header : HeaderTable) {
                if (reCount < 10) {

                        resultSet[0][reCount] = header.getCount() + "";

                        resultSet[1][reCount] = header.getName();

                        int innercount = 2;
                        for (String ele : postPattern) {
                            resultSet[innercount][reCount] = ele;
                            if (innercount == 2) {
                                innercount = 3;
                            } else {
                                innercount = 2;
                            }
                        }
                        reCount = reCount + 1;
                }
            }
        }
        // get header table and enter recursion
        for (TreeNode header : HeaderTable) {
            List<String> newPostPattern = new LinkedList<String>();
            newPostPattern.add(header.getName());
            if (postPattern != null)
                newPostPattern.addAll(postPattern);
            List<List<String>> newTransRecords = new LinkedList<List<String>>();
            TreeNode backnode = header.getNextHomonym();
            while (backnode != null) {
                int counter = backnode.getCount();
                List<String> prenodes = new ArrayList<String>();
                TreeNode parent = backnode;
                while ((parent = parent.getParent()).getName() != null) {
                    prenodes.add(parent.getName());
                }
                while (counter-- > 0) {
                    newTransRecords.add(prenodes);
                }
                backnode = backnode.getNextHomonym();
            }
            // recursively build FP tree
            FPGrowth(newTransRecords, newPostPattern);
        }
    }

    // construct header table
    public ArrayList<TreeNode> buildHeaderTable(List<List<String>> transRecords) {
        ArrayList<TreeNode> F1 = null;
        if (transRecords.size() > 0) {
            F1 = new ArrayList<TreeNode>();
            Map<String, TreeNode> map = new HashMap<String, TreeNode>();
            // get min support
            for (List<String> record : transRecords) {
                for (String item : record) {
                    if (!map.keySet().contains(item)) {
                        TreeNode node = new TreeNode(item);
                        node.setCount(1);
                        map.put(item, node);
                    } else {
                        map.get(item).countIncrement(1);
                    }
                }
            }
            // add min support to the tree
            Set<String> names = map.keySet();
            for (String name : names) {
                TreeNode tnode = map.get(name);
                if (tnode.getCount() >= 0) {
                    F1.add(tnode);
                }
            }
            Collections.sort(F1);
            return F1;
        } else {
            return null;
        }
    }

    // build FP Tree structure
    public TreeNode buildFPTree(List<List<String>> transRecords,
                                ArrayList<TreeNode> F1) {
        TreeNode root = new TreeNode();
        for (List<String> transRecord : transRecords) {
            LinkedList<String> record = sortByF1(transRecord, F1);
            TreeNode subTreeRoot = root;
            TreeNode tmpRoot = null;
            if (root.getChildren() != null) {
                while (!record.isEmpty()
                        && (tmpRoot = subTreeRoot.findChild(record.peek())) != null) {
                    tmpRoot.countIncrement(1);
                    subTreeRoot = tmpRoot;
                    record.poll();
                }
            }
            addNodes(subTreeRoot, record, F1);
        }
        return root;
    }

    // arrange transaction in descending order
    public LinkedList<String> sortByF1(List<String> transRecord,
                                       ArrayList<TreeNode> F1) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String item : transRecord) {
            for (int i = 0; i < F1.size(); i++) {
                TreeNode tnode = F1.get(i);
                if (tnode.getName().equals(item)) {
                    map.put(item, i);
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> al = new ArrayList<Map.Entry<String, Integer>>(
                map.entrySet());
        // arrange the map in decending order
        Collections.sort(al, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {

                return arg0.getValue() - arg1.getValue();
            }
        });
        LinkedList<String> rest = new LinkedList<String>();
        for (Map.Entry<String, Integer> entry : al) {
            rest.add(entry.getKey());
        }
        return rest;
    }

    // insert record as ancestor
    public void addNodes(TreeNode ancestor, LinkedList<String> record,
                         ArrayList<TreeNode> F1) {
        if (record.size() > 0) {
            while (record.size() > 0) {
                String item = record.poll();
                TreeNode leafnode = new TreeNode(item);
                leafnode.setCount(1);
                leafnode.setParent(ancestor);
                ancestor.addChild(leafnode);

                for (TreeNode f1 : F1) {
                    if (f1.getName().equals(item)) {
                        while (f1.getNextHomonym() != null) {
                            f1 = f1.getNextHomonym();
                        }
                        f1.setNextHomonym(leafnode);
                        break;
                    }
                }

                addNodes(leafnode, record, F1);
            }
        }
    }
}




















































































































































































