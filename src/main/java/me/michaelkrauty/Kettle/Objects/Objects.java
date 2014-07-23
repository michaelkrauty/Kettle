package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.UUID;

/**
 * Created on 7/8/2014.
 *
 * @author michaelkrauty
 */
public class Objects {

	private final Kettle kettle;

	public ArrayList<User> users = new ArrayList<User>();

	public ArrayList<Locker> lockers = new ArrayList<Locker>();

	public ArrayList<Faction> factions = new ArrayList<Faction>();

	public Objects(Kettle kettle) {
		this.kettle = kettle;
	}


	public void loadObjects() {
		loadLockers();
		loadUsers();
		loadFactions();
	}

	public void unloadObjects() {
		for (Locker locker : lockers) {
			locker.unload();
		}
		unloadUsers();
		unloadFactions();
	}

	/**
	 * LOCKERS
	 */

	public void loadLockers() {
		for (File file : new File(kettle.getDataFolder() + "/lockers").listFiles()) {
			String locString = file.getName().split("\\.")[0];
			String[] loc = locString.split(",");
			lockers.add(new Locker(kettle, new Location(kettle.getServer().getWorld(loc[0]), Integer.parseInt(loc[1]), Integer.parseInt(loc[2]), Integer.parseInt(loc[3]))));
		}
	}

	public Locker getLocker(Location loc) {
		if (lockers.size() != 0) {
			for (int i = 0; i < lockers.size(); i++) {
				if (kettle.locationToString(lockers.get(i).getLocation()).equals(kettle.locationToString(loc))) {
					return lockers.get(i);
				}
			}
		}
		return null;
	}

	public void createLocker(Location loc, Player owner) {
		createLocker(loc, owner.getUniqueId());
	}

	public void createLocker(Location loc, UUID owner) {
		if (!lockerExists(loc)) {
			lockers.add(new Locker(kettle, loc, owner));
		}
	}

	public boolean lockerExists(Location loc) {
		return getLocker(loc) != null;
	}

	public void copyLocker(Location loc1, Location loc2) {
		createLocker(loc2, getLocker(loc1).getOwner());
		getLocker(loc2).setUsers(getLocker(loc1).getUsers());
		getLocker(loc2).setLastInteract();
	}


	/**
	 * USERS
	 */

	public void loadUsers() {
		for (Player player : kettle.getServer().getOnlinePlayers()) {
			if (getUser(player) == null)
				users.add(new User(kettle, player));
		}
	}

	public User getUser(Player player) {
		if (users.size() != 0) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getPlayer() == player) {
					return users.get(i);
				}
			}
		}
		return null;
	}

	public void unloadUsers() {
		try {
			for (User user : users) {
				user.savePlayerFile();
				users.remove(user);
			}
		} catch (ConcurrentModificationException ignored) {
		}
	}


	/**
	 * FACTIONS
	 */

	public Faction getFaction(String name) {
		if (factions.size() != 0) {
			for (int i = 0; i < factions.size(); i++) {
				if (factions.get(i).getName().equalsIgnoreCase(name)) {
					return factions.get(i);
				}
			}
		}
		return null;
	}

	public boolean factionExists(String name) {
		return getFaction(name) != null;
	}

	public void createFaction(String name, UUID owner) {
		if (!factionExists(name)) {
			Faction faction = new Faction(kettle, name, owner);
			faction.addMember(owner);
			factions.add(faction);
		}
	}

	public void loadFactions() {
		for (File file : new File(kettle.getDataFolder() + "/factions").listFiles()) {
			String name = file.getName().split("\\.")[0];
			factions.add(new Faction(kettle, name));
		}
	}

	public void unloadFactions() {
		try {
			for (Faction faction : factions) {
				faction.saveFactionFile();
				factions.remove(faction);
			}
		} catch (ConcurrentModificationException ignored) {
		}
	}
}
