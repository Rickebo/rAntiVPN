package com.rickebo.rAntiVPN;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener
{
	private Plugin plugin;
	
	public PlayerListener(Plugin plugin)
	{
		this.plugin = plugin;
		this.plugin.getProxy().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void onJoin(PreLoginEvent event)
	{
		try
		{
			PendingConnection connection = event.getConnection();
			String ip = connection.getAddress().getAddress().toString();
			
			if (ip.startsWith("/") || ip.startsWith("\\"))
				ip = ip.substring(1);
			
			IP playerIp = G.database.parseIp(ip);
			if (!G.database.containsIp(playerIp))
				return;
			
			String kickMessage = G.settings.getKickMessage();
			
			kickMessage = kickMessage.replace("%ip%", ip);
			
			event.setCancelReason(TextComponent.fromLegacyText(kickMessage));
			event.setCancelled(true);
		} catch (Throwable ex)
		{
			plugin.getLogger().severe("An exception has occurred while processing player join event: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
