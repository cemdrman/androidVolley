package com.example.cem.combilisimiovolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final String requestLink = "https://api.apixu.com/v1/current.json?key=039e4db4333a482dabf214535170904&q=Istanbul";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView1 = (TextView) findViewById(R.id.txt1);
        final TextView textView2 = (TextView) findViewById(R.id.txt2);
        final TextView textView3 = (TextView) findViewById(R.id.txt3);
        final TextView textView4 = (TextView) findViewById(R.id.txt4);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonCurrent = jsonObject.getJSONObject("current");
                    JSONObject jsonLocation = jsonObject.getJSONObject("location");
                    JSONObject jsonCondition = jsonCurrent.getJSONObject("condition");
                    textView1.setText(jsonLocation.getString("region") + "-" +jsonLocation.getString("country"));
                    textView2.setText("Durum: " + jsonCondition.getString("text"));
                    textView3.setText("sıcaklık: " + jsonCurrent.getString("temp_c"));
                    textView4.setText("hissedilen sıcaklık: " + jsonCurrent.getString("feelslike_c"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error);
            }
        });
        requestQueue.add(stringRequest);
    }
}
