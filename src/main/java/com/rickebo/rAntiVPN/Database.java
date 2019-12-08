package com.rickebo.rAntiVPN;

import java.util.HashSet;

public class Database
{
	private HashSet<IP> blocked;
	
	public Database()
	{
		blocked = new HashSet<>();
	}
	
	public boolean inputLine(String line)
	{
		try
		{
			IP parsed = parseIp(line);
			
			if (containsIp(parsed))
				return false;
			
			blocked.add(parsed);
			return true;
		} catch (Throwable ex)
		{
			return false;
		}
	}
	
	public IP parseIp(String text)
	{
		String[] parts = text.split("/");
		
		String ipStr = parts[0];
		String maskStr = parts.length >= 2 ? parts[1] : "32";
		
		String[] ipParts = ipStr.split("\\.");
		
		if (ipParts.length != 4)
			return null;
		
		int a = Integer.parseInt(ipParts[0]);
		int b = Integer.parseInt(ipParts[1]);
		int c = Integer.parseInt(ipParts[2]);
		int d = Integer.parseInt(ipParts[3]);
		
		int mask = Integer.parseInt(maskStr);
		
		IP ip = new IP();
		
		ip.a = a;
		ip.b = b;
		ip.c = c;
		ip.d = d;
		ip.mask = mask;
		
		return ip;
	}
	
	public boolean containsIp(IP ip)
	{
		IP reduced = ip;
		while (reduced != null)
		{
			if (blocked.contains(reduced))
				return true;
			
			reduced = reduced.reduce();
		}
		
		return false;
	}
}
