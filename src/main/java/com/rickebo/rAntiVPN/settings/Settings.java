package com.rickebo.rAntiVPN.settings;

import java.util.ArrayList;
import java.util.List;

public class Settings
{
	private String kickMessage = "You have connected from a VPN (your ip = %ip%) which is not allowed on this server. Please disconnect from your VPN and re-join.";
	private List<String> sources;
	
	private long updateInterval = 3600;
	private long updateDelay = 0;
	
	public Settings()
	{
	
	}
	
	public long getUpdateInterval()
	{
		return updateInterval;
	}
	
	public void setUpdateInterval(long updateInterval)
	{
		this.updateInterval = updateInterval;
	}
	
	public long getUpdateDelay()
	{
		return updateDelay;
	}
	
	public void setUpdateDelay(long updateDelay)
	{
		this.updateDelay = updateDelay;
	}
	
	public List<String> getSources()
	{
		return sources;
	}
	
	public void setSources(List<String> sources)
	{
		this.sources = sources;
	}
	
	public String getKickMessage()
	{
		return kickMessage;
	}
	
	public void setKickMessage(String kickMessage)
	{
		this.kickMessage = kickMessage;
	}
}
