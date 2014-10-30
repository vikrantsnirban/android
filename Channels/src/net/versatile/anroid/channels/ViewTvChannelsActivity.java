package net.versatile.anroid.channels;

import net.versatile.anroid.channels.dto.TvChannel;
import net.versatile.anroid.channels.services.ChannelService;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewTvChannelsActivity extends Activity {
	String TAG = "ViewTvChannelsActivity";
	ListView listView;
	ArrayAdapter<TvChannel> listAdapter;
	Cursor cursor;
	SimpleCursorAdapter simpleCursorAdapter;
	String [] from = {ChannelService.channelName, ChannelService.channelNumber, ChannelService.tvName};
	int [] to = {R.id.txtChannel, R.id.txtChannelNumber, R.id.txtTvName};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_tv_channels);
		
		listView = (ListView) findViewById(R.id.tvChannelList);
		cursor = new ChannelService(this).getTvChannels(null);
		simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.rows, cursor, from, to);
		listView.setAdapter(simpleCursorAdapter);
//		listAdapter = new ArrayAdapter<TvChannel>(this, R.layout.rows, new ChannelService(this).getAllTvChannels(null));
		//listView.setAdapter(listAdapter);
		registerForContextMenu(listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_tv_channels, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater menuInflater = new MenuInflater(this);
		menuInflater.inflate(R.menu.tvchannelcontextmenu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info=(AdapterContextMenuInfo)item.getMenuInfo();
	    switch(item.getItemId())
	    {
	    case R.id.tvchannel_editchannel:
	    	//Log.d(TAG, "Editing " + listView.getAdapter().getItem(info.position));
	    	cursor = (Cursor)listView.getAdapter().getItem(info.position);
	    	TvChannel tvChannel = new TvChannel(cursor.getString(cursor.getColumnIndex(ChannelService.tvName)), cursor.getString(cursor.getColumnIndex(ChannelService.channelName)), cursor.getInt(cursor.getColumnIndex(ChannelService.channelNumber)));
	    	Intent intent = new Intent(this, AddTvChannelsActivity.class);
	    	//intent.putExtra("tvName", tvChannel.getTvName() != null ? tvChannel.getTvName() : "Default");
	    	intent.putExtra("tvName", cursor.getString(cursor.getColumnIndex(ChannelService.tvName)));
	    	intent.putExtra("channelNumber", cursor.getString(cursor.getColumnIndex(ChannelService.channelNumber)));
	    	intent.putExtra("channelName", cursor.getString(cursor.getColumnIndex(ChannelService.channelName)));
	    	startActivity(intent);
	    	Log.d(TAG, "Editing " + tvChannel.getChannelName());
	        return true;
	    case R.id.tvchannel_deletechannel:
	    	cursor = (Cursor)listView.getAdapter().getItem(info.position);
	    	String channelName = cursor.getString(cursor.getColumnIndex(ChannelService.channelName));
	    	new ChannelService(this).deleteChannel(new TvChannel(cursor.getString(cursor.getColumnIndex(ChannelService.tvName)), channelName, cursor.getInt(cursor.getColumnIndex(ChannelService.channelNumber))));
	    	Log.d(TAG, "Deleting " + channelName);
	    	simpleCursorAdapter.changeCursor(new ChannelService(this).getTvChannels(null));

	    }

	    return true; 
	}
}
