package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class DataFile {

	private final Kettle kettle;

	File dataFile = new File(Kettle.kettle.getDataFolder() + "/worth.yml");
	YamlConfiguration yaml = new YamlConfiguration();

	public DataFile(Kettle instance) {
		kettle = instance;
		checkFile();
		reload();
	}

	private void checkFile() {
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		try {
			yaml.save(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		try {
			yaml.load(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String path, String value) {
		yaml.set(path, value);
		save();
		reload();
	}

	public void set(String path, Location value) {
		yaml.set(path, kettle.locationToString(value));
		save();
		reload();
	}

	public void set(String path, ArrayList<String> value) {
		yaml.set(path, value.toArray());
		save();
		reload();
	}

	public String getString(String path) {
		return yaml.getString(path);
	}

	public ArrayList<String> getArrayList(String path) {
		ArrayList<String> al = new ArrayList<String>();
		if (yaml.getString(path) != null) {
			al.addAll((List<String>) yaml.getList(path));
		}
		return al;
	}

	public Location getLocation(String path) {
		if (yaml.getString(path) != null) {
			String[] dat = yaml.getString(path).split(",");
			return new Location(kettle.getServer().getWorld(dat[0]), Integer.parseInt(dat[1]), Integer.parseInt(dat[2]), Integer.parseInt(dat[3]));
		}
		return null;
	}
}