package com.rickebo.rAntiVPN;

import org.yaml.snakeyaml.reader.StreamReader;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class DatabaseUpdater
{
	private Consumer<Database> updateFunction;
	private Consumer<String> logFunction;
	private long interval;
	private long delay;
	
	public DatabaseUpdater(long interval, long delay, Consumer<Database> updateFunction, Consumer<String> logFunction)
	{
		this.interval = interval;
		this.delay = delay;
		this.updateFunction = updateFunction;
		this.logFunction = logFunction;
	}
	
	public void start()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				update();
			}
		}, delay, interval);
	}
	
	public void update()
	{
		logFunction.accept("Updating database from sources...");
		long time = System.currentTimeMillis();
		
		Database database = new Database();
		Downloader downloader = new Downloader(database::inputLine);
		
		if (G.settings.getSources() != null)
		{
			for (String source : G.settings.getSources())
				downloader.download(source);
		}
		
		if (G.settings.getSourceDirectories() != null)
		{
			for (String dir : G.settings.getSourceDirectories())
			{
				File sourceDir = new File(dir);
				
				if (sourceDir.exists())
				{
					File[] files = sourceDir.listFiles();
					
					if (files != null)
					{
						for (File contentFile : files)
						{
							if (contentFile.isDirectory())
								continue;
							
							try
							{
								BufferedReader reader = new BufferedReader(new FileReader(contentFile));
								
								String line;
								while ((line = reader.readLine()) != null)
									database.inputLine(line);
								
							} catch (FileNotFoundException ex)
							{
								logFunction.accept("Reading file failed: " + contentFile.getName() +
										" could not be found");
							} catch (IOException ex)
							{
								logFunction.accept("An exception has occurred while reading " + contentFile.getName());
								ex.printStackTrace();
							}
						}
					}
				}
			}
		}
		
		database.setInitialized();
		
		long elapsed = System.currentTimeMillis() - time;
		int loaded = database.size();
		
		updateFunction.accept(database);
		logFunction.accept("Database initialization has loaded " + loaded + " entries in " + elapsed + " ms.");
	}
}
