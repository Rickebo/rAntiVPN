package com.rickebo.rAntiVPN;

import org.agrona.collections.LongHashSet;

import java.util.HashSet;

public class Database
{
	private boolean initialized = false;
	private final LongHashSet blocked;
	
	public Database()
	{
		IP invalidIp = new IP(0, Integer.MAX_VALUE);
		blocked = new LongHashSet(invalidIp.toLong());
	}
	
	public int size()
	{
		return blocked.size();
	}
	
	public boolean isInitialized()
	{
		return initialized;
	}
	
	public void setInitialized()
	{
		initialized = true;
	}
	
	public boolean inputLine(String line)
	{
		if (line == null || line.isEmpty())
			return false;
		
		char first = line.charAt(0);
		if (first == '#' || first == '/')
			return false;
		
		try
		{
			IP parsed = IP.parseIp(line);
			
			if (containsIp(parsed))
				return false;
			
			blocked.add(parsed.toLong());
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
			
			if (blocked.contains(shifted.toLong()))
				return true;
		}
		
		return false;
	}
}
