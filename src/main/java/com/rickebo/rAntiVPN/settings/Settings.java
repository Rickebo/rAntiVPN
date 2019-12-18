package com.rickebo.rAntiVPN.settings;

import java.util.ArrayList;
import java.util.List;

public class Settings
{
	private String kickMessage = "You have connected from a VPN (ip = %ip%) which is not allowed on this server. Please disconnect from your VPN and re-join.";
	private List<String> sources;
	private List<String> whitelistIds;
	
	private long updateInterval = 3600;
	private long updateDelay = 0;
	
	private boolean blockInvalidIps = false;
	
	public Settings()
	{
	
	}
	
	public boolean isBlockInvalidIps()
	{
		return blockInvalidIps;
	}
	
	public void setBlockInvalidIps(boolean blockInvalidIps)
	{
		this.blockInvalidIps = blockInvalidIps;
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
	
	public List<String> getWhitelistIds()
	{
		return whitelistIds;
	}
	
	public void setWhitelistIds(List<String> whitelistIds)
	{
		this.whitelistIds = whitelistIds;
	}
}
