package com.yuvrajvi.onlineapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //'https://jsonplaceholder.typicode.com/todos/1'
    //private RequestQueue requestQueue;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue=MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //requestQueue= Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,
            "https://jsonplaceholder.typicode.com/todos/1",null,
                new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Json", "onResponse: "+response.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        Log.d("JsonArray", "onResponse: "+jsonObject.getString("title"));
                        boolean d=jsonObject.getBoolean("completed");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
        //requestQueue.add(jsonArrayRequest);
        //requestQueue.add(jsonObjectRequest);
    }
}