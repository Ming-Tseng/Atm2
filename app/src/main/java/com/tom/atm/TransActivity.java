package com.tom.atm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        list = (ListView) findViewById(R.id.list);

        String url = "http://atm201605.appspot.com/h";
        new TransTask().execute(url);
    }

    class TransTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String line = in.readLine();
                while(line!=null){
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("DATA", s);
            List<Map<String, String>> data = new
                    ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for (int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    String account = obj.getString("account");
                    String date = obj.getString("date");
                    int amount = obj.getInt("amount");
                    int type = obj.getInt("type");
                    Log.d("OBJ", account+"/"+date+"/"+amount+"/"+type);
                    Map<String, String> row = new HashMap<>();
                    row.put("account", account);
                    row.put("date" , date);
                    row.put("amount", amount+"");
                    row.put("type", type+"");
                    data.add(row);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SimpleAdapter adapter = new SimpleAdapter(
                    TransActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String[]{"date", "amount"},
                    new int[] {android.R.id.text1, android.R.id.text2}
            );
            list.setAdapter(adapter);
        }
    }

}
