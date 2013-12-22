package com.vladvv.simplebitcoinwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
public class MyWidgetProvider extends AppWidgetProvider {

	public static final int MINUTES = 30;

	private PendingIntent service = null;  
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		final Intent intent = new Intent(context, BitcoinWidgetService.class);

		if(service == null)
		{
			service = PendingIntent.getService(context,  0 ,  intent,  PendingIntent.FLAG_CANCEL_CURRENT);
		}
		//schedule recurring alarm event
		m.setInexactRepeating(AlarmManager.RTC, 0, 1000 * 60 * MINUTES, service);
	}
}

