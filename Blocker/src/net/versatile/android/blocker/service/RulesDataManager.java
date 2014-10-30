package net.versatile.android.blocker.service;

import java.util.ArrayList;
import java.util.List;

import net.versatile.android.blocker.dto.Rule;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RulesDataManager extends SQLiteOpenHelper{
	String TAG = "RulesDataManager";
	static String dbName = "rules.db";
	static int dbVersion = 1;
	String callRulesTableName = "CallRules";
	public static String ruleExpression = "EXPRESSION";
	public static String ruleCriteria = "CRITERIA";
	String id = "_id";

	public RulesDataManager(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s ( %s text primary key, %s text, %s text)", callRulesTableName, id, ruleCriteria, ruleExpression);
		db.execSQL(sql);
		Log.d(TAG, "Create SQL= " + sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table " + callRulesTableName);
		onCreate(db);
	}
	
	public void saveRule(Rule rule){
		ContentValues value = new ContentValues();
		value.put(ruleCriteria, rule.getRuleCriteria());
		value.put(ruleExpression, rule.getRuleExpression());
		getWritableDatabase().insert(callRulesTableName, null, value);
		Log.d(TAG, "Saved Rule to DB:" + rule);
	}
	
	public List<Rule> getAllRules(){
		Cursor cursor = getReadableDatabase().query(callRulesTableName, null, null, null, null, null, null);
		List<Rule> rules = new ArrayList<Rule>();
		
		if(cursor.getCount() > 0)
		while(cursor.moveToNext()){
			Rule rule = new Rule();
			rule.setRuleCriteria(cursor.getString(cursor.getColumnIndex(ruleCriteria)));
			rule.setRuleExpression(cursor.getString(cursor.getColumnIndex(ruleExpression)));
			rules.add(rule);
		}
		
		return  rules.size() > 0 ? rules : null; 
	}
	
	public Cursor getAllRulesCursor(){
		return getReadableDatabase().query(callRulesTableName, null, null, null, null, null, null);
	}
	
	public void deleteRule(Rule rule){
		getWritableDatabase().delete(callRulesTableName, ruleExpression + " = ? and " + ruleCriteria + " = ?", new String[]{rule.getRuleExpression(), rule.getRuleCriteria()});
	}

}
