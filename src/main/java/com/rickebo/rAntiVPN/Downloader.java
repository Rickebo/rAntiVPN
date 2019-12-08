package com.rickebo.rAntiVPN;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

public class Downloader
{
	private Consumer<String> lineReader;
	
	public Downloader(Consumer<String> lineReader)
	{
		this.lineReader = lineReader;
	}
	
	public void download(String source)
	{
		try (
				BufferedInputStream in = new BufferedInputStream(new URL(source).openStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))
			) {
			String line;
			while ((line = reader.readLine()) != null)
				lineReader.accept(line);
		} catch (IOException ex ) {
			System.out.println("An exception has occurred while downloading from a source: " + ex.getMessage());
			ex.printStackTrace();
			// handle exception
		}
	}
	
	public void download(String... sources)
	{
		for (String source : sources)
			download(source);
	}
}
