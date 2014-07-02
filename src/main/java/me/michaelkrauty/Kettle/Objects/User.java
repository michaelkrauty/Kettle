package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.entity.Player;

/**
 * Created on 7/1/2014.
 *
 * @author michaelkrauty
 */
public class User {

	private final Kettle kettle;
	private final Player player;

	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
	}



	/** GET */

	public Player getPlayer() {
		return this.player;
	}


	/** SET */
}
