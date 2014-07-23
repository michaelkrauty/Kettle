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
public class WorthFile {

	private final Kettle kettle;

	File worthFile = new File(Kettle.kettle.getDataFolder() + "/worth.yml");
	YamlConfiguration data = new YamlConfiguration();

	public WorthFile(Kettle instance) {
		kettle = instance;
		checkFile();
		reload();
	}

	private void checkFile() {
		if (!worthFile.exists()) {
			try {
				worthFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		try {
			data.save(worthFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		try {
			data.load(worthFile);
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

	public void set(String path, ArrayList<String> value) {
		data.set(path, value.toArray());
		save();
		reload();
	}

	public String getString(String path) {
		return data.getString(path);
	}

	public ArrayList<String> getArrayList(String path) {
		ArrayList<String> al = new ArrayList<String>();
		if (data.getString(path) != null) {
			al.addAll((List<String>) data.getList(path));
		}
		return al;
	}

	public Location getLocation(String path) {
		if (data.getString(path) != null) {
			String[] dat = data.getString(path).split(",");
			return new Location(kettle.getServer().getWorld(dat[0]), Integer.parseInt(dat[1]), Integer.parseInt(dat[2]), Integer.parseInt(dat[3]));
		}
		return null;
	}
}