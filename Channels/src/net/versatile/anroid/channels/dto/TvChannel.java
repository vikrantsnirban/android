package net.versatile.anroid.channels.dto;

import java.io.Serializable;

public class TvChannel{

	String tvName;
	String channelName;
	int channelNumber = -1;
	
	public TvChannel(){
		
	}
	
	public TvChannel(String tvName, String channelName, int channelNumber){
		this.tvName = tvName;
		this.channelName = channelName;
		this.channelNumber = channelNumber;
	}
	
	public String getTvName() {
		return tvName;
	}
	public void setTvName(String tvName) {
		this.tvName = tvName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public int getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
}
