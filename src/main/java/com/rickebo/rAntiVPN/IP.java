package com.rickebo.rAntiVPN;

public class IP
{
	public int f;
	public int mask;
	
	@Override
	public boolean equals(Object other)
	{
		return (other instanceof IP) && equals((IP) other);
	}
	
	public boolean equals(IP other)
	{
		return (f & mask) == (other.f & other.mask) && mask == other.mask;
	}
	
	@Override
	public int hashCode()
	{
		return ((Integer) f).hashCode() + ((Integer) mask).hashCode();
	}
	
	public static IP parseIp(String text)
	{
		String[] parts = text.split("/");
		
		String ipStr = parts[0];
		String maskStr = parts.length >= 2 ? parts[1] : "32";
		
		String[] ipParts = ipStr.split("\\.");
		
		if (ipParts.length != 4)
			return null;
		
		long a = Long.parseLong(ipParts[0]);
		long b = Long.parseLong(ipParts[1]);
		long c = Long.parseLong(ipParts[2]);
		long d = Long.parseLong(ipParts[3]);
		
		int mask = Integer.parseInt(maskStr);
		
		IP ip = new IP();
		
		long f = 0;
		
		f += (a << 24) + (b << 16) + (c << 8) + d;
		
		int shift = 32 - mask;
		f >>= shift;
		f <<= shift;
		
		ip.f = (int) f;
		ip.mask = mask;
		
		return ip;
	}
	
	public boolean contains(IP other)
	{
		int reduced = f >> (32 - mask);
		int otherReduced = other.f >> (32 - mask);
		
		return reduced == otherReduced;
	}
	
	public IP reduce()
	{
		IP returnIp = new IP();
		
		int fr = f;
		int m = mask - 1;
		int shift = (32 - m);
		
		if (m == 0)
			return null;
		
		fr >>= shift;
		fr <<= shift;
		
		returnIp.mask = m;
		returnIp.f = fr;
		
		return returnIp;
	}
	
	@Override
	public String toString()
	{
		final long aMask = 0xFF000000;
		final long bMask = 0x00FF0000;
		final long cMask = 0x0000FF00;
		final long dMask = 0x000000FF;
		
		long a = ((f & aMask) >> 24);
		long b = ((f & bMask) >> 16);
		long c = ((f & cMask) >> 8);
		long d = ((f & dMask));
		
		return a + "." + b + "." + c + "." + d + "/" + mask;
	}
}
