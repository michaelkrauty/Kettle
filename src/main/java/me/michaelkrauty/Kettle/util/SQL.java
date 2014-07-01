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
		openConnection();
		checkTables();
	}

	private Connection connection;

	public synchronized boolean openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://192.187.118.202:3306/KettleTest", kettle.configFile.getString("db_user"), kettle.configFile.getString("db_pass"));
			if (connection == null) {
				System.out.println("KETTLE COULD NOT CONNECT TO SQL DATABSE! SHUTTING DOWN.");
				kettle.getServer().shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (uuid varchar(256) PRIMARY KEY, username varchar(256), admin tinyint, ip varchar(256), firstlogin long, lastlogin long);");
			stmt.execute();
			stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `lockers` (location varchar(256) PRIMARY KEY, owner varchar(256), users varchar(256), lastinteract long);");
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	public synchronized boolean userExists(UUID userUUID) {
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM `users` WHERE UUID=?;");
			sql.setString(1, userUUID.toString());
			ResultSet resultSet = sql.executeQuery();
			boolean containsServer = resultSet.next();

			return containsServer;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized boolean lockerExists(Location location) {
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM `lockers` WHERE location=?;");
			sql.setString(1, kettle.locationToString(location));
			ResultSet resultSet = sql.executeQuery();
			boolean contains = resultSet.next();

			return contains;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized void addUser(UUID uuid) {
		try {
			if (!userExists(uuid)) {
				PreparedStatement sql = connection.prepareStatement("INSERT INTO `users`(`uuid`, `username`, `admin`, `ip`, `firstlogin`, `lastlogin`) VALUES (?,?,?,?,?,?)");
				sql.setString(1, uuid.toString());
				sql.setString(2, kettle.getServer().getPlayer(uuid).getName());
				sql.setBoolean(3, false);
				sql.setString(4, kettle.getServer().getPlayer(uuid).getAddress().toString());
				sql.setLong(5, System.currentTimeMillis());
				sql.setLong(6, System.currentTimeMillis());
				sql.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public synchronized HashMap getUser(UUID uuid) {
		try {
			if (userExists(uuid)) {
				PreparedStatement sql = connection.prepareStatement("SELECT * FROM `users` WHERE `uuid`=?");
				sql.setString(1, uuid.toString());

				ResultSet res = sql.executeQuery();
				res.next();
				HashMap ret = new HashMap();
				ret.put("uuid", UUID.fromString(res.getString("uuid")));
				ret.put("username", res.getString("username"));
				ret.put("ip", new InetSocketAddress(res.getString("ip").split(":")[0].replace("/", ""), Integer.parseInt(res.getString("ip").split(":")[1])));
				ret.put("firstlogin", new Date(res.getLong("firstlogin")));
				ret.put("lastlogin", new Date(res.getLong("lastlogin")));
				ret.put("admin", res.getBoolean("admin"));
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
				System.out.println("SQL: " + res.getString("location"));
				String[] locString = res.getString("location").split(",");
				locked.add(new Location(kettle.getServer().getWorld(locString[0]), Integer.parseInt(locString[1]), Integer.parseInt(locString[2]), Integer.parseInt(locString[3])));
			}
			res.close();
			return locked;
		} catch (Exception e) {
			e.printStackTrace();
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

	public synchronized boolean updateUsername(UUID uuid) {
		try {
			PreparedStatement sql = connection.prepareStatement("UPDATE `users` SET `username`=? WHERE `uuid`=?;");
			sql.setString(1, kettle.getServer().getPlayer(uuid).getName());
			sql.setString(2, uuid.toString());
			sql.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean updateIP(UUID uuid, InetSocketAddress address) {
		try {
			PreparedStatement sql = connection.prepareStatement("UPDATE `users` SET `ip`=? WHERE `uuid`=?;");
			sql.setString(1, address.toString());
			sql.setString(2, uuid.toString());
			sql.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean updateLastLogin(UUID uuid) {
		try {
			PreparedStatement sql = connection.prepareStatement("UPDATE `users` SET `lastlogin`=? WHERE `uuid`=?;");
			sql.setLong(1, System.currentTimeMillis());
			sql.setString(2, uuid.toString());
			sql.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
