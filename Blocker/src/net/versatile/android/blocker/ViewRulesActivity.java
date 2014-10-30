package net.versatile.android.blocker;

import net.versatile.android.blocker.dto.Rule;
import net.versatile.android.blocker.service.RulesDataManager;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class ViewRulesActivity extends Activity {
	ListView listRules;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_rules);
		listRules = (ListView)findViewById(R.id.listRules);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.columns, new RulesDataManager(this).getAllRulesCursor(), new String[]{RulesDataManager.ruleCriteria, RulesDataManager.ruleExpression}, new int [] {R.id.viewRules_txtRuleCriteria, R.id.viewRules_txtExpression});
		listRules.setAdapter(adapter);
		registerForContextMenu(listRules);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_rules, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.rulescontextmenu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info=(AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId())
	    {
	    	case R.id.rulecontext_delete:
	    		Cursor cursor = (Cursor)listRules.getAdapter().getItem(info.position);
		    	new RulesDataManager(this).deleteRule(new Rule(cursor.getString(cursor.getColumnIndex(RulesDataManager.ruleCriteria)), cursor.getString(cursor.getColumnIndex(RulesDataManager.ruleExpression))));
	    }
		return true;
		
	}
}
