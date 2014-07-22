package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created on 7/1/2014.
 *
 * @author michaelkrauty
 */
public class User {

	File playerFile;

	private final Kettle kettle;
	private final Player player;
	private boolean admin;
	private boolean teleportEnabled = true;
	private Location lastLocation;
	private String faction;


	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
		playerFile = new File(kettle.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
		boolean first = checkPlayerFile();
		if (first) {
			admin = false;
			setFaction(null);
		}
		loadInfo();
		kettle.getLogger().info("Loaded user: " + player.getName());
	}


	/**
	 * UTIL
	 */

	private void loadInfo() {
		YamlConfiguration playerData = new YamlConfiguration();
		try {
			playerData.load(playerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (playerData.getString("lastlocation") != null)
			lastLocation = kettle.stringToLocation(playerData.getString("lastlocation"));
		admin = playerData.getBoolean("admin");
		faction = playerData.getString("faction");
	}

	private boolean checkPlayerFile() {
		boolean exists = !playerFile.exists();
		if (!playerFile.exists()) {
			kettle.getServer().getScheduler().scheduleAsyncDelayedTask(kettle, new Runnable() {
				public void run() {
					try {
						playerFile.createNewFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		return exists;
	}

	public void savePlayerFile() {
		try {
			YamlConfiguration playerData = new YamlConfiguration();
			playerData.set("lastlocation", kettle.locationToString(lastLocation));
			playerData.set("faction", faction);
			playerData.save(playerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unload() {
		savePlayerFile();
		kettle.objects.users.remove(this);
		kettle.getLogger().info("Unloaded user: " + player.getName());
	}


	/**
	 * GET
	 */

	public Player getPlayer() {
		return this.player;
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
	}

	public void setLastLocation(Location location) {
		lastLocation = location;
	}

	public void setFaction(String faction) {
		this.faction = faction;
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
