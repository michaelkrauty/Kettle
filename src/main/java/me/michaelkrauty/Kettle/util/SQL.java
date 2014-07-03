package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
