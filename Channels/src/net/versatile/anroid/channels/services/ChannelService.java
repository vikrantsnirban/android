package net.versatile.anroid.channels.services;

import java.util.ArrayList;
import java.util.List;

import net.versatile.anroid.channels.dto.TvChannel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChannelService extends SQLiteOpenHelper{
	static String databaseName = "channels.db";
	static int databaseVersion = 1;
	static String tvChannelTableName = "TVChannel";
	static String channelTableName = "CHANNEL";
	public static String tvName = "TV";
	public static String id = "_id";
	public static String channelName = "CHANNEL";
	public static String channelNumber = "NUMBER"; 

	public ChannelService(Context context) {
		super(context, databaseName, null, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s ( %s text primary key, %s text, %s text, %s int)", tvChannelTableName, id, tvName, channelName, channelNumber);
		db.execSQL(sql);
		sql = String.format("create table %s ( %s text primary key, %s text)", channelTableName, id, channelName);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table " + tvChannelTableName);
		db.execSQL("drop table " + channelTableName);
		onCreate(db);
		
	}
	
	public void addTvChannel(TvChannel tvChannel){
		ContentValues values = new ContentValues();
		values.put(id, tvChannel.getTvName() +"_"+ tvChannel.getChannelName());
		values.put(tvName, tvChannel.getTvName());
		values.put(channelName, tvChannel.getChannelName());
		values.put(channelNumber, tvChannel.getChannelNumber());
		getWritableDatabase().insertWithOnConflict(tvChannelTableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);	
	}
	
	public Cursor getTvChannels(String stvName){
		return getReadableDatabase().query(tvChannelTableName, null, null, null, null, null, channelName);
	}
	
	public List<TvChannel> getAllTvChannels(String stvName){
		List<TvChannel> tvChannels = new ArrayList<TvChannel>();
		Cursor cursor = getReadableDatabase().query(tvChannelTableName, null, null, null, null, null, channelName);
		while(cursor.moveToNext()){
			TvChannel tvChannel = new TvChannel();
			tvChannel.setTvName(cursor.getString(cursor.getColumnIndex(tvName)));
			tvChannel.setChannelName(cursor.getString(cursor.getColumnIndex(channelName)));
			tvChannel.setChannelNumber(cursor.getInt(cursor.getColumnIndex(channelNumber)));
			tvChannels.add(tvChannel);
		}
		return tvChannels;
	}
	
	public void addChannel(String sChannelName){
		ContentValues values = new ContentValues();
		values.put(channelName, sChannelName);
		getWritableDatabase().insert(channelTableName, null, values);	
	}
	
	public Cursor getChannels(){
		return getReadableDatabase().query(channelTableName, null, null, null, null, null, channelName);
	}
	
	public void deleteChannel(TvChannel tvChannel){
		getWritableDatabase().delete(tvChannelTableName, "_id = ?", new String[] {tvChannel.getTvName() + "_" +tvChannel.getChannelName()});
	}
}
