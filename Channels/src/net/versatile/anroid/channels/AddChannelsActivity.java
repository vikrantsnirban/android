package net.versatile.anroid.channels;

import net.versatile.anroid.channels.services.ChannelService;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddChannelsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_channels);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_channels, menu);
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
	
	public void handleSaveChannel(View view){
		TextView txtTvChannel = (TextView)findViewById(R.id.addChannel_txtChannel);	
		CharSequence channelName = txtTvChannel.getText();
		new ChannelService(this).addChannel(channelName.toString());
		Toast.makeText(this, "Channel " + channelName + " added.", Toast.LENGTH_SHORT).show();
		txtTvChannel.setText(null);

	}
}
