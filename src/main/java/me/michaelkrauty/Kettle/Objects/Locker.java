package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created on 6/29/2014.
 *
 * @author michaelkrauty
 */
public class Locker {

	File lockerFile;
	YamlConfiguration lockerData = new YamlConfiguration();

	private final Kettle kettle;
	private Location location;
	private UUID owner;
	private ArrayList<UUID> users;
	private long lastInteract;

	public Locker(Kettle kettle, Location loc) {
		this.kettle = kettle;
		lockerFile = new File(kettle.getDataFolder() + "/lockers/" + kettle.locationToString(location) + ".yml");
		checkLockerFile();
		reloadLockerFile();
		location = loc;
		owner = UUID.fromString(lockerData.getString("owner"));
		users = new ArrayList<UUID>();
		for (String str : lockerData.getString("users").split(",")) {
			if (!str.equals(""))
				users.add(UUID.fromString(str));
		}
		lastInteract = lockerData.getLong("lastinteract");
		saveLockerFile();
		reloadLockerFile();
	}

	public Locker(Kettle kettle, Location loc, UUID owner) {
		this.kettle = kettle;
		lockerFile = new File(kettle.getDataFolder() + "/lockers/" + kettle.locationToString(location) + ".yml");
		checkLockerFile();
		reloadLockerFile();
		location = loc;
		this.owner = owner;
		users = new ArrayList<UUID>();
		users.add(owner);
		lastInteract = System.currentTimeMillis();
		saveLockerFile();
		reloadLockerFile();
	}

	public void addUser(UUID user) {
		users.add(user);
	}

	public void removeUser(UUID user) {
		users.remove(user);
	}

	public Location getLocation() {
		return location;
	}

	public UUID getOwner() {
		return owner;
	}

	public ArrayList<UUID> getUsers() {
		return users;
	}

	public long getLastInteract() {
		return lastInteract;
	}

	public String getUserNames() {
		ArrayList<String> userNames = new ArrayList<String>();
		ArrayList<UUID> userUUIDS = new ArrayList<UUID>();
		for (UUID uuid : getUsers()) {
			String name = kettle.getServer().getOfflinePlayer(uuid).getName();
			userNames.add(name);
			userUUIDS.add(uuid);
		}
		return userNames.toString();
	}

	public boolean userHasAccess(UUID user) {
		return users.contains(user);
	}

	public boolean userIsOwner(UUID user) {
		return owner.equals(user);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public void setUsers(ArrayList<UUID> users) {
		this.users = users;
	}

	public void setLastInteract() {
		lastInteract = System.currentTimeMillis();
	}

	public void delete() {
		lockerFile.delete();
		kettle.lockers.remove(this);
	}

	public void unload() {
		saveLockerFile();
		kettle.lockers.remove(this);
	}

	private boolean checkLockerFile() {
		boolean exists = !lockerFile.exists();
		if (!lockerFile.exists()) {
			try {
				lockerFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return exists;
	}

	public void reloadLockerFile() {
		try {
			lockerData.load(lockerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveLockerFile() {
		try {
			lockerData.save(lockerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
