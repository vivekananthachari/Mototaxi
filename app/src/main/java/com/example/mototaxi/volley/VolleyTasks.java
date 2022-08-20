package com.example.mototaxi.volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;





public class VolleyTasks {
	static VolleyTasksListener volley;
	private static String TAG = VolleyTasks.class.getSimpleName();
	private static ProgressDialog pDialog;
	public static void makeVolleyPost(Fragment context, String url, final JSONObject postdata, String method_name,String token) {
		volley = (VolleyTasksListener) context;
		/*pDialog = new ProgressDialog(context.getActivity());
		pDialog.setMessage("Loading ...");*/
        startVollyTask(url, postdata, method_name,token);

    }
	public static void makeVolleyPost(final Activity context, String url, final JSONObject postdata, String method_name,String token) {
		volley = (VolleyTasksListener) context;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading ...");
		startVollyTask(url, postdata, method_name,token);
	}
    public static void makeVolleyPostrecy(final Context context, String url, final JSONObject postdata, String method_name, String token) {
        volley = (VolleyTasksListener) context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading ...");
        startVollyTaskput(url, postdata, method_name,token);
    }

    public static void makeVolleyPostrecyde(final Context context, String url, final JSONObject postdata, String method_name, String token) {
        volley = (VolleyTasksListener) context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading ...");
        startVollyTaskdelect(url, postdata, method_name,token);
    }
	public static void makeVolleyPost1(final Activity context, String url, final HashMap<String,String> postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
	//	pDialog.setMessage("Loading ...");
		startVollyTask2(url, postdata, method_name);
	}




	public static void makeVolleyPostservice(final Service context, String url, final HashMap<String,String> postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
	//	pDialog.setMessage("Loading ...");
		startVollyTask3(url, postdata, method_name);
	}

	public static void makeVolleyPostservice1(final Service context, String url, final JSONObject postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
	//	pDialog.setMessage("Loading ...");
		startVollyTask1(url, postdata, method_name);
	}

	public static void makeVolleyPostcontent(final ContentObserver context, String url, final JSONObject postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading ...");
		startVollyTask1(url, postdata, method_name);
	}

	public static void makeVolleyPostcontent1(final ContentObserver context, String url, final HashMap<String,String> postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
		//pDialog.setMessage("Loading ...");
		startVollyTask3(url, postdata, method_name);
	}



	public static void makeVolleyPostbrodcast1(final BroadcastReceiver context, String url, final JSONObject postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading ...");
		startVollyTask1(url, postdata, method_name);
	}

	public static void makeVolleyPostbrodcast3(final BroadcastReceiver context, String url,  final HashMap<String,String> postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context);
		//pDialog.setMessage("Loading ...");
		startVollyTask3(url, postdata, method_name);
	}



	public static void makeVolleyPost2(final Fragment context, String url, final HashMap<String,String> postdata, String method_name) {
		volley = (VolleyTasksListener) context;
		//pDialog = new ProgressDialog(context.getActivity());
	//	pDialog.setMessage("Loading ...");
		startVollyTask2(url, postdata, method_name);
	}
    public static void makeVolleyPostactiviy(final Activity context, String url, final JSONObject postdata, String method_name,String token) {
        volley = (VolleyTasksListener) context;
        //pDialog = new ProgressDialog(context.getActivity());
        //	pDialog.setMessage("Loading ...");
        startVollyTaskput(url, postdata, method_name,token);
    }

	public static void makeVolleydelete(final Fragment context, String url, final JSONObject postdata , String method_name) {

		volley = (VolleyTasksListener) context;
		pDialog = new ProgressDialog(context.getActivity());
		pDialog.setMessage("Loading ...");
		startVollyTaskde(url, postdata, method_name);
	}

//	public static void makeVolleyPost12(final RecyclerView_Adapter context, String url, final HashMap<String,String> postdata, String method_name) {
//		volley = (VolleyTasksListener) context;
//
//		startVollyTask2(url, postdata, method_name);
//	}


//	public static void makeVolleyPost13(final RecyclerView_Adapter1 context, String url, final HashMap<String,String> postdata, String method_name) {
//		volley = (VolleyTasksListener) context;
//
//		startVollyTask2(url, postdata, method_name);
//	}

    public static void startVollyTaskdelect(String url, final JSONObject postdata, final String method_name,final String token) {
        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.DELETE,
                url, postdata, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                hidepDialog();
                if (response != null) {
                    volley.handleResult(method_name, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "1 Error: " + error.getMessage());
                hidepDialog();
                volley.handleError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                return params;
            };
        };
        // Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
        VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
        Log.i("1 url", url);
        Log.i("1 postdata", postdata.toString());

/*
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
*/
    }

    public static void startVollyTaskput(String url, final JSONObject postdata, final String method_name,final String token) {
        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.PUT,
                url, postdata, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                hidepDialog();
                if (response != null) {
                    volley.handleResult(method_name, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "1 Error: " + error.getMessage());
                hidepDialog();
                volley.handleError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                return params;
            };
        };
        // Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
        VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
        Log.i("1 url", url);
        Log.i("1 postdata", postdata.toString());

/*
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
*/
    }



    private static void startVollyTask(String url, final JSONObject postdata, final String method_name,final String token) {
		showpDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				url, postdata, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				hidepDialog();
				if (response != null) {
					volley.handleResult(method_name, response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "1 Error: " + error.getMessage());
				hidepDialog();
				volley.handleError(error);
			}
		}) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                return params;
            };
        };
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
        VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
		Log.i("1 url", url);
		Log.i("1 postdata", postdata.toString());

/*
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
*/
	}
	private static void startVollyTask1(String url, final JSONObject postdata, final String method_name) {

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				url, postdata, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());

				if (response != null) {
					volley.handleResult(method_name, response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "1 Error: " + error.getMessage());

				volley.handleError(error);
			}
		});
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
		Log.i("1 url", url);
		Log.i("1 postdata", postdata.toString());
	}
	private static void startVollyTask2(String url, final HashMap<String,String> postdata, final String method_name) {

	//	showpDialog();
		StringRequest json=new StringRequest(Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
			//	hidepDialog();
				try {
					volley.handleResult(method_name,new JSONObject(response));
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
		//		hidepDialog();
				volley.handleError(error);

			}
		}

		){

			@Override
			public String getBodyContentType() {
				return "application/x-www-form-urlencoded; charset=UTF-8";
			}

			@Override
			protected Map<String,String> getParams(){

				return postdata;
			}

		};

		// Adding request to request queue
		VolleyApplication.getInstance().getRequestQueue().getCache().clear();
		VolleyApplication.getInstance().addToRequestQueue(json);
		Log.i("1 url", url);
		Log.i("1 postdata", postdata.toString());
	}

	private static void startVollyTask3(String url, final HashMap<String,String> postdata, final String method_name) {

		StringRequest json=new StringRequest(Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {

				try {
					volley.handleResult(method_name,new JSONObject(response));
				} catch (JSONException e) {
					e.printStackTrace();
					System.out.println("error1"+e.toString());
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				volley.handleError(error);

			}
		}

		){
			@Override
			protected Map<String,String> getParams(){

				return postdata;
			}

		};

		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(json);
		Log.i("1 url", url);
		Log.i("1 postdata", postdata.toString());
	}

	private static void startVollyTaskde(String url, final JSONObject postdata, final String method_name) {

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.DELETE,
				url, postdata, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());

				if (response != null) {
					volley.handleResult(method_name, response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "1 Error: " + error.getMessage());

				volley.handleError(error);
			}
		});
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
		Log.i("1 url", url);
		Log.i("1 postdata", postdata.toString());
	}


	public static void makeVolleyGETObject(final Activity context, String url, final String method_name,String token) {
		volley = (VolleyTasksListener) context;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("State Loading ...");
		showpDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response)
			{
				Log.d(TAG, response.toString());
				if (response != null)
				{
					volley.handleResult(method_name, response);
				}
				hidepDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "2 Error: " + error.getMessage());
				hidepDialog();
				volley.handleError(error);
			}
		}){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorization", "Bearer " + token);

                hidepDialog();
                return params;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

              //  params.put("token", token);
                hidepDialog();
                return params;
            };
        };
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);

        VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
	}

	public static void makeVolleyGETObject(final Service context, String url, final String method_name) {
		volley = (VolleyTasksListener) context;


		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				if (response != null) {
					volley.handleResult(method_name, response);
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "2 Error: " + error.getMessage());

				volley.handleError(error);
			}
		});
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
	}
	public static void makeVolleyGETObjectFrag(Fragment context, String url, final String method_name) {
		volley = (VolleyTasksListener) context;

		//showpDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				if (response != null) {
					volley.handleResult(method_name, response);
				}
				//hidepDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "3 Error: " + error.getMessage());
				//hidepDialog();
				volley.handleError(error);
			}
		});
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
	}
	public static void makeVolleyGETObjectFrag2(Fragment context, String url, final JSONObject json, final String method_name) {
		volley = (VolleyTasksListener) context;
		pDialog = new ProgressDialog(context.getActivity());
		pDialog.setMessage("Loading ...");
		showpDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				url, json, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				if (response != null) {
					volley.handleResult(method_name, response);
				}
				hidepDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "3 Error: " + error.getMessage());
				hidepDialog();
				volley.handleError(error);
			}
		});
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
	}

	public static void makeVolleyGETObjectFrag3(Fragment context, String url, final JSONObject json, final String method_name) {
		volley = (VolleyTasksListener) context;
		pDialog = new ProgressDialog(context.getActivity());
		pDialog.setMessage("Loading ...");
		showpDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.DELETE,
				url, json, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				if (response != null) {
					volley.handleResult(method_name, response);
				}
				hidepDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "3 Error: " + error.getMessage());
				hidepDialog();
				volley.handleError(error);
			}
		});
		// Adding request to request queue
        VolleyApplication.getInstance().getRequestQueue().getCache().get(url);
		VolleyApplication.getInstance().addToRequestQueue(jsonObjReq);
	}


	private static void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}
	private static void hidepDialog() {
		pDialog.dismiss();
	}
}
