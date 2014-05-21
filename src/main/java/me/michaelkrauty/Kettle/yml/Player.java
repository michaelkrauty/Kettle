package me.michaelkrauty.Kettle.yml;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Player {

	private final Kettle kettle;

	public Player(Kettle instance) {
		kettle = instance;
	}

	public void newPlayer(String uuid) {
		YamlConfiguration player = new YamlConfiguration();
		File playerFile = new File("players/" + uuid + ".yml");
		try {
			player.load(playerFile);
		} catch (Exception e) {
			kettle.log.log(Level.WARNING, "Error loading players/" + uuid + ".yml!");
			kettle.log.log(Level.WARNING, e.getMessage());
		}
	}

	public ArrayList<String> getPlayer(String uuid) {
		File playerFile = new File("players/" + uuid + ".yml");
		if(playerFile.exists()) {
			ArrayList<String> playerInfo = new ArrayList<String>();
			// TODO: get player info
			return playerInfo;
		} else {
			return null;
		}
	}

	public void setPlayerData(String uuid, String key, String value) {
		File playerFile = new File("players/" + uuid + ".yml");
		if (playerFile.exists()) {
			// TODO: set value under key in players/uuid.yml
		}
	}
}
