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
		G.database = new Database();
		G.updater = new DatabaseUpdater(G.settings.getUpdateInterval() * 1000, G.settings.getUpdateDelay(),
		                                db -> G.database = db,
		                                str -> getLogger().info(str));
		
		G.updater.start();
	}
	
	public void initTimer()
	{
	
	}
	
	public void initEvents()
	{
		G.listener = new PlayerListener(G.plugin);
	}
}
