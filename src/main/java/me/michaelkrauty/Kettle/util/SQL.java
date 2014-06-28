package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;

import java.net.InetSocketAddress;
import java.sql.*;
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
		checkTables();
	}

	private Connection connection;

	private synchronized boolean openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://192.187.118.202:3306/KettleTest", kettle.configFile.getString("db_user"), kettle.configFile.getString("db_pass"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private synchronized void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized boolean checkTables() {
		if (openConnection()) {
			boolean res = true;
			try {
				PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (uuid varchar(256) PRIMARY KEY, username varchar(256), ip varchar(256), firstlogin long, lastlogin long);");
				stmt.execute();
			} catch (Exception e) {
				e.printStackTrace();
				res = false;
			}
			closeConnection();
			return res;
		}
		return false;
	}

	public synchronized boolean userExists(UUID userUUID) {
		openConnection();
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM `users` WHERE UUID=?;");
			sql.setString(1, userUUID.toString());
			ResultSet resultSet = sql.executeQuery();
			boolean containsServer = resultSet.next();

			sql.close();
			resultSet.close();
			closeConnection();

			return containsServer;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized void addUser(UUID uuid) {
		try {
			if (!userExists(uuid)) {
				openConnection();
				PreparedStatement sql = connection.prepareStatement("INSERT INTO `users`(`uuid`, `username`, `ip`, `firstlogin`, `lastlogin`) VALUES (?,?,?,?,?)");
				sql.setString(1, uuid.toString());
				sql.setString(2, kettle.getServer().getPlayer(uuid).getName());
				sql.setString(3, kettle.getServer().getPlayer(uuid).getAddress().toString());
				sql.setLong(4, System.currentTimeMillis());
				sql.setLong(5, System.currentTimeMillis());

				int res = sql.executeUpdate();
				System.out.println(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public synchronized HashMap<String, Object> getUser(UUID uuid) {
		try {
			if (userExists(uuid)) {
				openConnection();
				PreparedStatement sql = connection.prepareStatement("SELECT * FROM `users` WHERE `uuid`=?");
				sql.setString(1, uuid.toString());

				ResultSet res = sql.executeQuery();
				res.next();
				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("uuid", UUID.fromString(res.getString("uuid")));
				ret.put("username", res.getString("username"));
				ret.put("ip", new InetSocketAddress(res.getString("ip").split(":")[0].replace("/", ""), Integer.parseInt(res.getString("ip").split(":")[1])));
				ret.put("firstlogin", new Date(res.getLong("firstlogin")));
				ret.put("lastlogin", new Date(res.getLong("lastlogin")));
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public synchronized boolean updateUsername(UUID uuid) {
		try {
				openConnection();
				PreparedStatement sql = connection.prepareStatement("UPDATE `users` SET `username`=? WHERE `uuid`=?");
				sql.setString(1, kettle.getServer().getPlayer(uuid).getName());
				sql.setString(2, uuid.toString());
				sql.executeUpdate();
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	public synchronized boolean updateIP(UUID uuid) {
		try {
				openConnection();
				PreparedStatement sql = connection.prepareStatement("UPDATE `users` SET `ip`=? WHERE `uuid`=?");
				sql.setString(1, kettle.getServer().getPlayer(uuid).getAddress().toString());
				sql.setString(2, uuid.toString());
				sql.executeUpdate();
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	public synchronized boolean updateLastLogin(UUID uuid) {
		try {
				openConnection();
				PreparedStatement sql = connection.prepareStatement("UPDATE `users` SET `lastlogin`=? WHERE `uuid`=?");
				sql.setLong(1, System.currentTimeMillis());
				sql.setString(2, uuid.toString());
				sql.executeUpdate();
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}
}
