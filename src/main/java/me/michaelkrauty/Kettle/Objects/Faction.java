package me.michaelkrauty.Kettle.Objects;

import me.michaelkrauty.Kettle.Kettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Faction {

	private Kettle kettle;
	private String name;
	private String description;
	private ArrayList<UUID> members;
	private ArrayList<String> allies;
	private ArrayList<String> enemies;
	private String land;
	private int power;
	private boolean exists;

	public Faction(Kettle kettle, String name) {
		this.kettle = kettle;
		try {
			HashMap result = kettle.sql.getFaction(name);
			if (result != null) {
				this.name = (String) result.get("name");
				this.description = (String) result.get("desc");
				this.members = null;
				this.allies = null;
				this.enemies = null;
				this.land = ""; // TODO
				this.power = 0; //TODO
				this.exists = true;
			} else {
				this.exists = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.exists = false;
		}
	}

	//exists?
	public boolean exists() {
		return this.exists;
	}

	//get
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

	public String getLand() {
		return this.land;
	}

	public int getPower() {
		return this.power;
	}

	//set
	public boolean setName() {
		// TODO: set name
		return false;
	}

	public boolean setDesc() {
		// TODO: set desc
		return false;
	}

	//add
	public void addMember(UUID player) {
		members.add(player);
	}

	public void addAlly() {
		// TODO: add ally
	}

	public void addEnemy() {
		// TODO: add enemy
	}

	public void addLand() {
		// TODO: add land
	}

	public void addPower() {
		// TODO: add power
	}

	//remove
	public void removeMember() {
		// TODO: remove power
	}

	public void removeAlly() {
		// TODO: remove ally
	}

	public void removeEnemy() {
		// TODO: remove enemy
	}

	public void removeLand() {
		// TODO: remove land
	}

	public void removePower() {
		// TODO: remove power
	}
}
