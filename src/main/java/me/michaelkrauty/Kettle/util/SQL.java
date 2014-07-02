package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;

import java.net.InetSocketAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created on 6/27/2014.
 *
 * @author michaelkrauty
 */
public class SQL {

	private Kettle kettle;

	public SQL(Kettle instance) {
		kettle = instance;
		if (!openConnection())
			return;
		checkTables();
	}

	private Connection connection;

	public synchronized boolean openConnection() {
		try {
			// connection = DriverManager.getConnection("jdbc:mysql://" + kettle.configFile.getString("db_host") + ":" + kettle.configFile.getInt("db_port") + "/" + kettle.configFile.getString("db_database"), kettle.configFile.getString("db_user"), kettle.configFile.getString("db_pass"));
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kettle", "kettle", null);
		} catch (Exception e) {
			System.out.println("COULD NOT CONNECT TO SQL DATABASE");
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public synchronized void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized boolean checkTables() {
		boolean res = true;
		try {
			PreparedStatement stmt;
			stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (uuid varchar(256) PRIMARY KEY, username varchar(256), admin tinyint, ip varchar(256), firstlogin long, lastlogin long, power long);");
			stmt.execute();
			stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `lockers` (location varchar(256) PRIMARY KEY, owner varchar(256), users varchar(256), lastinteract long);");
			stmt.execute();
			stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `factions`(name varchar(255) PRIMARY KEY, desc varchar(255), members varchar(255), allies varchar(255), enemies varchar(255), land varchar(255));");
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	public synchronized boolean lockerExists(Location location) {
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM `lockers` WHERE `location`=?;");
			sql.setString(1, kettle.locationToString(location));
			ResultSet resultSet = sql.executeQuery();
			boolean contains = resultSet.next();

			return contains;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized boolean factionExists(String name) {
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM `factions` WHERE `name`=?;");
			sql.setString(1, name);
			ResultSet resultSet = sql.executeQuery();
			boolean contains = resultSet.next();

			return contains;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized void addLocker(Location location, UUID owner) {
		try {
			if (!lockerExists(location)) {
				PreparedStatement sql = connection.prepareStatement("INSERT INTO `lockers`(`location`, `owner`, `users`, `lastinteract`) VALUES (?,?,?,?)");
				sql.setString(1, kettle.locationToString(location));
				sql.setString(2, owner.toString());
				sql.setString(3, owner.toString());
				sql.setLong(4, System.currentTimeMillis());
				sql.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized HashMap getLocker(Location location) {
		try {
			if (lockerExists(location)) {
				PreparedStatement sql = connection.prepareStatement("SELECT * FROM `lockers` WHERE `location`=?;");
				sql.setString(1, kettle.locationToString(location));

				ResultSet res = sql.executeQuery();
				res.next();
				HashMap ret = new HashMap();
				ret.put("owner", UUID.fromString(res.getString("owner")));
				ret.put("users", res.getString("users"));
				ret.put("lastinteract", Long.toString(res.getLong("lastinteract")));
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized HashMap getFaction(String name) {
		try {
			if (factionExists(name)) {
				PreparedStatement sql = connection.prepareStatement("SELECT * FROM `factions` WHERE `name`=?");
				sql.setString(1, name);

				ResultSet res = sql.executeQuery();
				res.next();
				HashMap ret = new HashMap();
				ret.put("name", name);
				ret.put("description", res.getString("desc"));
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized void updateLocker(Location location, UUID owner, ArrayList<UUID> users, long lastInteract) {
		try {
			PreparedStatement sql = connection.prepareStatement("UPDATE `lockers` SET `owner`=?, `users`=?, `lastinteract`=? WHERE `location`=?;");
			sql.setString(1, owner.toString());
			String userString = "";
			for (int i = 0; i < users.size(); i++) {
				if (i == 0)
					userString = users.get(i).toString();
				else
					userString = userString + "," + users.get(i).toString();
			}
			sql.setString(2, userString);
			sql.setLong(3, lastInteract);
			sql.setString(4, kettle.locationToString(location));
			sql.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized ArrayList<Location> getAllLockers() {
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `lockers`;");
			ResultSet res = sql.executeQuery();
			ArrayList<Location> locked = new ArrayList<Location>();
			while (!res.isLast()) {
				res.next();
				String[] locString = res.getString("location").split(",");
				locked.add(new Location(kettle.getServer().getWorld(locString[0]), Integer.parseInt(locString[1]), Integer.parseInt(locString[2]), Integer.parseInt(locString[3])));
			}
			res.close();
			return locked;
		} catch (Exception ignored) {
		}
		return null;
	}

	public synchronized boolean removeLocker(Location location) {
		try {
			if (lockerExists(location)) {
				PreparedStatement sql = connection.prepareStatement("DELETE FROM `lockers` WHERE `location`=?;");
				sql.setString(1, kettle.locationToString(location));
				return sql.execute();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean updateLockerUsers(Location location, String users) {
		try {
			if (lockerExists(location)) {
				PreparedStatement sql = connection.prepareStatement("UPDATE `lockers` SET `users`=?, WHERE `location`=?");
				sql.setString(1, users);
				sql.setString(2, kettle.locationToString(location));
				sql.executeUpdate();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean updateLockerExpiry(Location location, long expiry) {
		try {
			if (lockerExists(location)) {
				PreparedStatement sql = connection.prepareStatement("UPDATE `lockers` SET `expiry`=?, WHERE `location`=?");
				sql.setLong(1, expiry);
				sql.setString(2, kettle.locationToString(location));
				sql.executeUpdate();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
