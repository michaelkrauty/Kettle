package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class ConfigFile {

	File configFile = new File("plugins/Kettle/config.yml");

	YamlConfiguration config = new YamlConfiguration();

	private final Kettle kettle;

	private final String[] defaultConfig = new String[]{
			"config_version: 1.0",
			"enabled_plugins:",
			"  - essential",
			"  - factions"
	};

	public ConfigFile(Kettle instance) {
		kettle = instance;
		if (!configFile.exists()) {
			try {
				File kettleDir = new File("plugins/Kettle");
				if (!kettleDir.exists()) {
					kettleDir.mkdir();
				}
				PrintWriter out = new PrintWriter("plugins/Kettle/config.yml");
				for (String line : defaultConfig) {
					out.println(line);
				}
				out.close();
			} catch (Exception e) {
				kettle.error.printError("Failed to copy default config.yml", e.getMessage());
			}
		}
		try {
			config.load(configFile);
		} catch (Exception e) {
			kettle.error.printError("Error loading config.yml", e.getMessage());
		}
	}

	public String getString(String key) {
		return config.getString(key);
	}

	public int getInt(String key) {
		return config.getInt(key);
	}

	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	/**
	 * get an item stack, if ever necessary
	 */
	public ItemStack getItemStack(String key) {
		return config.getItemStack(key);
	}

	public List<String> getList(String key) {
		return (List<String>) config.getList(key);
	}

	public void set(String key, String value) {
		config.set(key, value);
		save();
	}

	public void set(String key, List<String> value) {
		config.set(key, value);
		save();
	}

	public ArrayList<String> getEnabledPlugins() {
		ArrayList<String> plugins = new ArrayList<String>();
		plugins.addAll(getList("enabled_plugins"));
		return plugins;
	}

	public void save() {
		try {
			config.save(configFile);
		} catch (Exception e) {
			kettle.error.printError("Couldn't save config.yml", e.getMessage());
		}
	}
}
