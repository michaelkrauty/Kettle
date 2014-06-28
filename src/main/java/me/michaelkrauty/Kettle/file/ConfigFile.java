package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class ConfigFile {

	private final Kettle kettle;

	File configFile = new File(Kettle.kettle.getDataFolder() + "/config.yml");
	YamlConfiguration config = new YamlConfiguration();

	public ConfigFile(Kettle instance) {
		kettle = instance;
		checkFile();
		reload();
	}

	private void checkFile() {
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				InputStream input = kettle.getResource("config.yml");
				OutputStream output = new FileOutputStream(configFile);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buffer)) > 0) {
					output.write(buffer, 0, bytesRead);
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void reload() {
		try {
			config.load(configFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getString(String path) {
		return config.getString(path);
	}
}
