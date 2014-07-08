package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Faction {

	File factionFile;
	YamlConfiguration factionData = new YamlConfiguration();

	private Kettle kettle;
	private String name;
	private String description;
	private ArrayList<UUID> members;
	private ArrayList<String> allies;
	private ArrayList<String> enemies;
	private ArrayList<Chunk> land;
	private int power;

	public Faction(Kettle kettle, String name) {
		this.kettle = kettle;
		factionFile = new File(kettle.getDataFolder() + "/factions/" + name + ".yml");
		try {
			boolean first = false;
			if (!factionFile.exists()) {
				factionFile.createNewFile();
				first = true;
			}
			this.name = name;
			this.description = "";
			this.members = new ArrayList<UUID>();
			this.allies = new ArrayList<String>();
			this.enemies = new ArrayList<String>();
			this.land = new ArrayList<Chunk>();
			this.power = 0;
			if (first) {
				setDesc("Default faction description");
				setMembers(new ArrayList<UUID>());
				setAllies(new ArrayList<String>());
				setEnemies(new ArrayList<String>());
				setLand(new ArrayList<Chunk>());
				setPower(10);
				reloadFactionFile();
			}
			reloadInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get
	 */
	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.description;
	}

	public ArrayList<UUID> getMembers() {
		return this.members;
	}

	public ArrayList<String> getAllies() {
		return this.allies;
	}

	public ArrayList<String> getEnemies() {
		return this.enemies;
	}

	public ArrayList<Chunk> getLand() {
		return this.land;
	}

	public int getPower() {
		return this.power;
	}

	/**
	 * set
	 */
	public void setDesc(String description) {
		this.description = description;
		factionData.set("description", description);
		saveFactionFile();
	}

	public void setMembers(ArrayList<UUID> users) {
		ArrayList<String> test = new ArrayList<String>();
		for (UUID user : users) {
			test.add(user.toString());
		}
		factionData.set("members", test);
		saveFactionFile();
	}

	public void setAllies(ArrayList<String> allies) {
		this.allies = allies;
		factionData.set("allies", allies);
		saveFactionFile();
	}

	public void setEnemies(ArrayList<String> enemies) {
		this.enemies = enemies;
		factionData.set("enemies", enemies);
		saveFactionFile();
	}

	public void setLand(ArrayList<Chunk> land) {
		this.land = land;
		factionData.set("land", land);
		saveFactionFile();
	}

	public void setPower(int power) {
		this.power = power;
		factionData.set("power", power);
		saveFactionFile();
	}

	/**
	 * add
	 */
	public void addMember(UUID user) {
		members.add(user);
		setMembers(members);
	}

	public void addAlly(String ally) {
		allies.add(ally);
		setAllies(allies);
	}

	public void addEnemy(String enemy) {
		enemies.add(enemy);
		setEnemies(enemies);
	}

	public void addLand(Chunk land) {
		this.land.add(land);
		setLand(this.land);
	}

	public void addPower(int power) {
		this.power = this.power + power;
		setPower(this.power);
	}

	/**
	 * remove
	 */
	public void removeMember(UUID member) {
		members.remove(member);
		setMembers(members);
	}

	public void removeAlly(String ally) {
		allies.remove(ally);
		setAllies(allies);
	}

	public void removeEnemy(String enemy) {
		enemies.remove(enemy);
		setEnemies(enemies);
	}

	public void removeLand(Chunk land) {
		this.land.remove(land);
		setLand(this.land);
	}

	public void removePower(int power) {
		this.power = this.power - power;
		setPower(this.power);
	}

	/**
	 * File
	 */
	public void reloadFactionFile() {
		try {
			factionData.load(factionFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveFactionFile() {
		try {
			factionData.save(factionFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadInfo() {
		this.description = getDesc();
		this.members = getMembers();
		this.allies = getAllies();
		this.enemies = getEnemies();
		this.land = getLand();
		this.power = getPower();
	}
}
