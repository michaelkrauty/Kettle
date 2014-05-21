package me.michaelkrauty.Kettle.yml;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.logging.Level;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Config {

	private final Kettle kettle;

	public Config(Kettle instance) {
		kettle = instance;
		YamlConfiguration config = new YamlConfiguration();
		File configFile = new File("config.yml");
		try {
			config.load(configFile);
		} catch (Exception e) {
			kettle.log.log(Level.WARNING, "Error loading config.yml");
			kettle.log.log(Level.WARNING, e.getMessage());
		}
	}
}
