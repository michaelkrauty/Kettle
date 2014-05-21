package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class PlayerFile {

	private final Kettle kettle;

	public PlayerFile(Kettle instance) {
		kettle = instance;
	}

	public void newPlayer(String uuid, String name) {
		File playerFile = new File("players/" + uuid + ".yml");
		try {
			playerFile.createNewFile();
			setPlayerData(uuid, "uuid", uuid);
			setPlayerData(uuid, "username", name);
		} catch (Exception e) {
			kettle.error.printError("Error creating players/" + uuid + ".yml", e.getMessage());
		}
	}

	public ArrayList<String> getPlayer(String uuid) {
		File playerFile = new File("players/" + uuid + ".yml");
		if (playerFile.exists()) {
			ArrayList<String> playerInfo = new ArrayList<String>();
			YamlConfiguration player = new YamlConfiguration();
			try {
				player.load(playerFile);
			} catch (Exception e) {
				kettle.error.printError("Error loading players/" + uuid + ".yml", e.getMessage());
			}
			return playerInfo;
		} else {
			return null;
		}
	}

	public void setPlayerData(String uuid, String key, String value) {
		File playerFile = new File("players/" + uuid + ".yml");
		if (playerFile.exists()) {
			// TODO: set value under key in players/uuid.file
		}
	}
}
