package com.rickebo.rAntiVPN;

public class IP
{
	public int a;
	public int b;
	public int c;
	public int d;
	
	public int mask;
	
	@Override
	public boolean equals(Object other)
	{
		return (other instanceof IP) && equals((IP) other);
	}
	
	public boolean equals(IP other)
	{
		return a == other.a && b == other.b && c == other.c && d == other.d && mask == other.mask;
	}
	
	@Override
	public int hashCode()
	{
		return ((Integer) a).hashCode() + ((Integer) b).hashCode() + ((Integer) c).hashCode() + ((Integer) d).hashCode() + ((Integer) mask).hashCode();
	}
	
	public boolean contains(IP other)
	{
		if (mask >= 8 && a != other.a)
			return false;
		
		if (mask >= 16 && b != other.b)
			return false;
		
		if (mask >= 24 && c != other.c)
			return false;
		
		return d == other.d;
	}
	
	public IP reduce()
	{
		IP returnIp = new IP();
		
		int newMask = mask - 8;
		returnIp.mask = newMask;
		
		if (newMask >= 8)
			returnIp.a = a;
		else
			return null;
		
		if (newMask >= 16)
			returnIp.b = b;
		
		if (newMask >= 24)
			returnIp.c = c;
		
		return returnIp;
	}
}
