package com.rosid.androidasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String id, name, username, email,street,suite,city,zipcode,addr,lat,lng,ge;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView recycleView;
    private asynctaskAdapter adapter;
    private ArrayList<usserAsynctask> usersArrayList;
    private Button button;
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    ProgressDialog progressDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersArrayList = new ArrayList<>();
        button = (Button) findViewById(R.id.btn_data);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                AsyncTaskRunner task = new AsyncTaskRunner();
                task.execute();
            }
       });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsyncTaskRunner task = new AsyncTaskRunner();
                task.execute();
            }
        });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream js = getAssets().open("DATAJSON.json");
            int size = js.available();
            byte[] buffer = new byte[size];
            js.read(buffer);
            js.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String getData(){
        String line = "";
        try {
            //URL uri = new URL("http://www.mocky.io/v2/5ccaa82c610000910f161f7f");
            URL uri = new URL ("http://jsonplaceholder.typicode.com/users");
            connection = (HttpURLConnection) uri.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (MalformedURLException e ){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
            try{
                if (reader != null) reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();;

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

@Override
        protected String doInBackground(String... params) {

            try {
                /*JSONObject obj = new JSONObject(getData());
                JSONArray userArray = obj.getJSONArray("users");
                */
                JSONArray userArray = new JSONArray(getData());
                for (int i = 0; i < userArray.length(); i++) {
                    JSONObject userDetail = userArray.getJSONObject(i);
                    id = userDetail.getString("id");
                    name = userDetail.getString("name");
                    username = userDetail.getString("username");
                    email = userDetail.getString("email");

                    JSONObject address = userDetail.getJSONObject("address");
                    street = address.getString("street");
                    suite = address.getString("suite");
                    city = address.getString("city");
                    zipcode = address.getString("zipcode");

                    JSONObject geo = address.getJSONObject("geo");
                    lat = geo.getString("lat");
                    lng = geo.getString("lng");

                    addr = street+", "+suite+", "+city+", "+zipcode;
                    ge = lat+", "+lng;

                    usersArrayList.add(new usserAsynctask(id, name, username, email, addr, ge));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
            protected void onPostExecute(String result) {
            mSwipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                recycleView = (RecyclerView) findViewById(R.id.recycle_view);

                adapter = new asynctaskAdapter(usersArrayList);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

                recycleView.setLayoutManager(layoutManager);

                recycleView.setAdapter(adapter);

            }

        }
}