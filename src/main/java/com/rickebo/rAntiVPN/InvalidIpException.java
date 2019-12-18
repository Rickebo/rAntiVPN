package com.rickebo.rAntiVPN;

public class InvalidIpException extends RuntimeException
{
	public InvalidIpException(String message, Throwable subException)
	{
		super(message, subException);
	}
	public InvalidIpException(String message)
	{
		super(message);
	}
	
	public InvalidIpException()
	{
		super();
	}
}
