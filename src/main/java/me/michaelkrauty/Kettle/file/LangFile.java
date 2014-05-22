package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class LangFile {

	File langFile = new File("plugins/Kettle/lang.yml");

	YamlConfiguration lang = new YamlConfiguration();

	private final Kettle kettle;

	private final String[] defaultLang = new String[]{
			"teleport:",
			"  incorrectusage: 'Unknown usage! Use \"/help tp\" for help.'",
			"  nopermission: 'You don''t have permission to do that!'",
			"  playernotfound: 'Couldn''t find the player <%player%>!'",
			"  teleportsuccess: 'Teleported to <%player%>'",
			"  teleportplayertoplayersuccess: 'Teleported <%player1%> to <%player2%>'",
			"  console: 'You''re a console! You can''t teleport!'",
			"gamemode:",
			"  incorrectusage: 'Unknown usage! Use \"/help gm\" for help.'",
			"  success_creative: 'Creative mode achieved!'",
			"  success_survival: 'Survival mode achieved!'",
			"  nopermission: 'You don''t have permission to do that!'",
			"  console: 'You''re a console! You can''t change your gamemode!'"
	};

	public LangFile(Kettle instance) {
		kettle = instance;
		if (!langFile.exists()) {
			try {
				File kettleDir = new File("plugins/Kettle");
				if (!kettleDir.exists()) {
					kettleDir.mkdir();
				}
				PrintWriter out = new PrintWriter("plugins/Kettle/lang.yml");
				for (String line : defaultLang) {
					out.println(line);
				}
				out.close();
			} catch (Exception e) {
				kettle.error.printError("Failed to copy default lang.yml", e.getMessage());
			}
		}
		try {
			lang.load(langFile);
		} catch (Exception e) {
			kettle.error.printError("Error loading lang.yml", e.getMessage());
		}
	}

	public String getString(String command, String key) {
		return lang.getString(command + "." + key);
	}

	public String getString(String command, String key, String item) {
		return lang.getString(command + "." + key).replace("<%player%>", item);
	}

	public String getString(String command, String key, String player1, String player2) {
		return lang.getString(command + "." + key).replace("<%player1%>", player1).replace("<%player2%>", player2);
	}
}
