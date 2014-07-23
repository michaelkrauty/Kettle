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
	private int balance;


	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
		playerFile = new File(kettle.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
		boolean exists = checkPlayerFile();
		if (!exists) {
			admin = false;
			teleportEnabled = true;
			lastLocation = null;
			faction = null;
			balance = 1000;
		} else
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
		balance = playerData.getInt("balance");
	}

	private boolean checkPlayerFile() {
		boolean exists = playerFile.exists();
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
			playerData.set("admin", admin);
			playerData.set("teleportenabled", teleportEnabled);
			if (lastLocation != null)
				playerData.set("lastlocation", kettle.locationToString(lastLocation));
			if (faction != null)
				playerData.set("faction", faction);
			playerData.set("balance", balance);
			playerData.save(playerFile);
			kettle.getLogger().info("Saved user: " + player.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * GET
	 */

	public Player getPlayer() {
		return player;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean teleportEnabled() {
		return teleportEnabled;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public String getFaction() {
		return faction;
	}

	public int getBalance() {
		return balance;
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

	public void setBalance(int balance) {
		this.balance = balance;
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
