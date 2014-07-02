package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Date;

/**
 * Created on 7/1/2014.
 *
 * @author michaelkrauty
 */
public class User {

	File playerFile;
	YamlConfiguration playerData = new YamlConfiguration();

	private final Kettle kettle;
	private final Player player;
	private Date firstLogin;
	private Date lastLogin;
	private boolean admin;
	private boolean teleportEnabled = true;


	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
		playerFile = new File(kettle.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
		boolean first = checkPlayerFile();
		reloadPlayerFile();
		if (first) {
			playerData.set("firstlogin", new Date(System.currentTimeMillis()));
			firstLogin = new Date(System.currentTimeMillis());
			playerData.set("admin", false);
			admin = false;
		}
		playerData.set("lastlogin", new Date(System.currentTimeMillis()));
		lastLogin = new Date(System.currentTimeMillis());
		admin = playerData.getBoolean("admin");
		savePlayerFile();
		reloadPlayerFile();
	}


	/**
	 * UTIL
	 */

	private boolean checkPlayerFile() {
		boolean exists = !playerFile.exists();
		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return exists;
	}

	public void reloadPlayerFile() {
		try {
			playerData.load(playerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePlayerFile() {
		try {
			playerData.save(playerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * GET
	 */

	public Player getPlayer() {
		return this.player;
	}

	public Date getFirstLogin() {
		return this.firstLogin;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public boolean teleportEnabled() {
		return this.teleportEnabled;
	}


	/**
	 * SET
	 */

	public void setTeleportEnabled(boolean bool) {
		this.teleportEnabled = bool;
	}
}
