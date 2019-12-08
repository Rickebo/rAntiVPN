package com.rickebo.rAntiVPN;

import com.rickebo.rAntiVPN.settings.SettingsLoader;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Initializer extends Plugin
{
	
	@Override
	public void onEnable()
	{
		try
		{
			G.plugin = this;
			beginInitialization();
		} catch (IOException ex)
		{
			getLogger().severe("An exception has occurred while initializing rAntiVPN.:" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void beginInitialization() throws IOException
	{
		initSettings();
		initDatabase();
		initEvents();
	}
	
	public void initSettings() throws IOException
	{
		SettingsLoader loader = new SettingsLoader("plugins", "rAntiVPN");
		
		G.settings = loader.loadSettings();
	}
	
	public void initDatabase()
	{
		getLogger().info("Downloading from sources...");
		Database database = new Database();
		Downloader downloader = new Downloader(database::inputLine);
		
		for (String source : G.settings.getSources())
			downloader.download(source);
		
		G.database = database;
		getLogger().info("Download has finished.");
	}
	
	public void initEvents()
	{
		G.listener = new PlayerListener(G.plugin);
	}
}
