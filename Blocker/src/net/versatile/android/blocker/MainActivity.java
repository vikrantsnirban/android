package net.versatile.android.blocker;

import net.versatile.android.blocker.dto.Rule;
import net.versatile.android.blocker.service.RulesDataManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void handleSaveRules(View view){
		TextView txtRuleExpression = (TextView) findViewById(R.id.txtRuleExpression);
		Spinner txtRuleCriteria = (Spinner) findViewById(R.id.spinnerRuleCriteria);
		Rule rule = new Rule();
		rule.setRuleCriteria(txtRuleCriteria.getSelectedItem().toString());
		rule.setRuleExpression(txtRuleExpression.getText().toString());
		Log.d(TAG, "Saving Rule : " + rule);
		new RulesDataManager(this).saveRule(rule);
		Toast.makeText(this, "Saving Rule : " + rule, Toast.LENGTH_SHORT).show();
		Log.d(TAG, "Rules in DB: " + new RulesDataManager(this).getAllRules());
		
	}
	
	public void handleViewRules(View view){
		startActivity(new Intent(this, ViewRulesActivity.class));
	}
	
	public void handleViewLogs(View view){
		startActivity(new Intent(this, ViewLogActivity.class));
	}
}
