package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.Group;
import me.michaelkrauty.Kettle.util.Groups;
import me.michaelkrauty.Kettle.util.Permission;
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
	private boolean teleportEnabled = true;
	private Location lastLocation;
	private String faction;
	private int balance;
	private String prefix;
	private String suffix;
	private Group group;
	private String nickname;
	private boolean muted;
	private int muteTime;
	private ArrayList<Permission> permissions;
	private UUID teleportRequester;
	private int teleportRequestTimeout;


	public User(Kettle kettle, Player player) {
		this.kettle = kettle;
		this.player = player;
		playerFile = new File(kettle.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
		boolean exists = checkPlayerFile();
		if (!exists) {
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
			group = Group.LAME;
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

	public Group getGroup() {
		return group;
	}

	public boolean hasPermission(Permission key) {
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

	public String getName() {
		if (nickname != null)
			return nickname;
		else
			return player.getName();
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

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setAdminLoggedIn(boolean adminLoggedIn) {
		this.adminLoggedIn = adminLoggedIn;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public void requestTeleport(UUID player) {
		teleportRequester = player;
		teleportRequestTimeout = 30;
	}
}
