package me.michaelkrauty.Kettle.listener;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

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

	public void onPlayerLogin(PlayerLoginEvent event) {

	}
}
