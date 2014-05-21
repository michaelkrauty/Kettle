package me.michaelkrauty.Kettle.yml;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.FileUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Config {

	YamlConfiguration config = new YamlConfiguration();

	private final Kettle kettle;

	public Config(Kettle instance) {
		kettle = instance;
		File configFile = new File("config.yml");
		if (!configFile.exists()) {
			URL url = getClass().getResource("config.yml");
			try {
				FileOutputStream output = new FileOutputStream("config.yml");
				InputStream input = url.openStream();
				byte[] buffer = new byte[4096];
				int bytesRead = input.read(buffer);
				while (bytesRead != -1) {
					output.write(buffer, 0, bytesRead);
					bytesRead = input.read(buffer);
				}
				output.close();
				input.close();
			} catch (Exception e) {
				kettle.log.log(Level.WARNING, "Failed to copy default config.yml");
				kettle.log.log(Level.WARNING, e.getMessage());
			}
		}
		try {
			config.load(configFile);
		} catch (Exception e) {
			kettle.log.log(Level.WARNING, "Error loading config.yml");
			kettle.log.log(Level.WARNING, e.getMessage());
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
	}

	public ArrayList<String> getEnabledModules() {
		ArrayList<String> plugins = new ArrayList<String>();
		plugins.addAll(getList("enabled_plugins"));
		return plugins;
	}
}
