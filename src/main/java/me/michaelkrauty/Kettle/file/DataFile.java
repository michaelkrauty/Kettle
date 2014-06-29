package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class DataFile {

	private final Kettle kettle;

	File dataFile = new File(Kettle.kettle.getDataFolder() + "/data.yml");
	YamlConfiguration data = new YamlConfiguration();

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
			data.save(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		try {
			data.load(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String path, String value) {
		data.set(path, value);
		save();
		reload();
	}

	public void set(String path, Location value) {
		data.set(path, kettle.locationToString(value));
		save();
		reload();
	}

	public String getString(String path) {
		return data.getString(path);
	}

	public Location getLocation(String path) {
		if (data.getString(path) != null) {
			String[] dat = data.getString(path).split(",");
			return new Location(kettle.getServer().getWorld(dat[0]), Integer.parseInt(dat[1]), Integer.parseInt(dat[2]), Integer.parseInt(dat[3]));
		}
		return null;
	}
}