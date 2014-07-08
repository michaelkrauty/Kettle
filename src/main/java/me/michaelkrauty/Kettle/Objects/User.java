package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
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
	private Location lastLocation;
	private String faction;


	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
		playerFile = new File(kettle.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
		boolean first = checkPlayerFile();
		reloadPlayerFile();
		if (first) {
			setFirstLogin(new Date(System.currentTimeMillis()));
			playerData.set("admin", false);
			admin = false;
			setFaction(null);
		}
		loadInfo();
		savePlayerFile();
		reloadPlayerFile();
	}


	/**
	 * UTIL
	 */

	private void loadInfo() {
		if (playerData.getString("lastlocation") != null)
			lastLocation = kettle.stringToLocation(playerData.getString("lastlocation"));
		playerData.set("lastlogin", new Date(System.currentTimeMillis()));
		lastLogin = new Date(System.currentTimeMillis());
		admin = playerData.getBoolean("admin");
		faction = playerData.getString("faction");
	}

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
			loadInfo();
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

	public Location getLastLocation() {
		return this.lastLocation;
	}

	public String getFaction() {
		return this.faction;
	}


	/**
	 * SET
	 */

	public void setTeleportEnabled(boolean bool) {
		this.teleportEnabled = bool;
		savePlayerFile();
	}

	public void setLastLocation(Location location) {
		lastLocation = location;
		playerData.set("lastlocation", kettle.locationToString(location));
		savePlayerFile();
	}

	public void setFaction(String faction) {
		this.faction = faction;
		playerData.set("faction", faction);
		savePlayerFile();
	}

	public void setFirstLogin(Date firstLogin) {
		this.firstLogin = firstLogin;
		playerData.set("firstlogin", firstLogin);
		savePlayerFile();
	}


	/**
	 * VOID
	 */

	public void teleport(Location location) {
		setLastLocation(player.getLocation());
		player.teleport(location);
	}

	public void teleport(Entity entity) {
		setLastLocation(player.getLocation());
		player.teleport(entity);
	}
}
