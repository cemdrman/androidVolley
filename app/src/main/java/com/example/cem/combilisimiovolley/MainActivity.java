package com.example.cem.combilisimiovolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final String requestLink = "https://api.apixu.com/v1/current.json?key=039e4db4333a482dabf214535170904&q=Istanbul";
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineComponent();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void defineComponent(){
        textView1 = (TextView) findViewById(R.id.txt1);
        textView2 = (TextView) findViewById(R.id.txt2);
        textView3 = (TextView) findViewById(R.id.txt3);
        textView4 = (TextView) findViewById(R.id.txt4);
        icon = (ImageView) findViewById(R.id.icon);
    }

    private void parseResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonCurrent = jsonObject.getJSONObject("current");
            JSONObject jsonLocation = jsonObject.getJSONObject("location");
            JSONObject jsonCondition = jsonCurrent.getJSONObject("condition");
            textView1.setText(jsonLocation.getString("region") + "-" +jsonLocation.getString("country"));
            textView2.setText("Durum: " + jsonCondition.getString("text"));
            textView3.setText("sıcaklık: " + jsonCurrent.getString("temp_c"));
            textView4.setText("hissedilen sıcaklık: " + jsonCurrent.getString("feelslike_c"));
            String weatherIcon = jsonCondition.getString("icon");
            System.out.println(weatherIcon);
            Picasso.with(this).load("https:" + weatherIcon).into(icon);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
