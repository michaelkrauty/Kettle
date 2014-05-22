package me.michaelkrauty.Kettle.listener;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class PlayerListener implements Listener {

	private final Kettle kettle;

	public PlayerListener(Kettle instance) {
		kettle = instance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (kettle.playerFile.getPlayer(player) == null) {
			kettle.playerFile.newPlayer(player);
		}
		kettle.playerFile.setPlayerData(player.getUniqueId().toString(), "username", player.getName());
		for (String line : kettle.motdFile.getMOTD()) {
			player.sendMessage(line);
		}
	}
}
