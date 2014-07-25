package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Faction {

	File factionFile;

	private Kettle kettle;
	private String name;
	private String description;
	private UUID owner;
	private ArrayList<UUID> members;
	private ArrayList<String> allies;
	private ArrayList<String> enemies;
	private ArrayList<Chunk> land;
	private int power;

	public Faction(Kettle kettle, String name, UUID owner) {
		this.kettle = kettle;
		this.name = name;
		this.owner = owner;
		init(true);
	}

	public Faction(Kettle kettle, String name) {
		this.kettle = kettle;
		this.name = name;
		init(false);
	}

	private void init(boolean created) {
		factionFile = new File(kettle.getDataFolder() + "/factions/" + name + ".yml");
		try {
			boolean first = false;
			if (!factionFile.exists()) {
				factionFile.createNewFile();
				first = true;
			}
			this.description = "";
			if (created) {
				setOwner(this.owner);
			}
			this.members = new ArrayList<UUID>();
			this.allies = new ArrayList<String>();
			this.enemies = new ArrayList<String>();
			this.land = new ArrayList<Chunk>();
			this.power = 0;
			if (first) {
				setDesc("Default faction description");
				setOwner(owner);
				setMembers(new ArrayList<UUID>());
				setAllies(new ArrayList<String>());
				setEnemies(new ArrayList<String>());
				setLand(new ArrayList<Chunk>());
				setPower(10);
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

	public UUID getOwner() {
		return this.owner;
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
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public void setMembers(ArrayList<UUID> members) {
		this.members = members;
	}

	public void setAllies(ArrayList<String> allies) {
		this.allies = allies;
	}

	public void setEnemies(ArrayList<String> enemies) {
		this.enemies = enemies;
	}

	public void setLand(ArrayList<Chunk> land) {
		this.land = land;
	}

	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * add
	 */
	public void addMember(UUID user) {
		members.add(user);
	}

	public void addAlly(String ally) {
		allies.add(ally);
	}

	public void addEnemy(String enemy) {
		enemies.add(enemy);
	}

	public void addLand(Chunk land) {
		this.land.add(land);
	}

	public void addPower(int power) {
		this.power = this.power + power;
	}

	/**
	 * remove
	 */
	public void removeMember(UUID member) {
		members.remove(member);
	}

	public void removeAlly(String ally) {
		allies.remove(ally);
	}

	public void removeEnemy(String enemy) {
		enemies.remove(enemy);
	}

	public void removeLand(Chunk land) {
		this.land.remove(land);
	}

	public void removePower(int power) {
		this.power = this.power - power;
	}

	/**
	 * File
	 */

	public void saveFactionFile() {
		YamlConfiguration factionData = new YamlConfiguration();
		try {
			factionData.set("description", description);
			factionData.set("owner", owner.toString());
			ArrayList<String> members1 = new ArrayList<String>();
			for (UUID uuid : members) {
				members1.add(uuid.toString());
			}
			factionData.set("members", members1);
			factionData.set("allies", allies);
			factionData.set("enemies", enemies);
			factionData.set("land", land);
			factionData.set("power", power);
			factionData.save(factionFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadInfo() {
		YamlConfiguration factionData = new YamlConfiguration();
		try {
			factionData.load(factionFile);

			this.description = factionData.getString("description");

			this.owner = UUID.fromString(factionData.getString("owner"));

			ArrayList<UUID> memberList = new ArrayList<UUID>();
			for (String str : factionData.getStringList("members")) {
				memberList.add(UUID.fromString(str));
			}
			this.members = memberList;

			ArrayList<String> allyList = new ArrayList<String>();
			for (String str : factionData.getStringList("allies")) {
				allyList.add(str);
			}
			this.allies = allyList;

			ArrayList<String> enemyList = new ArrayList<String>();
			for (String str : factionData.getStringList("enemies")) {
				enemyList.add(str);
			}
			this.enemies = enemyList;

			this.land = new ArrayList<Chunk>();

			this.power = factionData.getInt("power");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFactionFile() {
		factionFile.delete();
	}
}
