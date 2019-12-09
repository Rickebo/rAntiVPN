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
			IP parsed = IP.parseIp(line);
			
			if (containsIp(parsed))
				return false;
			
			blocked.add(parsed);
			return true;
		} catch (Throwable ex)
		{
			return false;
		}
	}
	
	public boolean containsIp(IP ip)
	{
		IP shifted = new IP();
		
		for (int shift = (32 - ip.mask); shift <= 32; shift++)
		{
			shifted.f = (ip.f >> shift) << shift;
			shifted.mask = 32  - shift;
			
			if (blocked.contains(shifted))
				return true;
		}
		
		return false;
	}
}
