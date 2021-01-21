package com.rickebo.rAntiVPN;

public class IP
{
	public int f;
	public int mask;
	
	public IP()
	{
	
	}
	
	public IP(long l)
	{
		f = (int) ((l << 32) >> 32);
		mask = (int) (l >> 32);
	}
	
	public IP(int f, int mask)
	{
		this.f = f;
		this.mask = mask;
	}
	
	public long toLong()
	{
		return (long) f | ((long) mask << 32);
	}
	
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
		if (text == null)
			throw new InvalidIpException("Null is not a valid IP address.");
		
		String[] parts = text.split("/");
		
		if (parts.length == 0)
			throw new InvalidIpException("Null is not a valid IP address.");
		
		String ipStr = parts[0];
		String maskStr = parts.length >= 2 ? parts[1] : "32";
		
		String[] ipParts = ipStr.split("\\.");
		
		if (ipParts.length != 4)
			throw new InvalidIpException("IP address \"" + text + "\" is invalid.");
		
		long a,b,c,d;
		
		try
		{
			a = Long.parseLong(ipParts[0]);
			b = Long.parseLong(ipParts[1]);
			c = Long.parseLong(ipParts[2]);
			d = Long.parseLong(ipParts[3]);
		} catch (RuntimeException ex)
		{
			throw new InvalidIpException("Could not parse IP address parts for the following IP: \"" + text + "\".", ex);
		}
		
		if (a < 0 || a > 255 || b < 0 || b > 255 || c < 0 || c > 255 || d < 0 || d > 255)
			throw new InvalidIpException("IP address part is outside bounds a valid IP address part (0-255): \"" + text + "\".");
		
		int mask;
		try
		{
			mask = Integer.parseInt(maskStr);
		} catch (RuntimeException ex)
		{
			throw new InvalidIpException("IP mask is invalid: \"" + text + "\".", ex);
		}
		
		if (mask < 0 || mask > 32)
			throw new InvalidIpException("IP mask is outside bounds of valid IP mask: \"" + text + "\".");
		
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
