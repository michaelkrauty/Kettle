package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created on 6/29/2014.
 *
 * @author michaelkrauty
 */
public class Locker {

	private final Kettle kettle;
	private Location location;
	private UUID owner;
	private ArrayList<UUID> users;
	private long lastInteract;

	public Locker(Kettle kettle, Location loc) {
		this.kettle = kettle;
		HashMap info = kettle.sql.getLocker(loc);
		location = loc;
		owner = (UUID) info.get("owner");
		users = new ArrayList<UUID>();
		for (String str : ((String) info.get("users")).split(",")) {
			if (!str.equals(""))
			users.add(UUID.fromString(str));
		}
		lastInteract = Long.parseLong((String) info.get("lastinteract"));
	}

	public void saveInfo() {
		kettle.sql.updateLocker(location, owner, users, lastInteract);
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
		kettle.sql.removeLocker(location);
		kettle.lockers.remove(this);
	}

	public void unload() {
		saveInfo();
		kettle.lockers.remove(this);
	}
}
