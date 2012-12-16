package com.itcuties.android.apps;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.itcuties.android.apps.data.SMSData;

/**
 * Main Activity. Displays a list of numbers.
 * 
 * @author itcuties
 *
 */
public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<SMSData> smsList = new ArrayList<SMSData>();
		
		Uri uri = Uri.parse("content://sms/inbox");
		Cursor c= getContentResolver().query(uri, null, null ,null,null);
		startManagingCursor(c);
		
		// Read the sms data and store it in the list
		if(c.moveToFirst()) {
			for(int i=0; i < c.getCount(); i++) {
				SMSData sms = new SMSData();
				sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
				sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
				smsList.add(sms);
				
				c.moveToNext();
			}
		}
		c.close();
		
		// Set smsList in the ListAdapter
		setListAdapter(new ListAdapter(this, smsList));
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		SMSData sms = (SMSData)getListAdapter().getItem(position);
		
		Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();
		
	}
	
}
