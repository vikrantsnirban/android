package net.versatile.android.blocker.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.versatile.android.blocker.dto.CallLog;
import net.versatile.android.blocker.dto.Rule;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RulesDataManager extends SQLiteOpenHelper{
	public static final String from = "INCOMING";
	String TAG = "RulesDataManager";
	static String dbName = "rules.db";
	static int dbVersion = 3;
	String callRulesTableName = "CallRules";
	String callLogsTableName = "CallLogs";
	public static String ruleExpression = "EXPRESSION";
	public static String ruleCriteria = "CRITERIA";
	public static final String time = "TIME";
	String id = "_id";

	public RulesDataManager(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s ( %s text primary key, %s text, %s text)", callRulesTableName, id, ruleCriteria, ruleExpression);
		db.execSQL(sql);
		Log.d(TAG, "Create SQL= " + sql);
		
		sql = String.format("create table %s ( %s text primary key, %s text, %s int)", callLogsTableName, id, from, time);
		db.execSQL(sql);
		Log.d(TAG, "Create SQL2= " + sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
		db.execSQL("drop table " + callRulesTableName);
		db.execSQL("drop table " + callLogsTableName);
		onCreate(db);
	}
	
	public void saveRule(Rule rule){
		ContentValues value = new ContentValues();
		value.put(ruleCriteria, rule.getRuleCriteria());
		value.put(ruleExpression, rule.getRuleExpression());
		getWritableDatabase().insert(callRulesTableName, null, value);
		Log.d(TAG, "Saved Rule to DB:" + rule);
	}
	
	public void saveLog(CallLog rule){
		ContentValues value = new ContentValues();
		value.put(from, rule.getFrom());
		value.put(time, Calendar.getInstance().getTimeInMillis());
		getWritableDatabase().insert(callLogsTableName, null, value);
		Log.d(TAG, "Saved Log to DB:" + rule);
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

	public Cursor getAllLogsCursor() {
		return getReadableDatabase().query(callLogsTableName, null, null, null, null, null, null);
	}

}
