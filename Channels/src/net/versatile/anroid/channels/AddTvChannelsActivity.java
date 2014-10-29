package net.versatile.anroid.channels;

import java.util.ArrayList;
import java.util.List;

import net.versatile.anroid.channels.dto.TvChannel;
import net.versatile.anroid.channels.services.ChannelService;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddTvChannelsActivity extends Activity {

	String TAG = "AddTvChannelsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tv_channels);
		List<String> channelList = new ArrayList<String>();
		Cursor cursor = new ChannelService(this).getChannels();
		while(cursor.moveToNext()){
			channelList.add(cursor.getString(cursor.getColumnIndex(ChannelService.channelName)));
		}
		Spinner spinnerChannel = (Spinner) findViewById(R.id.addTvChannels_spinnerChannel);
		ArrayAdapter<String> channelAdapter = new ArrayAdapter<String>(this	, android.R.layout.simple_spinner_item, channelList);
		channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChannel.setAdapter(channelAdapter);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			String tvName = extras.getString("tvName");
			if(tvName != null){
				TextView tvText= (TextView)findViewById(R.id.txtTv);
				tvText.setText(tvName);
			}
			
			String channelName = extras.getString("channelName");
			if(channelName != null){
				spinnerChannel.setSelection(channelAdapter.getPosition(channelName));
			}

			String channelNumber = extras.getString("channelNumber");
			if(channelNumber != null){
				TextView txtTvChannelNumber = (TextView)findViewById(R.id.txtTvChannelNumber);	
				txtTvChannelNumber.setText(channelNumber);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_tv_channels, menu);
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
	
	public void handleSaveTvChannels(View view){
		TextView txtTvChannelNumber = (TextView)findViewById(R.id.txtTvChannelNumber);	
		CharSequence channelNumber = txtTvChannelNumber.getText();
		
		TextView tvText= (TextView)findViewById(R.id.txtTv);	
		CharSequence tvname = tvText.getText();
		
		Spinner spinnerChannel = (Spinner) findViewById(R.id.addTvChannels_spinnerChannel);

		TvChannel tvChannel = new TvChannel();
		tvChannel.setTvName((tvname.toString() != null && tvname.toString().toString().trim().length() > 0 ) ? tvname.toString() : "Default");
		tvChannel.setChannelName(spinnerChannel.getSelectedItem().toString());
		tvChannel.setChannelNumber(Integer.parseInt(channelNumber.toString()));
		//TODO Add validation for required values
		
		new ChannelService(this).addTvChannel(tvChannel);
		
		Toast.makeText(this, "Channel " + tvChannel.getChannelName() + " added for " + tvChannel.getTvName() +" TV.", Toast.LENGTH_SHORT).show();

		txtTvChannelNumber.setText(null);
	}
}
