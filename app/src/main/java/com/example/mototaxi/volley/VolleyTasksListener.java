package com.example.mototaxi.volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyTasksListener {

public void handleResult(String method_name, JSONObject response);

public void handleError(VolleyError e);



}
 