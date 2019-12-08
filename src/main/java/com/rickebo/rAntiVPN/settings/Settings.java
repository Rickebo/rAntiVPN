package com.rickebo.rAntiVPN.settings;

import java.util.ArrayList;
import java.util.List;

public class Settings
{
	private String kickMessage = "You have connected from a VPN (your ip = %ip%) which is not allowed on this server. Please disconnect from your VPN and re-join.";
	private List<String> sources;
	
	public Settings()
	{
	
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
