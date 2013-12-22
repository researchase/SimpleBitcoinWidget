package com.vladvv.simplebitcoinwidget;

import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

public class BitcoinWidgetService extends IntentService {
	public BitcoinWidgetService() {
		super("BitcoinWidgetService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		performUpdate();		
	}
	
	private void performUpdate(){
		String lastUpdated = DateFormat.format("hh:mm:ss",  new Date()).toString();
		double value = -1;
		//attempt to get data from server and decode the JSON string
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(getFromServer());
			value = jsonObject.getDouble("amount");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		Log.d("com.vladvv.simplebitcoinwidget", "Value from server: " + value);		
		
		//update widget with new value
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget_layout);
		view.setTextViewText(R.id.widgetText, lastUpdated + " $" + value);
		ComponentName thisWidget = new ComponentName(this, MyWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget,  view);
	}
	
	/**
	 * Contact the server to get bitcoin value, and return JSON string from server
	 * @return
	 */
	private String getFromServer(){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(getResources().getString(R.string.valueUrl));
		ResponseHandler<String> res = new BasicResponseHandler();
		String response = "";
		
		try {
			response = httpclient.execute(httppost, res);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return response;
	}



	
}
