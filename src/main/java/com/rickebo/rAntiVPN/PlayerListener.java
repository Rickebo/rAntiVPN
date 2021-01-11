package com.rickebo.rAntiVPN;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PlayerListener implements Listener
{
	private Plugin plugin;
	private HashSet<UUID> whitelistedIds;
	
	public PlayerListener(Plugin plugin, List<String> whitelistedIds)
	{
		this.whitelistedIds = new HashSet<>();
		this.plugin = plugin;
		this.plugin.getProxy().getPluginManager().registerListener(plugin, this);
		
		for (String str : whitelistedIds)
		{
			try
			{
				UUID uuid = UUID.fromString(str);
				this.whitelistedIds.add(uuid);
			} catch (Throwable ex)
			{
				plugin.getLogger().severe("UUID \"" + str + "\" could not be parsed.");
			}
		}
		
		if (whitelistedIds.size() > 0)
			this.plugin.getLogger().info(whitelistedIds.size() + " whitelisted UUID's have been loaded.");
	}
	
	@EventHandler
	public void onJoin(LoginEvent event)
	{
		try
		{
			PendingConnection connection = event.getConnection();
			String ip = connection.getAddress().getAddress().toString();
			
			if (G.database == null || !G.database.isInitialized())
			{
				cancelJoin(event, "The server is still starting, please wait.",
						"Kicking " + ip + ", rAntiVPN is still initializing.");
				
				return;
			}
			
			
			if (ip.startsWith("/") || ip.startsWith("\\"))
				ip = ip.substring(1);
			
			IP playerIp;
			
			try
			{
				playerIp = IP.parseIp(ip);
			} catch (InvalidIpException ex)
			{
				plugin.getLogger().severe("An exception has occurred while parsing player IP \"" + ip + "\": " + ex.getMessage());
				ex.printStackTrace();
				
				if (G.settings.isBlockInvalidIps())
					cancelJoin(event, "You have connected from an invalid IP address which is not allowed on this server.", "\"" + ip  + "\" invalid IP address.");
				
				return;
			}
			
			if (!G.database.containsIp(playerIp))
				return;
			
			String kickMessage = G.settings.getKickMessage();
			
			kickMessage = kickMessage.replace("%ip%", ip);
			cancelJoin(event, kickMessage, "\"" + ip + "\" is a known VPN/proxy IP address.");
		} catch (Throwable ex)
		{
			plugin.getLogger().severe("An exception has occurred while processing player join event: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void cancelJoin(LoginEvent event, String message, String logMessage)
	{
		if (whitelistedIds.size() > 0 && event.getConnection() != null)
		{
			UUID uuid = event.getConnection().getUniqueId();
			if (uuid != null && whitelistedIds.contains(uuid))
				return;
		}
		
		plugin.getLogger().info("Denying server access for \"" + event.getConnection().getName() + "\": " + logMessage);
		
		event.setCancelReason(TextComponent.fromLegacyText(message));
		event.setCancelled(true);
	}
}
