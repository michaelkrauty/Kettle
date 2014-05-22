package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

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

	public void newPlayer(Player player) {
		String uuid = player.getUniqueId().toString();
		setPlayerData(uuid, "uuid", uuid);
		setPlayerData(uuid, "username", player.getName());
	}

	public HashMap<String, String> getPlayer(Player player) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		HashMap<String, String> playerData = new HashMap<String, String>();
		File playerFile = new File("plugins/Kettle/players/" + playerUUID + ".yml");
		if (playerFile.exists()) {
			YamlConfiguration playerYaml = new YamlConfiguration();
			try {
				playerYaml.load(playerFile);
				playerData.put("uuid", playerUUID);
				playerData.put("username", playerYaml.getString("username"));
			} catch (Exception e) {
				kettle.error.printError("Error loading plugins/Kettle/players/" + playerUUID + ".yml", e.getMessage());
			}
			return playerData;
		} else {
			return null;
		}
	}

	public void setPlayerData(String uuid, String key, String value) {
		File playerFile = new File("plugins/Kettle/players/" + uuid + ".yml");
		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
			} catch (Exception e) {
				kettle.error.printError("Error creating plugins/Kettle/players/" + uuid + ".yml", e.getMessage());
			}
		}
		YamlConfiguration player = new YamlConfiguration();
		try {
			player.load(playerFile);
			player.set(key, value);
			player.save(playerFile);
		} catch (Exception e) {
			kettle.error.printError("Error loading plugins/Kettle/players/" + uuid + ".yml", e.getMessage());
		}
	}
}
