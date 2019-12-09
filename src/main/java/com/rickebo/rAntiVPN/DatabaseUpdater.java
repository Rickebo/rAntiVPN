package com.rickebo.rAntiVPN;

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
		
		for (String source : G.settings.getSources())
			downloader.download(source);
		
		updateFunction.accept(database);
		logFunction.accept("Database initialization has finished in " + (System.currentTimeMillis() - time) + " ms.");
	}
}
