package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.Groups;
import me.michaelkrauty.Kettle.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

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
	private boolean adminLoggedIn;
	private boolean teleportEnabled;
	private Location lastLocation;
	private String faction;
	private int balance;
	private String prefix;
	private String suffix;
	private String group;
	private boolean muted;
	private int muteTime;
	private ArrayList<String> permissions;
	private UUID teleportRequester;
	private int teleportRequestTimeout;


	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
		playerFile = new File(kettle.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
		boolean exists = checkPlayerFile();
		admin = false;
		adminLoggedIn = false;
		teleportEnabled = true;
		lastLocation = null;
		faction = null;
		balance = 1000;
		prefix = null;
		suffix = null;
		muted = false;
		muteTime = -1;
		teleportRequester = null;
		teleportRequestTimeout = -1;
		permissions = kettle.defaultPermissions;
		group = "lame";
		if (!exists) {
			kettle.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Welcome " + ChatColor.RESET + ChatColor.BOLD + player.getName() + ChatColor.GREEN + ChatColor.BOLD + " to the server!");
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
		admin = playerData.getBoolean("admin");
		teleportEnabled = playerData.getBoolean("teleportenabled");
		if (playerData.getString("lastLocation") != null)
			lastLocation = kettle.stringToLocation(playerData.getString("lastlocation"));
		faction = playerData.getString("faction");
		balance = playerData.getInt("balance");
		prefix = playerData.getString("prefix");
		suffix = playerData.getString("suffix");
		muted = playerData.getBoolean("muted");
		muteTime = playerData.getInt("mutetime");
		group = playerData.getString("group");
		permissions = new ArrayList<String>(playerData.getStringList("permissions"));
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
			if (prefix != null)
				playerData.set("prefix", prefix);
			if (suffix != null)
				playerData.set("suffix", suffix);
			playerData.set("muted", muted);
			playerData.set("mutetime", muteTime);
			playerData.set("permissions", permissions);
			playerData.set("group", group);
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

	public String getGroup() {
		return group;
	}

	public boolean hasPermission(String key) {
		if (adminLoggedIn)
			return true;
		else
			return permissions.contains(key);

	}

	public String getPrefix() {
		if (prefix != null)
			return prefix;
		else
			return Groups.getPrefix(group);
	}

	public String getSuffix() {
		if (suffix != null)
			return suffix;
		else
			return Groups.getSuffix(group);
	}

	public boolean isMuted() {
		return muted;
	}

	public boolean isAdminLoggedIn() {
		return adminLoggedIn;
	}

	public ArrayList<String> getPermissions() {
		return permissions;
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

	public void setGroup(String group) {
		this.group = group;
	}

	public void setAdminLoggedIn(boolean adminLoggedIn) {
		this.adminLoggedIn = adminLoggedIn;
	}

	public void setNickname(String nickname) {
		player.setDisplayName(nickname);
		player.setPlayerListName(Util.stripColor(nickname));
		player.setCustomName(Util.stripColor(nickname));
		player.setCustomNameVisible(true);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

	public void heal() {
		player.setHealth(20);
	}

	public void feed() {
		player.setFoodLevel(20);
		player.setSaturation(10);
	}

	public void mute() {
		muted = true;
		muteTime = 1000;
	}

	public void mute(int time) {
		muted = true;
		muteTime = time;
	}

	public void unmute() {
		muted = false;
		muteTime = -1;
	}

	public void requestTeleport(UUID player) {
		teleportRequester = player;
		teleportRequestTimeout = 30;
	}

	public void addPermission(String permission) {
		permissions.add(permission);
	}

	public void removePermission(String permission) {
		permissions.remove(permission);
	}
}
